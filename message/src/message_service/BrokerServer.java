package message_service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 用于启动消息处理中心
 */
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
        		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream()) 
        		)
        {
            while (true) {
              try{ 
            	   String str = in.readLine();
            	   System.out.println(str);
            	   if (str == null) {
                       continue;
                   }
            	   System.out.println("接收到原始数据：" + str);
            	   if (str.equals("CONSUME")) { //CONSUME 表示要消费一条消息
                       //从消息队列中消费一条消息
            		   System.out.println(str);
            		   str=in.readLine();
                       String message = Broker.consume(str);
                       out.println(message);
                       out.flush();
                   } else if (str.equals("SEND")){
                	   System.out.println(str);
                       //接受到的请求包含SEND:字符串 表示生产消息放到消息队列中
                	   String target = in.readLine();
                	   str=in.readLine();
                       Broker.produce(target,str);
                   }else if(str.equals("REGIST")) {
                	   //System.out.println(str);
                	   str=in.readLine();
                	  // System.out.println(str);
                	   System.out.println("用户"+str+"注册到消息服务器！");
                	   Broker.regist(str);
                   }else if(str.equals("UNREGIST")) {
                	   System.out.println(str);
                	   str=in.readLine();
                	   Broker.unregist(str);
                   }else {
                       System.out.println("原始数据:"+str+"没有遵循协议,不提供相关服务");
                   }
               }catch(Exception e){
            	   System.out.println("Exception");
            	   System.out.println("远程终止连接");
            	   System.out.println("=======================");
            	   e.printStackTrace();
            	   break;
               }
              System.out.println("=======================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")

		ServerSocket server = new ServerSocket(SERVICE_PORT);
        System.out.println("server running");
       while (true) 
        {
            BrokerServer brokerServer = new BrokerServer(server.accept());
            System.out.println("远程连接开始：");
            new Thread(brokerServer).start();
        }
    }
}
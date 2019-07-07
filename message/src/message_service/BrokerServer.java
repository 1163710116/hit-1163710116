package message_service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����������Ϣ��������
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
            	   System.out.println("���յ�ԭʼ���ݣ�" + str);
            	   if (str.equals("CONSUME")) { //CONSUME ��ʾҪ����һ����Ϣ
                       //����Ϣ����������һ����Ϣ
            		   System.out.println(str);
            		   str=in.readLine();
                       String message = Broker.consume(str);
                       out.println(message);
                       out.flush();
                   } else if (str.equals("SEND")){
                	   System.out.println(str);
                       //���ܵ����������SEND:�ַ��� ��ʾ������Ϣ�ŵ���Ϣ������
                	   String target = in.readLine();
                	   str=in.readLine();
                       Broker.produce(target,str);
                   }else if(str.equals("REGIST")) {
                	   //System.out.println(str);
                	   str=in.readLine();
                	  // System.out.println(str);
                	   System.out.println("�û�"+str+"ע�ᵽ��Ϣ��������");
                	   Broker.regist(str);
                   }else if(str.equals("UNREGIST")) {
                	   System.out.println(str);
                	   str=in.readLine();
                	   Broker.unregist(str);
                   }else {
                       System.out.println("ԭʼ����:"+str+"û����ѭЭ��,���ṩ��ط���");
                   }
               }catch(Exception e){
            	   System.out.println("Exception");
            	   System.out.println("Զ����ֹ����");
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
            System.out.println("Զ�����ӿ�ʼ��");
            new Thread(brokerServer).start();
        }
    }
}
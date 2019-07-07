package messageclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 访问消息队列的客户端
 */
public class MQClient {
	private Socket socket;
	private  PrintWriter out;
	private BufferedReader in;
	private MQClient(){
	       try {
	    	   //System.out.println("1");
			socket = new Socket(InetAddress.getLocalHost(), 9999);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static MQClient instance = new MQClient();
	
	public static MQClient  getClient() {
		return instance;
	}
	public void regist(String targetName) {
		out.println("REGIST");
		System.out.println("REGIST");
		out.println(targetName);
		System.out.println(targetName);
		out.flush();
		
	}
	public void unregist(String targetName) {
		out.println("UNREGIST");
		out.println(targetName);
		out.flush();
		
	}
    public  void produce(String targetName,String message) throws Exception {
        //本地的的BrokerServer.SERVICE_PORT 创建SOCKET
    	//PrintWriter out = new PrintWriter(socket.getOutputStream());
    	out.println("SEND");
    	out.println(targetName);
         out.println(message);
         out.flush();
    }

    //消费消息
    public  String consume(String sourceName) throws Exception {
//        	Socket socket = new Socket(InetAddress.getLocalHost(),9999);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream());
            //先向消息队列发送命令
            out.println("CONSUME");
            out.println(sourceName);
            out.flush();
            //再从消息队列获取一条消息
            String message = in.readLine();
            return message;
        }
    }
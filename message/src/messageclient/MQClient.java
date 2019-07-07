package messageclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ������Ϣ���еĿͻ���
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
        //���صĵ�BrokerServer.SERVICE_PORT ����SOCKET
    	//PrintWriter out = new PrintWriter(socket.getOutputStream());
    	out.println("SEND");
    	out.println(targetName);
         out.println(message);
         out.flush();
    }

    //������Ϣ
    public  String consume(String sourceName) throws Exception {
//        	Socket socket = new Socket(InetAddress.getLocalHost(),9999);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream());
            //������Ϣ���з�������
            out.println("CONSUME");
            out.println(sourceName);
            out.flush();
            //�ٴ���Ϣ���л�ȡһ����Ϣ
            String message = in.readLine();
            return message;
        }
    }
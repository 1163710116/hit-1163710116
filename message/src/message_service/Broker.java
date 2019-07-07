package message_service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * ��Ϣ��������
 */
public class Broker {
    // ���д洢��Ϣ���������
    private final static int MAX_SIZE = 10;

    // ������Ϣ���ݵ�����
    //private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);
    private static Map<String,ArrayBlockingQueue<String>> messages=new HashMap<>();
    // ������Ϣ
    public static void produce(String target,String msg) {
    	if(target.toLowerCase().equals("all")) {
    		for(ArrayBlockingQueue<String> one : messages.values()){
    			one.offer(msg);
    		}
    	}
    	else  if (messages.get(target).offer(msg)) {
            System.out.println("�ɹ�����Ϣ��������Ͷ����Ϣ��" + msg + "����ǰ�ݴ����Ϣ�����ǣ�" + messages.get(target).size());
        } else {
            System.out.println("��Ϣ�����������ݴ����Ϣ�ﵽ��󸺺ɣ����ܼ���������Ϣ��");
        }
        //System.out.println("=======================");
    }

    // ������Ϣ
    public static String consume(String source) {
        String msg = messages.get(source).poll();
        if (msg != null) {
            // ���������������������Ϣ������ȡ��һ����Ϣ
            System.out.println("�Ѿ�������Ϣ��" + msg + "����ǰ�ݴ����Ϣ�����ǣ�" + messages.get(source).size());
        } else {
            System.out.println("��Ϣ����������û����Ϣ�ɹ����ѣ�");
        }
        //System.out.println("=======================");
        return msg;
    }

	public static void regist(String str) {
		// TODO Auto-generated method stub
		messages.put(str,new ArrayBlockingQueue<String>(MAX_SIZE));
	}
	public static void unregist(String str) {
		messages.remove(str);
	}

}
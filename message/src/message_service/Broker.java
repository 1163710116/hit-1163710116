package message_service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 消息处理中心
 */
public class Broker {
    // 队列存储消息的最大数量
    private final static int MAX_SIZE = 10;

    // 保存消息数据的容器
    //private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);
    private static Map<String,ArrayBlockingQueue<String>> messages=new HashMap<>();
    // 生产消息
    public static void produce(String target,String msg) {
    	if(target.toLowerCase().equals("all")) {
    		for(ArrayBlockingQueue<String> one : messages.values()){
    			one.offer(msg);
    		}
    	}
    	else  if (messages.get(target).offer(msg)) {
            System.out.println("成功向消息处理中心投递消息：" + msg + "，当前暂存的消息数量是：" + messages.get(target).size());
        } else {
            System.out.println("消息处理中心内暂存的消息达到最大负荷，不能继续放入消息！");
        }
        //System.out.println("=======================");
    }

    // 消费消息
    public static String consume(String source) {
        String msg = messages.get(source).poll();
        if (msg != null) {
            // 消费条件满足情况，从消息容器中取出一条消息
            System.out.println("已经消费消息：" + msg + "，当前暂存的消息数量是：" + messages.get(source).size());
        } else {
            System.out.println("消息处理中心内没有消息可供消费！");
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
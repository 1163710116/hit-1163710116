package messageclient;

public class ConsumeClient {

    public static void main(String[] args) throws Exception {
        MQClient client = MQClient.getClient();
        //第一次使用需要先注册，然后才能接收发给自己的消息；
        //client.regist("1");
        client.regist("1");
        client.regist("2");
        client.regist("3");
        client.regist("4");
        client.regist("5");
        client.regist("6");
        client.regist("7");
        client.regist("8");
        client.regist("9");
        client.regist("10");
        //while(true);
        
        
        
        String sourceName1="1";
        String message1 = client.consume(sourceName1);
        System.out.println("获取的消息为：" + message1);
        
        String sourceName2="2";
        String message2 = client.consume(sourceName2);
        System.out.println("获取的消息为：" + message2);
        
        String sourceName3="3";
        String message3 = client.consume(sourceName3);
        System.out.println("获取的消息为：" + message3);
        
        String sourceName4="4";
        String message4 = client.consume(sourceName4);
        System.out.println("获取的消息为：" + message4);
        
        String sourceName5="5";
        String message5 = client.consume(sourceName5);
        System.out.println("获取的消息为：" + message5);
        
    }
}
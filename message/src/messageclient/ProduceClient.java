package messageclient;
public class ProduceClient {

    public static void main(String[] args) throws Exception {
    	MQClient client= MQClient.getClient();

        client.produce("1","Hello World1");
        client.produce("3","Hello World2");
        client.produce("5","Hello World3");
        client.produce("7","Hello World4");
        client.produce("4","Hello World5");
        client.produce("5","Hello World6");
        client.produce("4","Hello World7");
        client.produce("3","Hello World8");
        client.produce("5","Hello World9");
        client.produce("6","Hello World10");
        client.produce("1","Hello World11");
        client.produce("4","Hello World12");       
        client.produce("3","Hello World13");
        client.produce("2","Hello World14");
         while(true);
    }

}


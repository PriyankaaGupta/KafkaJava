package Producer;

import org.apache.kafka.clients.producer.Producer;

public class HelloWorld {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!!");

        TestProducer.start();
    }
}

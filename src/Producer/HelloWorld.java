package Producer;
import java.io.FileReader;
import java.util.Properties;
//import org.json.simple.JSONObject;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Hello World!!");
            String topicName = "camunda-customer-events";
            //String paymentId="1234567";
            Properties props = new Properties();
            props.load(new FileReader("src/Producer/producer.txt"));
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "camunda");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
            JSONObject obj=new JSONObject();
            obj.put("type","AccountCreated");
            obj.put("accountNumber", new Integer(1234567));
            obj.put("paymentAgreementId", new Integer(45678910));


            //obj.put("accountNo", new Double(23456789));
            String value=obj.toString();
            long key=1234567;


            System.out.print(obj);
            System.out.println("producer properties"+" " + props);
            KafkaProducer<Long, String> producer = new KafkaProducer<Long, String>(props);
            ProducerRecord<Long, String> producerRecord = new ProducerRecord<Long, String>(topicName, key,value);

            producer.send(producerRecord, new Callback() {

                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println(exception.getMessage());
                        System.exit(1);
                    }

                }
            });

            System.out.println("Message sent successfully"+" " + producerRecord);
            producer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Successful");

    }

    /*public static void main(String[] args)
    {
        System.out.println("Hello World!!");

        try {
            TestProducer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

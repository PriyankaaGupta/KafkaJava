package Producer;//Copyright (c) Microsoft Corporation. All rights reserved.
//Licensed under the MIT License.

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.sql.Timestamp;

public class TestDataReporter implements Runnable {

    private static final int NUM_MESSAGES = 5    ;
    private final String TOPIC;

    private final Producer<Long, String> producer;

    public TestDataReporter(final Producer<Long, String> producer, String TOPIC) {
        this.producer = producer;
        this.TOPIC = TOPIC;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUM_MESSAGES; i++) {
            long time = System.currentTimeMillis();
            System.out.println("Test Data #" + i + " from thread #" + Thread.currentThread().getId());

            final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC, time, "Test Data #" + i);
            System.out.println("Message Sent#" + record);
            producer.send(record, new Callback() {

                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println(exception.getMessage());
                        System.exit(1);
                    }

                }
            });
        }
        System.out.println("Finished sending " + NUM_MESSAGES + " messages from thread #" + Thread.currentThread().getId() + "!");
    }
}
package com.kris.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kris.kafka.myPac.ConsumerCreator;
import com.kris.kafka.myPac.IKafkaConstants;
import com.kris.kafka.myPac.ProducerCreator;
import com.kris.kafka.myPac.TwitterHandler;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) {
        disableLog();
        try {
            runProducer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        runConsumer();
    }

    private static void disableLog() {
        Set<String> artifactoryLoggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
        for(String log:artifactoryLoggers) {
            ch.qos.logback.classic.Logger artLogger = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(log);
            artLogger.setLevel(ch.qos.logback.classic.Level.INFO);
            artLogger.setAdditive(false);
        }
    }

    static void runConsumer() {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();

        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }

            //print each record.
            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
            });

            // commits the offset of record to broker.
            consumer.commitAsync();
        }
        consumer.close();
    }

    static void runProducer() throws IOException, URISyntaxException {
        Producer<String, String> producer = ProducerCreator.createProducer();
        TwitterHandler twitterHandler = new TwitterHandler();
        BufferedReader reader = null;
        reader = twitterHandler.getBufferedReader();
        String line = "";
        int key = 0;
        while ((line = reader.readLine()) != null) {
           System.out.println(line);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("try_Buffered3Part", Integer.toString(key), line);
            key++;
            System.out.println(key);
            producer.send(producerRecord);
        }
    }
}

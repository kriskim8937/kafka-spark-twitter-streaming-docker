package com.kris.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import com.kris.kafka.myPac.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerMain {
    public static void main(String[] args) throws IOException, URISyntaxException {
        runProducer();
    }
    static void runProducer() throws IOException, URISyntaxException {
        final Logger logger = LoggerFactory.getLogger(Producer.class);
        Producer<String, String> producer = ProducerCreator.createProducer();
        TwitterHandler twitterHandler = new TwitterHandler();
        BufferedReader reader = null;
        reader = twitterHandler.getBufferedReader();
        String line = "";
        int key = 0;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(IKafkaConstants.TOPIC_NAME, Integer.toString(key), line);
            key++;
            System.out.println(key);
            producer.send(producerRecord);
        }
    }
}

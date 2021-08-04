package com.kris.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kris.kafka.myPac.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;

public class ProducerMain {
    public static void main(String[] args) throws IOException, URISyntaxException {
        runProducer();
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

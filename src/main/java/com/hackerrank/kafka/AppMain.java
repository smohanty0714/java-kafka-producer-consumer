package com.hackerrank.kafka;

import com.hackerrank.kafka.consumer.SubscriptionConsumerLoop;
import com.hackerrank.kafka.consumer.SubscriptionConsumerManager;
import com.hackerrank.kafka.model.Card;
import com.hackerrank.kafka.model.Subscription;
import com.hackerrank.kafka.producer.SubscriptionProducerManager;
import com.hackerrank.kafka.util.FileUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class AppMain {

    public static void main(String[] args) {
        //topic to which subscription records are sent
        String topic = "hackerrank-washing";

        //record to be produced
        Subscription subscription = new Subscription(1l, "Gold Wash", 50d, 2, "USD", "Weekly", new Card("32165498", "2021-12-13", "345"));

        //read config
        Properties kafkaConfig = FileUtils.readKafkaConfig();

        //producer
        SubscriptionProducerManager producerManager = new SubscriptionProducerManager();

        Properties producerProps = producerManager.createProducerProps(kafkaConfig);

        KafkaProducer<Long, Subscription> producer = producerManager.createProducer(producerProps);

        ProducerRecord record = producerManager.createRecord(topic, subscription);

        producerManager.sendRecord(producer, record);

        producerManager.closeProducer(producer);

        //consumer
        SubscriptionConsumerManager consumerManager = new SubscriptionConsumerManager();

        Properties consumerProps = consumerManager.createConsumerProps(kafkaConfig);

        KafkaConsumer<Long, Subscription> consumer = consumerManager.createConsumer(consumerProps);

        SubscriptionConsumerLoop consumerLoop = consumerManager.startConsumption(consumer, topic);

        consumerManager.stopConsumer(consumerLoop);
    }
}

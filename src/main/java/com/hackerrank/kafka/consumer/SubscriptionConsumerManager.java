package com.hackerrank.kafka.consumer;

import com.hackerrank.kafka.model.Subscription;
import com.hackerrank.kafka.serdes.SubscriptionDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SubscriptionConsumerManager {

    /**
     * - set bootstrap servers
     * - set key deserializer
     * - set value deserializer
     * - set consumer group id to 'group_1'
     */
    public Properties createConsumerProps(Properties kafkaConfig) {
        System.out.println("Creating consumer properties");

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getProperty("bootstrap.servers"));
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SubscriptionDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_1");

        return properties;
    }

    /**
     * - create consumer using given consumer properties
     */
    public KafkaConsumer<Long, Subscription> createConsumer(Properties consumerProps) {
        System.out.println("Creating Consumer");

        KafkaConsumer<Long, Subscription> consumer = new KafkaConsumer<>(consumerProps);

        return consumer;
    }

    /**
     * - start consumption from the given topic using the given consumer
     * - need to consume in a loop
     */
    public SubscriptionConsumerLoop startConsumption(KafkaConsumer<Long, Subscription> consumer, String topic) {
        SubscriptionConsumerLoop consumerLoop = new SubscriptionConsumerLoop(consumer, topic);
        consumerLoop.start();

        return consumerLoop;
    }

    /**
     * - stop consumer, wait 10 seconds to let consumer consume at least once
     */
    public void stopConsumer(SubscriptionConsumerLoop consumerLoop) {
        //wait 10 seconds for consumer to consume at least once though the poll duration is 3 seconds.
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumerLoop.stopConsumption();
    }
}

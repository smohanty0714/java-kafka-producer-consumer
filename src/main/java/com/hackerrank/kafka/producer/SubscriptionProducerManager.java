package com.hackerrank.kafka.producer;

import com.hackerrank.kafka.model.Subscription;
import com.hackerrank.kafka.serdes.SubscriptionSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

public class SubscriptionProducerManager {

    /**
     * - set bootstrap servers
     * - set key serializer
     * - set value serializer
     */
    public Properties createProducerProps(Properties kafkaConfig) {
        System.out.println("Creating producer properties");

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getProperty("bootstrap.servers"));
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SubscriptionSerializer.class.getName());

        return props;
    }

    /**
     * - create producer using given properties
     */
    public KafkaProducer<Long, Subscription> createProducer(Properties producerProps) {
        System.out.println("Creating producer");

        KafkaProducer<Long, Subscription> subscriptionProducer = new KafkaProducer<>(producerProps);

        return subscriptionProducer;
    }

    /**
     * - create the producer record for the given topic and message
     * - use subscription's id as the message key
     */
    public ProducerRecord<Long, Subscription> createRecord(String topic, Subscription subscription) {
        System.out.println("Creating record for topic: " + topic);

        ProducerRecord<Long, Subscription> record = new ProducerRecord<>(topic, subscription.getSubscriberId(), subscription);
        return record;
    }

    /**
     * - send the given record using the given producer synchronously
     */
    public void sendRecord(KafkaProducer<Long, Subscription> producer, ProducerRecord<Long, Subscription> record) {
        System.out.println("Sending record with key: " + record.key() + " to topic: " + record.topic());

        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * - flush and close the producer
     */
    public void closeProducer(KafkaProducer<Long, Subscription> producer) {
        System.out.println("Closing producer");
        producer.flush();
        producer.close();
    }
}

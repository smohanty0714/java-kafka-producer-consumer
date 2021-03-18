package com.hackerrank.kafka.consumer;

import com.hackerrank.kafka.model.Subscription;
import com.hackerrank.kafka.store.InAppDataStore;
import com.hackerrank.kafka.util.FileUtils;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubscriptionConsumerLoop extends Thread {
    private static KafkaConsumer<Long, Subscription> consumer;
    private static String topic;
    private static AtomicBoolean shutdown;

    public SubscriptionConsumerLoop(KafkaConsumer<Long, Subscription> _consumer, String _topic) {
        consumer = _consumer;
        topic = _topic;
        shutdown = new AtomicBoolean(false);
    }

    //keep consuming until shutdown = true
    @Override
    public void run() {
        System.out.println("Starting consumption");
        try {
            consumer.subscribe(Collections.singleton(topic));
            while (true) {
                ConsumerRecords<Long, Subscription> records = consumer.poll(Duration.of(100, ChronoUnit.MILLIS));
                if(records.count() > 0) {
                	records.forEach(record -> {
                    	Subscription sub = record.value();
                    	if(FileUtils.isValidData(sub.getCard()) && FileUtils.validCurrency(sub.getCurrency())) {
                    		InAppDataStore.valid.put(record.key(), record.value());
                    	} else {
                    		InAppDataStore.invalid.put(record.key(), record.value());
                    	}
                    });
                }
                
                consumer.commitSync();
            }
        } catch (WakeupException we) {
            if (!shutdown.get())
                throw we;
        } finally {
            consumer.close();
        }
        
    }

    public void stopConsumption() {
        shutdown.set(true);
    }
}

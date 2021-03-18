package com.hackerrank.kafka.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.kafka.model.Subscription;
import org.apache.kafka.common.serialization.Deserializer;

public class SubscriptionDeserializer implements Deserializer<Subscription> {

    @Override
    public Subscription deserialize(String topic, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();

        Subscription subscription = null;
        try {
            subscription = mapper.readValue(bytes, Subscription.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }
}

package com.hackerrank.kafka.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.kafka.model.Subscription;
import org.apache.kafka.common.serialization.Serializer;

public class SubscriptionSerializer implements Serializer<Subscription> {

    @Override
    public byte[] serialize(String topic, Subscription subscription) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(subscription).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}

package com.hackerrank.kafka.store;

import com.hackerrank.kafka.model.Subscription;

import java.util.HashMap;
import java.util.Map;

public class InAppDataStore {
    //key,value;
    public static final Map<Long, Subscription> valid = new HashMap<>();
    public static final Map<Long, Subscription> invalid = new HashMap<>();
}

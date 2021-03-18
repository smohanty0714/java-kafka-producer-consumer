package com.hackerrank.kafka;

import com.hackerrank.kafka.consumer.SubscriptionConsumerLoop;
import com.hackerrank.kafka.consumer.SubscriptionConsumerManager;
import com.hackerrank.kafka.model.Card;
import com.hackerrank.kafka.model.Subscription;
import com.hackerrank.kafka.producer.SubscriptionProducerManager;
import com.hackerrank.kafka.store.InAppDataStore;
import com.hackerrank.kafka.util.FileUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class ApplicationTest {
    public static final String TOPIC_NAME = "hackerrank-washing";
    private static Properties kafkaConfig;

    private static SubscriptionProducerManager producerManager;
    private static SubscriptionConsumerManager consumerManager;

    private static Map<String, Map<Long, Subscription>> testData;

    @BeforeClass
    public static void before() {
        System.out.println("Setting up env");

        kafkaConfig = FileUtils.readKafkaConfig();
        producerManager = new SubscriptionProducerManager();
        consumerManager = new SubscriptionConsumerManager();

        testData = getTestData();

        produceSubscription();
        consumeSubscription();
    }

    private static void produceSubscription() {
        Properties props = producerManager.createProducerProps(kafkaConfig);

        KafkaProducer<Long, Subscription> producer = producerManager.createProducer(props);

        for (Subscription expectedRecord : testData.get("valid").values()) {
            ProducerRecord<Long, Subscription> record = producerManager.createRecord(TOPIC_NAME, expectedRecord);
            producerManager.sendRecord(producer, record);
        }

        for (Subscription expectedRecord : testData.get("invalid").values()) {
            ProducerRecord<Long, Subscription> record = producerManager.createRecord(TOPIC_NAME, expectedRecord);
            producerManager.sendRecord(producer, record);
        }

        //make sure store is empty
        Assert.assertTrue(InAppDataStore.invalid.isEmpty());
        Assert.assertTrue(InAppDataStore.valid.isEmpty());
    }

    private static void consumeSubscription() {
        Properties props = consumerManager.createConsumerProps(kafkaConfig);

        KafkaConsumer<Long, Subscription> consumer = consumerManager.createConsumer(props);
        Assert.assertNotNull("Consumer is null", consumer);

        SubscriptionConsumerLoop consumerLoop = consumerManager.startConsumption(consumer, TOPIC_NAME);

        consumerManager.stopConsumer(consumerLoop);
    }

    private static Map<String, Map<Long, Subscription>> getTestData() {
        Map<String, Map<Long, Subscription>> data = new HashMap<>();

        Map<Long, Subscription> valid = new HashMap<>();
        valid.put(1l, new Subscription(1l, "Gold Wash", 50d, qty(), "USD", "Monthly", new Card("32165498", "2025-12-13", code())));
        valid.put(2l, new Subscription(2l, "Platinum Wash", 40d, qty(), "GBP", "Weekly", new Card("32166498", "2026-12-13", code())));
        valid.put(3l, new Subscription(3l, "Silver Wash", 30d, qty(), "EURO", "Daily", new Card("32165198", "2027-12-13", code())));

        valid.put(4l, new Subscription(4l, "Gold Wash", 50d, qty(), "USD", "Weekly", new Card("92165498", "20225-12-13", code())));
        valid.put(5l, new Subscription(5l, "Platinum Wash", 40d, qty(), "GBP", "Weekly", new Card("62166498", "2025-12-13", code())));
        valid.put(6l, new Subscription(6l, "Silver Wash", 30d, qty(), "USD", "Weekly", new Card("42165198", "2025-12-13", code())));

        Map<Long, Subscription> invalid = new HashMap<>();
        //invalid card number
        invalid.put(7l, new Subscription(7l, "Gold Wash", 50d, qty(), "USD", "Weekly", new Card("9216598", "2025-12-13", code())));
        invalid.put(8l, new Subscription(8l, "Platinum Wash", 40d, qty(), "GBP", "Weekly", new Card("6216498", "2026-12-13", code())));
        invalid.put(9l, new Subscription(9l, "Silver Wash", 30d, qty(), "USD", "Weekly", new Card("4265198", "2027-12-13", code())));

        //invalidvalid card expire date
        invalid.put(10l, new Subscription(10l, "Gold Wash", 50d, qty(), "USD", "Weekly", new Card("32165498", "2019-12-13", code())));
        invalid.put(11l, new Subscription(11l, "Platinum Wash", 40d, qty(), "GBP", "Weekly", new Card("32166498", "2018-12-13", code())));
        invalid.put(12l, new Subscription(12l, "Silver Wash", 30d, qty(), "USD", "Weekly", new Card("32165198", "2018-12-13", code())));

        //invalidvalid card security code
        invalid.put(13l, new Subscription(13l, "Gold Wash", 50d, qty(), "USD", "Weekly", new Card("9216598", "2025-12-13", code())));
        invalid.put(14l, new Subscription(14l, "Platinum Wash", 40d, qty(), "GBP", "Weekly", new Card("6216498", "2026-12-13", code())));
        invalid.put(15l, new Subscription(15l, "Silver Wash", 30d, qty(), "USD", "Weekly", new Card("4265198", "2027-12-13", code())));

        data.put("valid", valid);
        data.put("invalid", invalid);

        return data;
    }

    @Test
    public void testValidSubscription() {
        Assert.assertEquals(testData.get("valid").size(), InAppDataStore.valid.size());
        for (Subscription expectedRecord : testData.get("valid").values()) {
            Assert.assertTrue("expected: " + expectedRecord + " actual: " + InAppDataStore.valid.get(expectedRecord.getSubscriberId()), new ReflectionEquals(expectedRecord, "card").matches(InAppDataStore.valid.get(expectedRecord.getSubscriberId())));
            Assert.assertTrue("expected: " + expectedRecord.getCard() + " actual: " + InAppDataStore.valid.get(expectedRecord.getSubscriberId()).getCard(), new ReflectionEquals(expectedRecord.getCard()).matches(InAppDataStore.valid.get(expectedRecord.getSubscriberId()).getCard()));
        }
    }

    @Test
    public void testInvalidSubscription() {
        Assert.assertEquals(testData.get("invalid").size(), InAppDataStore.invalid.size());
        for (Subscription expectedRecord : testData.get("invalid").values()) {
            Assert.assertTrue("expected: " + expectedRecord + " actual: " + InAppDataStore.invalid.get(expectedRecord.getSubscriberId()), new ReflectionEquals(expectedRecord, "card").matches(InAppDataStore.invalid.get(expectedRecord.getSubscriberId())));
            Assert.assertTrue("expected: " + expectedRecord.getCard() + " actual: " + InAppDataStore.invalid.get(expectedRecord.getSubscriberId()).getCard(), new ReflectionEquals(expectedRecord.getCard()).matches(InAppDataStore.invalid.get(expectedRecord.getSubscriberId()).getCard()));
        }
    }

    @Test
    public void testValidateConsumer() {
        Properties props = consumerManager.createConsumerProps(kafkaConfig);

        KafkaConsumer<Long, Subscription> consumer = consumerManager.createConsumer(props);
        Assert.assertNotNull("consumer is null", consumer);
    }

    private static Integer qty() {
        Random random = new Random();
        return random.nextInt(4) + 1;
    }

    private static String code() {
        return (qty() + 1) + String.valueOf(qty() + 1) + (qty() + 1);
    }
}

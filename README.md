## Environment:
- Java version: 1.8
- Maven version: 3.*
- Kafka version: kafka_2.13-2.6.0
- Kafka Java Client version: 2.6.0

## Read-Only Files:
- src/test/*
- src/main/resources/kafka.properties
- setup/kafkaInstall.sh

## Data:
Each subscription object has the following attributes.
```json
{
    "subscriberId": 1,
    "productName": "Name of the subscribed product; Gold Wash, Platinum Wash, Silver Wash.",
    "unitPrice": "Price of the subscribed product.",
    "quantity": "Number of cars to be washed",
    "currency": "Payment currency; FJD,GBP,USD,EURO",
    "frequency": "If the subscribed services is recurring its values can be Daily,Weekly,Monthly.",
    "card": {
          "cardNumber": "8-digits credit card number.",
          "cardExpirationDate": "Card expiry date.",
          "cardSecurityCode": "Card security code in 3-digit format."
    }
}
```

Example of a subscription object:
```json
{
    "subscriberId": 1,
    "productName": "Gold Wash",
    "unitPrice": 50,
    "quantity": 2,
    "currency": "USD",
    "frequency": "Weekly",
    "card": {
          "cardNumber": "32165498",
          "cardExpirationDate": "2021-12-13",
          "cardSecurityCode": "345"
    }
}
```

## Requirements:
Consider yourself as an employee of the company, `Hackerrank Washing Inc.` which offers Car washing services where the customers subscribe to the various washing offers offered by the company. Further suppose you are a part of team building the software solution for validating and storing the real-time subscriptions coming from the company's website. Here the subscription generated via website are sent to kafka broker service in the topic `hackerrank-washing`. 

- `Subscription` model class is the message which is sent to kafka broker where subscriberId is used as message key. 
- Producer is fully implemented but Consumer is partly implemented.

`Installation Note`: 

You can use the given setup script `setup/kafkaInstall.sh` to install and configure the above mentioned version's Kafka broker server in your local machine if you haven't already installed. Not that the install script is for Ubuntu OS and you have to install it if you are using other OS like windows, Mac OS or other distributions of Linux. If you want install Kafka yourself without using give script just make sure that the topic named `hackerrank-washing` exists and is empty.

You have to complete the implementation of following methods.

In the `SubscriptionConsumerManager` class:

- `createConsumer(Properties consumerProps)`:
    - create consumer using given consumer properties

- In the `SubscriptionConsumerLoop` class:
    - implement the consumer loop `run()` method where you have to subscribe to the topic `hackerrank-washing` and keep consuming until the `shutdown` variable is `false`.
    - poll duration needs to be 3 seconds and commit mode needs to be synchronized.
    - validate the subscriptions and store the valid in `InAppDataStore.valid` and invalid in `InAppDataStore.valid` like `InAppDataStore.valid.put(messageKey, message);`
    - `Validation rules:`
       - make sure card number is of 8 digits only.
       - card security code number is of 3 digits only.
       - expiration date is greater than current system date.
       - Payment currency is one of FJD,GBP,USD,EURO. 

Complete the implementation of above methods so that unit tests pass while running. You can make use of the given unit tests to check your progress while solving the challenge.

## Commands
- run:
```bash
mvn clean package; java -jar target/kafka-java-subscription-management-1.0.jar
```
- install:
```bash
sh setup/kafkaInstall.sh; mvn clean install
```
- test:
```bash
mvn clean test
```
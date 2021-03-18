## Environment:
- Java version: 1.8
- Maven version: 3.*
- Kafka version: kafka_2.13-2.6.0
- Kafka Java Client version: 2.6.0

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

## Installation Note:
You can use the given setup script `setup/kafkaInstall.sh` to install and configure the above mentioned version's Kafka broker server in your local machine if you haven't already installed. Not that the install script is for Ubuntu OS and you have to install it if you are using other OS like windows, Mac OS or other distributions of Linux. If you want install Kafka yourself without using give script just make sure that the topic named `hackerrank-washing` exists and is empty.

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

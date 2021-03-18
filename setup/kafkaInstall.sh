#!/bin/sh

#Install netcat as it's need later on
echo "Installing netcat"
sudo apt-get -y install netcat

#utils
waiter(){
  timeout=1
  while ! nc -z localhost $1; do
    sleep 1
    ((timeout=timeout+1))
    if [ $timeout = 10 ] ; then
          echo "Timeout after $timeout seconds"
          break
    fi
  done
}

ROOT_DIR="Kafka"

DOWNLOAD_FILE="kafka_2.13-2.6.0.tgz"
DOWNLOAD_URL="https://downloads.apache.org/kafka/2.6.0/$DOWNLOAD_FILE"

echo "Creating Kafka root dir"
mkdir $ROOT_DIR
cd $ROOT_DIR

echo "Downloading Kafka"
if [ -e "$DOWNLOAD_FILE" ]; then
    echo "$DOWNLOAD_FILE already exists"
else
    curl $DOWNLOAD_URL -o $DOWNLOAD_FILE
fi

echo "Un-taring Kafka binary"
tar -xzf $DOWNLOAD_FILE

cd kafka_2.13-2.6.0

echo "Starting Zookeeper service"
./bin/zookeeper-server-start.sh -daemon config/zookeeper.properties

echo "Waiting for Zookeeper to launch on 2181"
waiter 2181

echo "Starting Kafka Broker service"
./bin/kafka-server-start.sh -daemon config/server.properties

echo "Waiting for Kafka to launch on 9092"
waiter 9092

echo "Creating topic hackerrank-washing"
./bin/kafka-topics.sh --create --topic hackerrank-washing --bootstrap-server localhost:9092

echo "Done!"
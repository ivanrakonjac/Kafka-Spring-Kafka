# How to use

- Start docker container with kafka and zookeeper from the solution/main/resources/docker-compose.yml file.
- Run kafka-spring-kafka.jar file from build folder.
- You should **first run kafka-spring-kafka.jar** and then send data to topic because during starting of kafka-spring-kafka.jar CONSUMING_TOPIC and RESULT_TOPIC will be created.
- When kafka-spring-kafka.jar is running you should send test data to CONSUMING_TOPIC.
- You should create a consumer for the RESULT_TOPIC because the results will be sent there.
- Initial values are CONSUMING_TOPIC = "consuming-topic" and RESULT_TOPIC = "result-topic".
- The required commands are below .

## Overview

The program will automatically read data from CONSUMING_TOPIC. You are able to see logs of consumed data. When you want you can send an HTTP POST request to /groupUsersByMinutes route (http://localhost:8081/groupUsersByMinutes) and a number of unique users per minute will send to RESULT_TOPIC. You can also use other API methods (check API documentation). You can find test data here: https://tda-public.s3.eu-central-1.amazonaws.com/hire-challenge/stream.jsonl.gz.

## Commands

You can run .jar file using this command:

	java -jar kafka-spring-kafka.jar

You can start docker container from the docker-compose file using this command:

 	docker-compose -f docker-compose.yml up

You can send data to Kafka topic using this command:

 	./kafka-console-producer.sh --broker-list localhost:9092 --topic topicName

You can get data from the topic using this command:

	./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topicName --from-beginning

You can create a new Kafka topic by this command:
	
	./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic newTopicName

You can get a list of topics using this command:

	./kafka-topics.sh --list --zookeeper localhost:2181

You can send data from file to topic using this command:
	
	cat stream.jsonl | ./kafka-console-producer.sh --broker-list localhost:9092 --topic topicName

You can copy file from filesystem to docker container by this command:

	docker cp ./stream.jsonl <mycontainer>:/<location>

You can get into the docker container with this command:
	
	docker exec -it <mycontainer> bash

You can send data from container's filesystem by this command:

	cat ./stream.jsonl | $KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topicName
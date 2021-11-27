package com.springkafka;

import com.counting_mechanism.CounterMechanism;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = Producer.CONSUMING_TOPIC,groupId = "group_id", containerFactory = "modelKafkaListenerFactory")
    public void consumeMessage(Model model) throws JsonProcessingException {
        log.info("ConsumedMessage: " + model.getUid() + " | " + model.getTs());
        CounterMechanism.insert(model);
    }

//    @KafkaListener(topics = "test_topic",groupId = "group_id")
//    public void consumeMessage(String message){
//        System.out.println(message);
//    }


}

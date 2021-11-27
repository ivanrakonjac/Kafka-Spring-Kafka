package com.springkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Producer {

    public static final String CONSUMING_TOPIC = "consuming-topic";

    public static final String RESULT_TOPIC = "result-topic";

    @Autowired
    private KafkaTemplate<String, Model> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ResultResponseModel> kafkaTemplateResult;

    public void sendJSONMessage(Model model) throws JsonProcessingException {

        try {
            this.kafkaTemplate.send(CONSUMING_TOPIC, model);
        }catch (Exception exception){
            log.error("Producer::sendJSONMessage JsonProcessingException");
        }
    }

    public void sendJSONToResultTopic(ResultResponseModel response) throws JsonProcessingException {

        try {
            this.kafkaTemplateResult.send(RESULT_TOPIC, response);
        }catch (Exception exception){
            log.error("Producer::sendJSONMessage JsonProcessingException");
        }
    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(CONSUMING_TOPIC,1,(short) 1);
    }

    @Bean
    public NewTopic createResultTopic(){
        return new NewTopic(RESULT_TOPIC,1,(short) 1);
    }

//    public void sendStringMessage(String message){
//        this.kafkaTemplate.send(TOPIC,message);
//    }

}

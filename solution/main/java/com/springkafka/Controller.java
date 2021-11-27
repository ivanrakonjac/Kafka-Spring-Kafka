package com.springkafka;

import com.counting_mechanism.CounterMechanism;
import com.counting_mechanism.Minute;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;

@RestController
@Slf4j
public class Controller {

    private final Producer producer;

    @Autowired
    public Controller(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/publishJSON")
    public void messageToTopic(@RequestBody Model model) throws JsonProcessingException {
            this.producer.sendJSONMessage(model);
    }

    @PostMapping("/sendJSONToResultTopic")
    public void sendJSONToResultTopic(@RequestBody ResultResponseModel responseModel) throws JsonProcessingException {
        this.producer.sendJSONToResultTopic(responseModel);
    }

    @PostMapping("/getUsersPerSeconds")
    public int getUsersPerSeconds() {
        return CounterMechanism.getUsersPerSeconds();
    }

    @PostMapping("/getUniqueUsersBetweenTwoDates/{startUnixTime}/{endUnixTime}")
    public int getUniqueUsersBetweenTwoDates(@PathVariable  int startUnixTime ,@PathVariable int endUnixTime) {
        return CounterMechanism.getUniqueUsersBetweenTwoDates(startUnixTime, endUnixTime);
    }

    @PostMapping("/groupUsersByMinutes")
    public String groupUsersByMinutes() throws JsonProcessingException {

        Map<Minute, HashSet<String>> userToMinuteMap = CounterMechanism.groupUsersByMinutes();
        sendUserToMinuteMapToTopic(userToMinuteMap, Producer.RESULT_TOPIC);

        return "DONE";
    }

    private void sendUserToMinuteMapToTopic(Map<Minute, HashSet<String>> userToMinuteMap, String topicName) throws JsonProcessingException {
        for (Minute minute: userToMinuteMap.keySet()) {
            HashSet<String> usersInMinute = userToMinuteMap.getOrDefault(minute, null);
            if (usersInMinute != null) {
                this.producer.sendJSONToResultTopic(new ResultResponseModel(minute.toString(), usersInMinute.size()));
                log.info("SendToResultTopic: " + minute.toString(), usersInMinute.size());
            }
        }
    }

//    @PostMapping("/publishString")
//    public void messageToTopic(@RequestParam("message") String message){
//        this.producer.sendStringMessage(message);
//    }

}

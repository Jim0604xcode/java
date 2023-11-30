package io.tecky.kafkaexample.kafka;

import io.tecky.kafkaexample.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

//    @KafkaListener(topics = "kafkaTopic",groupId = "myGroup")
//    public void consumeFromString(String message){
//        logger.info(String.format("Message receive -> %s",message));
//    }
    @KafkaListener(topics = "kafkaTopic",groupId = "myGroup")
    public void consumeFromJson(User user){
        logger.info(String.format("JSON Message receive -> %s",user.toString()));
    }
}

package io.tecky.kafkaexample.kafka;

import io.tecky.kafkaexample.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);
    private KafkaTemplate<String,String> kafkaTemplate;
    public KafkaProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageWithString(String message){
        logger.info(String.format("Message sent %s",message));
        kafkaTemplate.send("kafkaTopic",message);

    }

    public void sendMessageWithJson(User data){
        logger.info(String.format("Message sent %s",data.toString()));
        kafkaTemplate.send(MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC,"kafkaTopic").build());
    }

}

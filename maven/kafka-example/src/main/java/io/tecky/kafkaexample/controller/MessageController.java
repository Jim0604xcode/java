package io.tecky.kafkaexample.controller;




import io.tecky.kafkaexample.dto.User;
import io.tecky.kafkaexample.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {
    private KafkaProducer kafkaProducer;
    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

//    @PostMapping("/publish-string")
//    public ResponseEntity<String> publishWithString(@RequestParam("message") String message){
//        kafkaProducer.sendMessageWithString(message);
//        return ResponseEntity.ok("String Message sent to the topic");
//    }
    @PostMapping("/publish-json")
    public ResponseEntity<String> publishWithJson(@RequestBody User user){
        kafkaProducer.sendMessageWithJson(user);
        return ResponseEntity.ok("Json Message sent to the topic");
    }
}

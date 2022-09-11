package com.myrental.web.kafka.controller;

import com.myrental.web.kafkamessage.AuditTrailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<Integer, AuditTrailMessage> kafkaTemplate;

    @Value(value = "${spring.kafka.template.default-topic}")
    private String topic;

    @PostMapping("/produce-audit-trail-message")
    public AuditTrailMessage produceAuditTrailMessage(@Valid @RequestBody AuditTrailMessage body) {
        try {
            System.out.printf("Sending : %s\n", body);
            kafkaTemplate.send(topic, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }
}

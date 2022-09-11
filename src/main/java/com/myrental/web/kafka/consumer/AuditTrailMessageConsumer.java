package com.myrental.web.kafka.consumer;

import com.myrental.web.kafkamessage.AuditTrailMessage;
import com.myrental.web.service.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class AuditTrailMessageConsumer {
    @Autowired
    private AuditTrailService auditTrailService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.template.default-topic}", containerFactory = "kafkaListenerContainerFactory")
    void listener(AuditTrailMessage auditTrailMessage) {
        System.out.printf("Received : %s\n", auditTrailMessage);
        try {
            auditTrailService.saveFromKafkaMessage(auditTrailMessage);
        } catch (Exception e) {
            System.out.printf("Error on kafka, saving AuditTrailMessage. " + e);
        }
    }
}

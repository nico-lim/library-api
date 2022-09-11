package com.myrental.web.service;

import com.myrental.web.kafkamessage.AuditTrailMessage;
import com.myrental.web.model.AuditTrail;
import com.myrental.web.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditTrailService {
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public AuditTrail saveFromKafkaMessage(AuditTrailMessage auditTrailMessage) {
        AuditTrail auditTrail = new AuditTrail(
                auditTrailMessage.getTableName(),
                auditTrailMessage.getTableRecordId(),
                auditTrailMessage.getAction(),
                auditTrailMessage.getOldValue(),
                auditTrailMessage.getNewValue(),
                auditTrailMessage.getAuditDate(),
                LocalDateTime.now()
        );

        auditTrailRepository.save(auditTrail);

        return auditTrail;
    }
}

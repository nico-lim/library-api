package com.myrental.web.dto;

import com.myrental.web.enumeration.AuditTrailActionEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateAuditTrail {
    private String tableName;
    private String tableRecordId;
    private AuditTrailActionEnum auditTrailActionEnum;
    private Object oldValue;
    private Object newValue;
    private LocalDateTime auditDate;
}

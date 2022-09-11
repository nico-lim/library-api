package com.myrental.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myrental.web.enumeration.AuditTrailActionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_trails")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuditTrail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "table_record_id", nullable = false)
    private String tableRecordId;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private AuditTrailActionEnum action;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "audit_date")
    private LocalDateTime auditDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AuditTrail(String tableName, String tableRecordId, AuditTrailActionEnum action, String oldValue, String newValue, LocalDateTime auditDate, LocalDateTime createdAt) {
        this.tableName = tableName;
        this.tableRecordId = tableRecordId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.auditDate = auditDate;
        this.createdAt = createdAt;
    }
}

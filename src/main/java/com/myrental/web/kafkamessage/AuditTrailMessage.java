package com.myrental.web.kafkamessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myrental.web.enumeration.AuditTrailActionEnum;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrailMessage {
    @JsonProperty("table_name")
    @NotNull
    private String tableName;

    @JsonProperty("table_record_id")
    @NotNull
    private String tableRecordId;

    @JsonProperty("action")
    @NotNull
    private AuditTrailActionEnum action;

    @JsonProperty("old_value")
    @NotNull
    private String oldValue;

    @JsonProperty("new_value")
    @NotNull
    private String newValue;

    @JsonProperty("audit_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime auditDate;
}

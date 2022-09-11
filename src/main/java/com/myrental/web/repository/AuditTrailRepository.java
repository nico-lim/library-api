package com.myrental.web.repository;

import com.myrental.web.model.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
}

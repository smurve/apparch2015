package org.smurve.hsr2014.repo;

import org.smurve.hsr2014.domain.AuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface AuditRecordRepository extends JpaRepository<AuditRecord, String> {

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AuditRecord> findByUser(String user);
}

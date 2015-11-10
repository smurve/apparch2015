package org.smurve.hsr2014.repo.gents;

import org.smurve.hsr2014.domain.gents.AttributeMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AttributeMetaDateRepository extends JpaRepository<AttributeMetaData, String> {
}

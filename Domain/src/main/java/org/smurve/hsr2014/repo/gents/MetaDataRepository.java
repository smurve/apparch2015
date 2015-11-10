package org.smurve.hsr2014.repo.gents;

import org.smurve.hsr2014.domain.gents.GentsMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MetaDataRepository extends JpaRepository<GentsMetaData, String> {
}

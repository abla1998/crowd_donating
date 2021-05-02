package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {

}

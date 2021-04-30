package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.Sponsor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Page<Sponsor> findByNameLike(String name, Pageable pageable);
}

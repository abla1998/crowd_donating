package org.mql.crowddonating.dao;


import java.util.List;

import org.mql.crowddonating.models.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
    Page<Case> findByNameLikeAndDisabledFalse(String name, Pageable pageable);
    Case findBySlugAndDisabledFalse(String slug);
//	List<Case> findFirst3ByDisabledFalseAndOrderByIdDesc();
	List<Case> findFirst3ByDisabledFalseOrderByIdDesc();
	Page<Case> findAllByDisabledFalse(Pageable pageable);
}

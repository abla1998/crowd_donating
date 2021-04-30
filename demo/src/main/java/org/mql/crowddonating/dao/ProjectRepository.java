package org.mql.crowddonating.dao;

import java.util.List;

import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Project findBySlug(String slug);
	List<Project> findFirst3ByDisabledFalseOrderByIdDesc();

}

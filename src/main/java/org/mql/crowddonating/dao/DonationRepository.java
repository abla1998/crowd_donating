package org.mql.crowddonating.dao;

import java.util.List;

import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
	
	List<Donation> findByACase(Case aCase);
	List<Donation> findByProject(Project project);

}

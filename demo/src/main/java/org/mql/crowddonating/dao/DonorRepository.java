package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
	Donor findByEmailIgnoreCase(String emailId);
	Donor getByUsername(String username);
}

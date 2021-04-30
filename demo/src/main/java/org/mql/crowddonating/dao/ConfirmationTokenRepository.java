package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;


public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}

package org.mql.crowddonating.business;

import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;

public interface IDonorBusiness extends IUserServices{
	/* *** donations *** */
	Donation addDonation(Donation donation);
	
	/* *** for signup *** */
	void signup(Donor donor);
	void mailConfirmation(Donor donor);
	Boolean confirmation(String confirmationToken);

	Donor getByUsername(String username);
}

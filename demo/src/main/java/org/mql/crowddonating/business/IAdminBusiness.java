package org.mql.crowddonating.business;

import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.Sponsor;

import java.util.List;

public interface IAdminBusiness extends IUserServices{
	
	/* *** users *** */
	void banUser(long id);
	boolean banUser(long id, boolean state);

	/* *** sponsors *** */
	Sponsor addSponsor(Sponsor sponsor);
	void deleteSponsor(Sponsor sponsor);
	Sponsor updateSponsor(Sponsor sponsor);
	
	/* *** domains *** */
	void deleteDomain(long id);
	
	/* *** case's types *** */
	void deleteType(long id);

	/*** valider association **/
	void validateAssociation(long id);

	List<Donor> getAllDonators();
	List<Case> getAllCases();
}

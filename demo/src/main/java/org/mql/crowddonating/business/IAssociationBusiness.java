package org.mql.crowddonating.business;

import java.util.List;

import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Domain;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.Event;
import org.mql.crowddonating.models.Project;
import org.mql.crowddonating.models.Type;

public interface IAssociationBusiness extends IUserServices{
	
	/* *** cases *** */
    Case addCase(Case aCase);
    Case updateCase(Case aCase);
    void deleteCase(long id);
    boolean disableCase(long id, boolean state);
    
    /* *** cases types *** */
    Type addType(Type type);
    Type findTypeByLabel(String label);
    
    /* *** donations *** */
    List<Donation> getDonationByCase(long id);
    
    /* *** donors *** */
    List<Donor> getDonorByCase(long id);
    
    /* *** events *** */
    Event addEvent(Event event);
    Event updateEvent(Event event);
    void deleteEvent(long id);
    
    /* *** association's domains *** */
    Domain addDomain(Domain domain);
    
	/* *** for signup *** */
	void signup(Association association);

	Project deleteProject(long id);
	String addProject(Project project);
	String updateProject(Project project);

	boolean disableProject(long id, boolean state);
	boolean urgentACase(long id, boolean state);

	
}

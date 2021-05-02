package org.mql.crowddonating.business;

import java.util.List;
import java.util.Map;

import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Domain;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.Event;
import org.mql.crowddonating.models.Project;
import org.mql.crowddonating.models.Sponsor;
import org.mql.crowddonating.models.Type;
import org.mql.crowddonating.models.User;
import org.springframework.data.domain.Page;


public interface IPublicServices {

    /* *** Cases *** */
    Page<Case> getAllCases(int page, int size);

    Case getCaseById(long id);

    Page<Case> getCasesByName(String name, int page, int size);
    List<Case> getCasesByAssociation(Association association);
    
    Case getCaseBySlug(String slug);

    List<Type> getAllTypes();

    List<Donation> getCaseDonating(Case aCase);

    /* *** user *** */
    @Deprecated
    Association addAssociation(Association association);

    @Deprecated
    Donor addDonor(Donor donor);

    User findUserByUsername(String userName);

    /* *** associations *** */
    List<Association> getAllAssociations();

    List<Association> getAssociationsByDomain();

    List<Association> getAssociationsByName();

    User getAssociationById(long id);

    /* *** events *** */
    List<Event> getAllEvents();

    Event getEventById(long id);

    List<Event> getEventByName(String name);


    /* *** sponsors *** */
    List<Sponsor> getAllSponsors();

    Sponsor getSponsorById(long id);

    Page<Sponsor> getSponsorByName(String name, int page, int size);

    /* *** domains *** */
    List<Domain> getAllDomains();

    /* *** types *** */
    Type getTypeById(long id);


    /* *** Donations *** */
    Donation getDonationById(long id);

    Map<String, Object> globalStats();

    List<Case> findLastNCases();

    List<Event> getLastNEvents();

	List<Case> getCasesByAssociation(long id);
	
	public Map<String, Object> globalStatsForAssociation(List<Case> cases);
	
	List<Event> getEventsByAssociation(Association association);
	
	List<Project> getAllProject();
    Project getProject(String slug);
    Project getProjectById(long id);
	List<Project> getProjects(String name);

	List<Project> getProjectsByAssociation(Association association);

	List<Project> findLastNProjects();

	Map<String, Object> projectGlobalStats();
	Map<String, Object> globalProjectsStatsForAssociation(Association association);

}

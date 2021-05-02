package org.mql.crowddonating.business.implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.dao.AssociationRepository;
import org.mql.crowddonating.dao.CaseRepository;
import org.mql.crowddonating.dao.DomainRepository;
import org.mql.crowddonating.dao.SponsorRepository;
import org.mql.crowddonating.dao.EventRepository;
import org.mql.crowddonating.dao.ProjectRepository;
import org.mql.crowddonating.dao.DonationRepository;
import org.mql.crowddonating.dao.DonorRepository;
import org.mql.crowddonating.dao.TypeRepository;
import org.mql.crowddonating.dao.UserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublicServicesBusiness implements IPublicServices {

	@Autowired
	private CaseRepository caseDao;

	@Autowired
	private TypeRepository typeDao;

	@Autowired
	private UserRepository userDao;

	@Autowired
	private DonorRepository danatorDao;

	@Autowired
	private SponsorRepository sponsorDao;

	@Autowired
	private AssociationRepository associationDao;

	@Autowired
	private EventRepository eventDao;

	@Autowired
	private DonationRepository donationDao;

	@Autowired
	private DomainRepository domainDao;

	@Autowired
	private ProjectRepository projectDao;

	public Page<Case> getAllCases(int page, int size) {
		Page<Case> pageCases = caseDao.findAllByDisabledFalse(PageRequest.of(page, size));

		for (Case aCase : pageCases) {
			aCase = calculCaseStats(aCase);
		}

		return pageCases;
	}

	private Case calculCaseStats(Case aCase) {
		double totalRaised = 0;
		double percentageRased = 0;

		List<Donation> donations = donationDao.findByACase(aCase);
		for (Donation donation : donations) {
			totalRaised += donation.getAmount();
		}
		aCase.setAmountRaised(totalRaised);
		aCase.setNbreDonations(donations.size());
		aCase.setPercentageRaised(totalRaised * 100 / aCase.getAmount());
		return aCase;
	}

	public Map<String, Object> globalStats() {
		Map<String, Object> stats = new HashMap<>();

		double globalAmount = 0;
		double globalRaised = 0;
		double percentageRaised = 0;
		int globalNbreDonations = 0;
		int solvedCauses = 0;

		List<Case> cases = caseDao.findAll();
		for (Case aCase : cases) {
			globalAmount += aCase.getAmount();
			globalNbreDonations += aCase.getNbreDonations();

			aCase = calculCaseStats(aCase);
			if (aCase.getAmountRaised() >= aCase.getAmount())
				solvedCauses++;

			globalRaised += aCase.getAmountRaised();

		}

		stats.put("globalAmount", globalAmount);
		stats.put("globalRaised", globalRaised);
		stats.put("percentageRaised", globalRaised * 100 / globalAmount);
		stats.put("globalTotalDonations", globalNbreDonations);
		stats.put("nbreDonators", donationDao.findAll().size());
		stats.put("solvedCauses", solvedCauses);
		return stats;
	}

	public Map<String, Object> globalStatsForAssociation(List<Case> cases) {
		Map<String, Object> stats = new HashMap<>();

		double globalAmount = 0;
		double globalRaised = 0;
		double percentageRaised = 0;
		int globalNbreDonations = 0;
		int solvedCauses = 0;

		for (Case aCase : cases) {
			globalAmount += aCase.getAmount();
			globalNbreDonations += aCase.getNbreDonations();

			aCase = calculCaseStats(aCase);
			if (aCase.getAmountRaised() >= aCase.getAmount())
				solvedCauses++;

			globalRaised += aCase.getAmountRaised();

		}

		stats.put("globalAmount", globalAmount);
		stats.put("globalRaised", globalRaised);
		stats.put("percentageRaised", globalRaised * 100 / globalAmount);
		stats.put("globalTotalDonations", globalNbreDonations);
		stats.put("nbreDonators", donationDao.findAll().stream().filter(d -> d.getProject() == null).collect(Collectors.toList()).size());
		stats.put("solvedCauses", solvedCauses);
		return stats;
	}

	public Case getCaseById(long id) {
		Case aCase = caseDao.findById(id).get();

		aCase = calculCaseStats(aCase);

		return aCase;
	}

	public Page<Case> getCasesByName(String name, int page, int size) {
		Page<Case> pageCases = caseDao.findByNameLikeAndDisabledFalse(name, PageRequest.of(page, size));
		for (Case aCase : pageCases) {
			aCase = calculCaseStats(aCase);
		}
		return pageCases;
	}

	@Override
	public Case getCaseBySlug(String slug) {
		Case aCase = caseDao.findBySlugAndDisabledFalse(slug);
		if (aCase == null)
			return null;
		aCase = calculCaseStats(aCase);
		return aCase;
	}

	public List<Type> getAllTypes() {
		return typeDao.findAll();
	}

	@Override
	public Association addAssociation(Association association) {
		return userDao.save(association);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventDao.findAll();
	}

	@Override
	public Event getEventById(long id) {
		return eventDao.getOne(id);
	}

	@Override
	public List<Event> getEventByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sponsor> getAllSponsors() {
		return sponsorDao.findAll();
	}

	@Override
	public Sponsor getSponsorById(long id) {
		return sponsorDao.getOne(id);
	}

	@Override
	public List<Association> getAllAssociations() {
		return associationDao.findAll();
	}

	@Override
	public List<Association> getAssociationsByDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Association> getAssociationsByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getAssociationById(long id) {
		return userDao.findById(id).get();
	}

	@Override
	public List<Domain> getAllDomains() {
		return domainDao.findAll();
	}

	@Override
	public Type getTypeById(long id) {
		return typeDao.getOne(id);
	}

	@Override
	public Page<Sponsor> getSponsorByName(String name, int page, int size) {
		return sponsorDao.findByNameLike(name, PageRequest.of(page, size));
	}

	public Donor addDonor(Donor donor) {
		return userDao.save(donor);
	}

	@Override
	public List<Donation> getCaseDonating(Case aCase) {
		return donationDao.findByACase(aCase);
	}

	@Override
	public Donation getDonationById(long id) {
		return donationDao.findById(id).get();
	}

	@Override
	public User findUserByUsername(String userName) {
		return userDao.findByUsername(userName);
	}

	@Override
	public List<Case> findLastNCases() {
		return caseDao.findFirst3ByDisabledFalseOrderByIdDesc();
	}

	@Override
	public List<Event> getLastNEvents() {
		return eventDao.findFirst4ByOrderByIdDesc();
	}

	@Override
	public List<Case> getCasesByAssociation(long id) {
		List<Case> cases = caseDao.findAll().stream().filter(aCase -> aCase.getAssociation().getId() == id)
				.collect(Collectors.toList());
		return cases;
	}

	@Override
	public List<Case> getCasesByAssociation(Association association) {

		List<Case> cases = caseDao.findAll().stream().filter(c -> c.getAssociation().getId() == association.getId())
				.collect(Collectors.toList());

		return cases;
	}

	@Override
	public List<Event> getEventsByAssociation(Association association) {

		List<Event> events = eventDao.findAll().stream().filter(c -> c.getAssociation().getId() == association.getId())
				.collect(Collectors.toList());

		return events;

	}

	@Override
	public List<Project> getAllProject() {
		List<Project> projects = projectDao.findAll();
		for (Project project : projects) {
			calculProjectStats(project);
		}
		return projects;
	}

	private List<Project> getProjectStatus(List<Project> projects) {

		double totalRaised = 0;

		for (Project project : projects) {
			
			project.getDonations().stream().mapToDouble(d -> d.getAmount()).sum();
			
			project.setAmountRaised(totalRaised);
			project.setNbreDonations(project.getDonations().size());
			project.setPercentageRaised(totalRaised * 100 / project.getAmount());

		}

		return projects;
	}

	@Override
	public Project getProject(String slug) {
		return calculProjectStats(projectDao.findBySlug(slug));
	}

	@Override
	public Project getProjectById(long id) {
		return projectDao.getOne(id);
	}

	@Override
	public List<Project> getProjects(String name) {
		return projectDao.findAll().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public List<Project> getProjectsByAssociation(Association association) {
		List<Project> projects = projectDao.findAll().stream()
				.filter(p -> p.getAssociation().getId() == association.getId()).collect(Collectors.toList());
		return projects;
	}

	@Override
	public List<Project> findLastNProjects() {
		return getProjectStatus(projectDao.findFirst3ByDisabledFalseOrderByIdDesc());
	}

	@Override
	public Map<String, Object> projectGlobalStats() {
		
		
		
		Map<String, Object> stats = new HashMap<>();

		double p_globalAmount = 0;
		double p_globalRaised = 0;
		int p_globalNbreDonations = 0;
		int p_solvedProjects = 0;

		List<Project> projects =  getAllProject();
	
		
		for (Project project : projects) {
			p_globalAmount += project.getAmount();
			p_globalNbreDonations += project.getNbreDonations();

			if (project.getAmountRaised() >= project.getAmount())
				 p_solvedProjects++;

			p_globalRaised += project.getAmountRaised();

		}

		stats.put("globalAmount", p_globalAmount);
		stats.put("globalRaised", p_globalRaised);
		stats.put("percentageRaised", p_globalRaised * 100 / p_globalAmount);
		stats.put("globalTotalDonations", p_globalNbreDonations);
		stats.put("nbreDonators", donationDao.findAll().stream().filter(d -> d.getCase() == null).collect(Collectors.toList()).size());
		stats.put("solvedProjects", p_solvedProjects);
		return stats;
	}
	
	public Map<String, Object> globalProjectsStatsForAssociation(Association association) {
		Map<String, Object> stats = new HashMap<>();

		double p_globalAmount = 0;
		double p_globalRaised = 0;
		int p_globalNbreDonations = 0;
		int p_solvedProjects = 0;

		List<Project> projects =  getProjectsByAssociation(association);
	
		
		for (Project project : projects) {
			p_globalAmount += project.getAmount();
			p_globalNbreDonations += project.getNbreDonations();

			if (project.getAmountRaised() >= project.getAmount())
				 p_solvedProjects++;

			p_globalRaised += project.getAmountRaised();

		}

		stats.put("globalAmount", p_globalAmount);
		stats.put("globalRaised", p_globalRaised);
		stats.put("percentageRaised", p_globalRaised * 100 / p_globalAmount);
		stats.put("globalTotalDonations", p_globalNbreDonations);
		stats.put("nbreDonators", donationDao.findAll().size());
		stats.put("solvedCauses", p_solvedProjects);
		return stats;
	}
	
	private Project calculProjectStats(Project project) {
		double totalRaised = 0;
		double percentageRased = 0;

		List<Donation> donations = donationDao.findByProject(project);
		for (Donation donation : donations) {
			totalRaised += donation.getAmount();
		}
		project.setAmountRaised(totalRaised);
		project.setNbreDonations(donations.size());
		project.setPercentageRaised(totalRaised * 100 / project.getAmount());
		return project;
	}

}

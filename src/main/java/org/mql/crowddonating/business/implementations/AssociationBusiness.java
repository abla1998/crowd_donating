package org.mql.crowddonating.business.implementations;

import org.mql.crowddonating.business.IAssociationBusiness;
import org.mql.crowddonating.dao.AssociationRepository;
import org.mql.crowddonating.dao.CaseRepository;
import org.mql.crowddonating.dao.DomainRepository;
import org.mql.crowddonating.dao.EventRepository;
import org.mql.crowddonating.dao.ProjectRepository;
import org.mql.crowddonating.dao.RoleRepository;
import org.mql.crowddonating.dao.TypeRepository;
import org.mql.crowddonating.models.*;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class AssociationBusiness extends UserBusiness implements IAssociationBusiness {

	@Autowired
	private CaseRepository caseDao;

	@Autowired
	private TypeRepository typeDao;

	@Autowired
	private EventRepository eventDao;

	@Autowired
	private RoleRepository roleDao;
	@Autowired
	private AssociationRepository associationDao;

	@Autowired
	private DomainRepository domainDao;

	@Autowired
	private ProjectRepository projectDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Case addCase(Case aCase) {
		aCase.setName(Utility.cleanupSpaces(aCase.getName()));
		aCase.setDescription(Utility.cleanupSpaces(aCase.getDescription()));
		aCase.setSlug(Utility.toSlug(aCase.getName()));
		return caseDao.save(aCase);
	}

	public Case updateCase(Case aCase) {
		aCase.setName(Utility.cleanupSpaces(aCase.getName()));
		aCase.setDescription(Utility.cleanupSpaces(aCase.getDescription()));
		return caseDao.save(aCase);
	}

	@Override
	public void deleteCase(long id) {
		caseDao.deleteById(id);
	}

	@Override
	public boolean disableCase(long id, boolean state) {
		Case aCase = caseDao.findById(id).get();
		aCase.setDisabled(state);
		caseDao.save(aCase);
		return state;
	}

	public Type addType(Type type) {
		return typeDao.save(type);
	}

	public Type findTypeByLabel(String label) {
		return typeDao.findByLabel(label);
	}

	@Override
	public List<Donation> getDonationByCase(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donor> getDonorByCase(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event addEvent(Event event) {
		event.setTitle(Utility.cleanupSpaces(event.getTitle()));
		event.setDescription(Utility.cleanupSpaces(event.getDescription()));
		return eventDao.save(event);
	}

	@Override
	public Event updateEvent(Event event) {
		event.setTitle(Utility.cleanupSpaces(event.getTitle()));
		event.setDescription(Utility.cleanupSpaces(event.getDescription()));
		return eventDao.save(event);
	}

	@Override
	public void deleteEvent(long id) {
		eventDao.deleteById(id);
	}

	@Override
	public Domain addDomain(Domain domain) {
		return domainDao.save(domain);
	}

	@Override
	public void signup(Association association) {
		association.addRole(roleDao.findByRole("ASSOCIATION"));
		association.setAvatar("cover.jpg");
		association.setPassword(bCryptPasswordEncoder.encode(association.getPassword()));
		associationDao.save(association);
	}
		@Override
		public Project deleteProject(long id) {
			Project p = projectDao.findById(id).orElse(null);
			projectDao.deleteById(id);
			return p;
		}

	@Override
	public String addProject(Project project) {
		project.setName(Utility.cleanupSpaces(project.getName()));
		project.setDescription(Utility.cleanupSpaces(project.getDescription()));
		project.setSlug(Utility.toSlug(project.getName()));
		projectDao.save(project);
		return project.getSlug();
	}

	@Override
	public String updateProject(Project project) {
		return addProject(project);
	}
  
	@Override
	public boolean disableProject(long id, boolean state) {
		Project project = projectDao.findById(id).get();
		project.setDisabled(state);
		projectDao.save(project);
		return state;
	}

	@Override
	public boolean urgentACase(long id, boolean state) {
		Case c = caseDao.findById(id).get();
		c.setUrgent(state);
		caseDao.save(c);
		return state;
	}
}

package org.mql.crowddonating.business.implementations;

import javax.transaction.Transactional;

import org.mql.crowddonating.business.IAdminBusiness;
import org.mql.crowddonating.dao.*;

import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.Sponsor;
import org.mql.crowddonating.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminBusiness extends UserBusiness implements IAdminBusiness {

    @Autowired
    private SponsorRepository sponsorDao;
    @Autowired
    private DomainRepository domainDao;
    @Autowired
    private TypeRepository typeDao;
    @Autowired
    private UserRepository userDao;
    @Autowired
    private DonorRepository donorDao;
    @Autowired
    private CaseRepository caseDao;

    @Override
    public void banUser(long id) {
        User user = userDao.getOne(id);
        user.setBanned(true);
        userDao.save(user);
    }

    @Override
    public boolean banUser(long id, boolean state) {
        User user = userDao.getOne(id);
        user.setBanned(state);
        userDao.save(user);
        return state;
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        return sponsorDao.save(sponsor);
    }

    @Override
    public void deleteSponsor(Sponsor sponsor) {
        sponsorDao.delete(sponsor);
    }

    @Override
    public Sponsor updateSponsor(Sponsor sponsor) {
        return sponsorDao.save(sponsor);
    }

    @Override
    public void deleteDomain(long id) {
        domainDao.deleteById(id);
    }

    @Override
    public void deleteType(long id) {
        typeDao.deleteById(id);
    }

    @Override
    public List<Donor> getAllDonators() {
        return donorDao.findAll();
    }

    @Override
    public void validateAssociation(long id) {
        User user = userDao.getOne(id);
        user.setEnabled(true);
        userDao.save(user);
    }

    @Override
    public List<Case> getAllCases() {
        return caseDao.findAll();
    }

}

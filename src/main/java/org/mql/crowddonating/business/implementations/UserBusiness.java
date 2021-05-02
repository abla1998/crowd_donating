package org.mql.crowddonating.business.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.mql.crowddonating.business.IUserServices;
import org.mql.crowddonating.dao.AssociationRepository;
import org.mql.crowddonating.dao.DonorRepository;
import org.mql.crowddonating.dao.FileRepository;
import org.mql.crowddonating.dao.UserRepository;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.models.User;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Transactional
public class UserBusiness extends PublicServicesBusiness implements IUserServices {
    @Autowired
    private FileRepository fileDao;

    @Autowired
    private DonorRepository donorDao;

    @Autowired
    private UserRepository userDao;
    
    @Autowired
    private AssociationRepository associationDao;

    @Override
    public File saveFile(File file) {
        return fileDao.save(file);
    }

    @Override
    public void deleteFile(long id, String repository) {
        File file = fileDao.findById(id).get();
        Path filePath = Paths.get(Utility.uploadDir, repository, file.getPath());
        System.out.println("Deleting  : " + Utility.uploadDir + repository + file.getPath());
        try {
            Files.delete(filePath);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        fileDao.deleteById(id);
    }

    @Override
    public Donor getDonorById(long id) {
        return donorDao.getOne(id);
    }

    @Override
    public Donor getDonorByUsername(String username) {
        return (Donor) userDao.findByUsername(username);
    }

    @Override
    public Association getAssociationById(long id) {
        return associationDao.getOne(id);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userDao.findByEmailIgnoreCase(email);
    }

	@Override
	public Association getAssociationByUserName(String userName) {
		return associationDao.findByUsername(userName);
	}
}

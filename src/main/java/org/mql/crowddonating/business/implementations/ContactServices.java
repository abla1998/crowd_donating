package org.mql.crowddonating.business.implementations;

import org.mql.crowddonating.business.IContactServices;
import org.mql.crowddonating.dao.CantactRepository;
import org.mql.crowddonating.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContactServices implements IContactServices {

    @Autowired
    private CantactRepository contactDao;

    @Override
    public void add(Contact contact) {
        contactDao.save(contact);
    }

    @Override
    public Contact delete(long id) {
        Contact contact = contactDao.findById(id).orElse(null);
        contactDao.delete(contact);
        return contact;
    }

    @Override
    public List<Contact> getAll() {
        return contactDao.findAllByOrderByPostedDateDesc();
    }

    @Override
    public Contact get(long id) {
        return contactDao.findById(id).orElse(null);
    }
}

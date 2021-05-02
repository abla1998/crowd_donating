package org.mql.crowddonating.business;

import org.mql.crowddonating.models.Contact;

import java.util.List;

public interface IContactServices {

    public void add(Contact contact);

    public Contact delete(long id);

    public List<Contact> getAll();

    public Contact get(long id);

}

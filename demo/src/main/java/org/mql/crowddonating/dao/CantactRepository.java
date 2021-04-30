package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CantactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByPostedDateDesc();
}

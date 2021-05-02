package org.mql.crowddonating.dao;

import java.util.List;

import org.mql.crowddonating.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findFirst4ByOrderByIdDesc();

}

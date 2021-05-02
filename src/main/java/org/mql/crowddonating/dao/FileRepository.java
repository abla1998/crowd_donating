package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}

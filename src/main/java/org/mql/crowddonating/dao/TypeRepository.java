package org.mql.crowddonating.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.mql.crowddonating.models.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByLabel(String label);
}

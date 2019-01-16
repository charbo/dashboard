package org.dashboard.back.repository;

import org.dashboard.back.model.Query;
import org.dashboard.back.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
}

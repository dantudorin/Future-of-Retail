package com.infosys.admin.repository;

import com.infosys.admin.model.CustomerTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTrackerRepository extends JpaRepository<CustomerTracker, Long> {
}

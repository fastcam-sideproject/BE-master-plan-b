package com.example.masterplanbbe.domain.jobRole.repository;

import com.example.masterplanbbe.domain.jobRole.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
}

package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
    Optional<JobRole> findByJobRoleName(String jobRoleName);
}

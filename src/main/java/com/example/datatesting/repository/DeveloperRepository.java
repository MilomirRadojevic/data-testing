package com.example.datatesting.repository;

import com.example.datatesting.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}

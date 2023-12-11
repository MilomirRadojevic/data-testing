package com.example.datatesting.repository;

import com.example.datatesting.entity.Organization;
import java.util.Optional;

public interface CustomOrganizationRepository {
    Optional<Organization> findByIdViaEntityManager(Long id);

    Optional<Organization> findByIdViaSession(Long id);
}

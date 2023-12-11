package com.example.datatesting.repository;

import com.example.datatesting.entity.Organization;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.hibernate.Session;

public class CustomOrganizationRepositoryImpl implements CustomOrganizationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Organization> findByIdViaEntityManager(Long id) {
        if (id == null) {
            throw new RuntimeException("Demonstration error");
        }

        return Optional.ofNullable(
            entityManager.find(Organization.class, id)
        );
    }

    @Override
    public Optional<Organization> findByIdViaSession(Long id) {
        if (id == null) {
            throw new RuntimeException("Demonstration error");
        }

        Session session = entityManager.unwrap(Session.class);

        return Optional.ofNullable(
            session.get(Organization.class, id)
        );
    }
}

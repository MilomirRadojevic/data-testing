package com.example.datatesting.service;

import com.example.datatesting.entity.Organization;
import com.example.datatesting.repository.OrganizationRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DirectFetchingService extends PersistenceContextInspectingService {
    // Essentially, we want to show session level repeatable read functionality provided by hibernate
    //  and to see how it behaves with direct fetching, fetching by explicit queries nad fetching projections

    private final OrganizationRepository organizationRepository;

    private static final Long ORGANIZATION_ID = 1L;
    private static final String ORGANIZATION_OLD_NAME = "org old name";
    private static final String ORGANIZATION_NEW_NAME = "org new name";
    private static final List<Long> MULTIPLE_ORGANIZATION_IDS = List.of(2L, 3L);

    public DirectFetchingService(EntityManager entityManager, OrganizationRepository organizationRepository) {
        super(entityManager);
        this.organizationRepository = organizationRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addOrganizations() {
        Organization o1 = new Organization();
        o1.setName(ORGANIZATION_OLD_NAME);

        Organization o2 = new Organization();
        o2.setName("org 2 name");

        Organization o3 = new Organization();
        o3.setName("org 3 name");

        organizationRepository.save(o1);
        organizationRepository.save(o2);
        organizationRepository.save(o3);
    }

    @Transactional(readOnly = true)
    public void fetchById() {
        Organization organization = organizationRepository.findById(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organization);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void fetchByIdAndUpdateName() {
        Organization organization = organizationRepository.findById(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        organization.setName(ORGANIZATION_NEW_NAME);
    }

    @Transactional(readOnly = true)
    public void fetchWithSessionLevelRepeatableRead() {
        // Direct methods fetch until the entity is found in the following order: from current context, from second level cache, from database
        Organization organizationViaSpringData = organizationRepository.findById(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organizationViaSpringData);

        Organization organizationViaEntityManager = organizationRepository.findByIdViaEntityManager(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organizationViaEntityManager);

        Organization organizationViaSession = organizationRepository.findByIdViaSession(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organizationViaSession);

        // These two will fetch in the following order: second level cache, database (but will ignore the result if the entity is present in current context)
        Organization organizationViaExplicitJPQL = organizationRepository.findByIdViaExplicitJPQL(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organizationViaExplicitJPQL);

        Organization organizationViaExplicitNativeQuery = organizationRepository.findByIdViaExplicitNativeQuery(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organizationViaExplicitNativeQuery);

        // Projections always load data from database
        String organizationNameViaExplicitJPQL = organizationRepository.fetchNameByIdViaExplicitJPQL(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: name: {}", organizationNameViaExplicitJPQL);

        String organizationNameViaExplicitNativeQuery = organizationRepository.fetchNameByIdViaExplicitNativeQuery(ORGANIZATION_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: name: {}", organizationNameViaExplicitNativeQuery);
    }

    @Transactional(readOnly = true)
    public void fetchMultipleByIds() {
        // These two will fetch in the following order: second level cache, database (but will ignore the result if the entity is present in current context)
        List<Organization> organizationsViaSpringData = organizationRepository.findAllById(MULTIPLE_ORGANIZATION_IDS);
        log.info("Organizations: {}", organizationsViaSpringData);

        List<Organization> organizationsViaExplicitJPQL = organizationRepository.fetchAllByIds(MULTIPLE_ORGANIZATION_IDS);
        log.info("Organizations: {}", organizationsViaExplicitJPQL);
    }

    @Transactional
    public void fetchByNameReadWrite() {
        briefOverviewOfPersistentContextContent();
        Organization organization = organizationRepository.findByName(ORGANIZATION_OLD_NAME)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organization);
        briefOverviewOfPersistentContextContent();
    }

    @Transactional(readOnly = true)
    public void fetchByNameReadOnly() {
        briefOverviewOfPersistentContextContent();
        Organization organization = organizationRepository.findByName(ORGANIZATION_OLD_NAME)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization: {}", organization);
        briefOverviewOfPersistentContextContent();
    }
}

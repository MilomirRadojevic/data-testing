package com.example.datatesting.service;

import com.example.datatesting.dto.OrganizationClassBasedProjection;
import com.example.datatesting.dto.OrganizationDtoCustomTransformation;
import com.example.datatesting.dto.OrganizationDtoNestedClosedProjection;
import com.example.datatesting.dto.OrganizationDtoSimpleClosedProjection;
import com.example.datatesting.dto.OrganizationInterfaceBasedProjection;
import com.example.datatesting.dto.OrganizationTransformer;
import com.example.datatesting.dto.PersonDtoNestedClosedProjection;
import com.example.datatesting.dto.PersonDtoSimpleClosedProjection;
import com.example.datatesting.dto.PersonDtoSimpleOpenProjection;
import com.example.datatesting.entity.Organization;
import com.example.datatesting.entity.Person;
import com.example.datatesting.repository.OrganizationRepository;
import com.example.datatesting.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ProjectionFetchingService extends PersistenceContextInspectingService {
    private final OrganizationRepository organizationRepository;
    private final PersonRepository personRepository;
    private final OrganizationTransformer organizationTransformer;

    private static final String GOOD_MOTTO = "good motto";
    private static final String EVIL_MOTTO = "evil motto";

    public ProjectionFetchingService(EntityManager entityManager, OrganizationRepository organizationRepository,
                                     PersonRepository personRepository, OrganizationTransformer organizationTransformer) {
        super(entityManager);
        this.organizationRepository = organizationRepository;
        this.personRepository = personRepository;
        this.organizationTransformer = organizationTransformer;
    }

    @Transactional
    public void addOrganizationsAndPeople() {
        // There is a lot more way to use projection fetching, and hopefully, I will add examples soon

        Organization o1 = new Organization();
        o1.setName("org 1 name");
        o1.setSuperSecretName("org 1 super secret name");
        o1.setMotto(GOOD_MOTTO);

        Organization o2 = new Organization();
        o2.setName("org 2 name");
        o2.setSuperSecretName("org 2 super secret name");
        o2.setMotto(GOOD_MOTTO);

        Organization o3 = new Organization();
        o3.setName("org 3 name");
        o3.setSuperSecretName("org 3 super secret name");
        o3.setMotto(EVIL_MOTTO);

        Person person1 = new Person();
        person1.setName("p1 name");
        person1.setNickname("p1 nickname");

        Person person2 = new Person();
        person2.setName("p2 name");
        person2.setNickname("p2 nickname");

        o1.addPerson(person1);
        o1.addPerson(person2);

        organizationRepository.save(o1);
        organizationRepository.save(o2);
        organizationRepository.save(o3);
    }

    public void findAllByMotto() {
        // We can fetch the same projection using generated method and both explicit jpql and native query
        List<OrganizationInterfaceBasedProjection> organizations = organizationRepository.findAllByMotto(GOOD_MOTTO);
        List<OrganizationInterfaceBasedProjection> organizationsViaJPQL = organizationRepository.findAllByMottoViaExplicitJPQL(GOOD_MOTTO);
        List<OrganizationInterfaceBasedProjection> organizationsViaNativeQuery = organizationRepository.findAllByMottoViaExplicitNativeQuery(GOOD_MOTTO);

        organizations.forEach(
            organization -> log.info("Organization: name: {}; super secret name: {}", organization.getName(), organization.getSuperSecretName())
        );
        organizationsViaJPQL.forEach(
            organization -> log.info("Organization: name: {}; super secret name: {}", organization.getName(), organization.getSuperSecretName())
        );
        organizationsViaNativeQuery.forEach(
            organization -> log.info("Organization: name: {}; super secret name: {}", organization.getName(), organization.getSuperSecretName())
        );
    }

    public void findDynamicProjectionsByMotto() throws JsonProcessingException {
        // dynamic projection (same method used for fetching different projections or even the entity itself)
        briefOverviewOfPersistentContextContent();

        OrganizationInterfaceBasedProjection organizationProjection = organizationRepository
            .findByMotto(EVIL_MOTTO, OrganizationInterfaceBasedProjection.class)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("OrganizationInterfaceBasedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organizationProjection));
        briefOverviewOfPersistentContextContent();

        OrganizationClassBasedProjection organizationClassBasedProjection = organizationRepository
            .findByMotto(EVIL_MOTTO, OrganizationClassBasedProjection.class)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("OrganizationClassBasedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organizationClassBasedProjection));
        briefOverviewOfPersistentContextContent();

        Organization organization = organizationRepository
            .findByMotto(EVIL_MOTTO, Organization.class)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));
        log.info("Organization:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organization));
        briefOverviewOfPersistentContextContent();
    }

    // TODO Check performance
    public void projectionIncludingManyToOneRelationship() throws JsonProcessingException {
        // projections which include fields from *-to-one relationships
        briefOverviewOfPersistentContextContent();

        // not very performant
        List<PersonDtoNestedClosedProjection> peopleUsingNestedClosedProjection = personRepository.findAllUsingNestedClosedProjection();
        log.info("PersonDtoNestedClosedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(peopleUsingNestedClosedProjection));
        briefOverviewOfPersistentContextContent();

        // better performance but worse representation
        List<PersonDtoSimpleClosedProjection> peopleUsingSimpleClosedProjection = personRepository.findAllUsingSimpleClosedProjection();
        log.info("PersonDtoSimpleClosedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(peopleUsingSimpleClosedProjection));
        briefOverviewOfPersistentContextContent();

        // better representation but extra mapper needed
        List<PersonDtoSimpleOpenProjection> peopleUsingSimpleOpenProjection = personRepository.findAllUsingSimpleOpenProjection();
        log.info("PersonDtoSimpleOpenProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(peopleUsingSimpleOpenProjection));
        briefOverviewOfPersistentContextContent();
    }

    public void projectionIncludingOneToManyRelationship() throws JsonProcessingException {
        // projections which include fields from *-to-many relationships
        briefOverviewOfPersistentContextContent();

        // not very performant, bad representation
        List<OrganizationDtoNestedClosedProjection> organizationDtoNestedClosedProjections = organizationRepository.findAllUsingNestedClosedProjection();
        log.info("OrganizationDtoNestedClosedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organizationDtoNestedClosedProjections));
        briefOverviewOfPersistentContextContent();

        // better performance but worse representation
        List<OrganizationDtoSimpleClosedProjection> organizationDtoSimpleClosedProjections = organizationRepository.findAllUsingSimpleClosedProjection();
        log.info("OrganizationDtoSimpleClosedProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organizationDtoSimpleClosedProjections));
        briefOverviewOfPersistentContextContent();

        // better representation but extra transformer needed
        List<Object[]> allUsingCustomTransformation = organizationRepository.findAllUsingCustomTransformation();
        List<OrganizationDtoCustomTransformation> organizationDtoCustomTransformations = organizationTransformer.transform(allUsingCustomTransformation);
        log.info("PersonDtoSimpleOpenProjection:\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(organizationDtoCustomTransformations));
        briefOverviewOfPersistentContextContent();
    }
}

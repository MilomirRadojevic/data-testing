package com.example.datatesting.service;

import com.example.datatesting.entity.Organization;
import com.example.datatesting.entity.Person;
import com.example.datatesting.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // It is suggested to set readOnly on class level transactional annotation and override it on method level if needed
public class OneToManyRelationshipService {
    // use bidirectional one to many (unidirectional is less efficient)
    // cascade only from parent to child
    // orphan removal only from parent side
    // set mapped by on parent side
    // keep both sides in sync
    // equals, hash code, to string overriding
    // specify join column

    private final OrganizationRepository organizationRepository;

    private static final String ORGANIZATION_NAME = "org name";
    private static final String PERSON_1_NAME = "p1 name";
    private static final String PERSON_2_NAME = "p2 name";
    private static final String PERSON_3_NAME = "p3 name";

    @Transactional
    public void addOrganizationWithPeople() {
        Organization organization = new Organization();
        organization.setName(ORGANIZATION_NAME);

        Person person1 = new Person();
        person1.setName(PERSON_1_NAME);

        Person person2 = new Person();
        person2.setName(PERSON_2_NAME);

        organization.addPerson(person1);
        organization.addPerson(person2);

        organizationRepository.save(organization);
    }

    @Transactional
    public void addNewPerson() {
        Organization organization = organizationRepository.findByName(ORGANIZATION_NAME)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        Person person = new Person();
        person.setName(PERSON_3_NAME);

        organization.addPerson(person);
    }

    @Transactional
    public void removeLastPerson() {
        Organization organization = organizationRepository.findByName(ORGANIZATION_NAME)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        List<Person> people = organization.getPeople();

        organization.removePerson(people.get(people.size() - 1));
    }

    @Transactional
    public void removeFirstPerson() {
        Organization organization = organizationRepository.findByName(ORGANIZATION_NAME)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        List<Person> people = organization.getPeople();

        organization.removePerson(people.get(0));
    }
}

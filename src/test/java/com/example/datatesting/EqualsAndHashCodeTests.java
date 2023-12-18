package com.example.datatesting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.datatesting.entity.Organization;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class EqualsAndHashCodeTests {
    @Autowired
    private TestEntityManager entityManager;

    private static final Organization organization = new Organization();
    private static final Set<Organization> organizations = new HashSet<>();

    @BeforeAll
    public static void setUp() {
        organization.setName("name");
        organizations.add(organization);
    }

    @Test
    public void A_givenOrganizationInSetWhenContainsThenTrue() throws Exception {
        assertTrue(organizations.contains(organization));
    }

    @Test
    public void B_givenOrganizationWhenPersistThenSuccess() throws Exception {
        assertNull(organization.getId());
        entityManager.persistAndFlush(organization);
        assertNotNull(organization.getId());
        assertTrue(organizations.contains(organization));
    }

    @Test
    public void C_givenOrganizationWhenMergeThenSuccess() throws Exception {
        organization.setMotto("some blah blah");
        assertTrue(organizations.contains(organization));
        Organization mergedOrganization = entityManager.merge(organization);
        entityManager.flush();
        assertTrue(organizations.contains(mergedOrganization));
    }

    @Test
    public void D_givenOrganizationWhenFindThenSuccess() throws Exception {
        Organization foundOrganization = entityManager.find(Organization.class, organization.getId());
        entityManager.flush();
        assertTrue(organizations.contains(foundOrganization));
    }

    @Test
    public void E_givenOrganizationWhenFindAndDetachThenSuccess() throws Exception {
        Organization foundOrganization = entityManager.find(Organization.class, organization.getId());
        entityManager.detach(foundOrganization);
        assertTrue(organizations.contains(foundOrganization));
    }

    @Test
    public void F_givenOrganizationWhenFindAndRemoveThenSuccess() throws Exception {
        Organization foundOrganization = entityManager.find(Organization.class, organization.getId());
        entityManager.remove(foundOrganization);
        entityManager.flush();
        assertTrue(organizations.contains(foundOrganization));
        organizations.remove(foundOrganization);
        assertFalse(organizations.contains(foundOrganization));
    }
}

package com.example.datatesting.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@ToString
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String superSecretName;
    private String motto;

    // Efficient bidirectional one to many mapping (parent side)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization", orphanRemoval = true)
    @Exclude // Exclude associations and lazy loaded attributes from toString
    private List<Person> people = new ArrayList<>();

    // It is suggested to keep both sides of the association in sync (following three methods are an example)
    public void addPerson(Person person) {
        people.add(person);
        person.setOrganization(this);
    }

    public void removePerson(Person person) {
        person.setOrganization(null);
        people.remove(person);
    }

    public void removePeople() {
        Iterator<Person> iterator = people.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            person.setOrganization(null);
            iterator.remove();
        }
    }

    // Things to note for overriding equals and hashcode methods:
    //  entity must be equal to itself across all state transitions (transient, managed, detached, removed)
    //  dirty checking does not use equals and hashcode methods
    //  equals and hashcode methods have to be overridden when:
    //      entities are stored in sets
    //      entities are reattached to new persistence context
    //      synchronizing both sides of relationships via helper methods

    // Best way to override equals if there is no unique business key
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id != null && id.equals(((Organization) obj).id);
    }

    // Best way to override hashCode (there is no performance penalty for having the constant value)
    @Override
    public int hashCode() {
        return 1;
    }
}

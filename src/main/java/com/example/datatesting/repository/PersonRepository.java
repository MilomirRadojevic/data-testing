package com.example.datatesting.repository;

import com.example.datatesting.dto.PersonDtoNestedClosedProjection;
import com.example.datatesting.dto.PersonDtoSimpleClosedProjection;
import com.example.datatesting.dto.PersonDtoSimpleOpenProjection;
import com.example.datatesting.entity.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select p.name as name, o as organization from Person p left join p.organization o")
    List<PersonDtoNestedClosedProjection> findAllUsingNestedClosedProjection();

    @Query("select p.name as personName, o.name as organizationName, o.motto as motto from Person p left join p.organization o")
    List<PersonDtoSimpleClosedProjection> findAllUsingSimpleClosedProjection();

    @Query("select p.name as personName, o.name as organizationName, o.motto as motto from Person p left join p.organization o")
    List<PersonDtoSimpleOpenProjection> findAllUsingSimpleOpenProjection();
}

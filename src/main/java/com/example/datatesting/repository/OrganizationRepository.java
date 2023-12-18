package com.example.datatesting.repository;

import com.example.datatesting.dto.OrganizationDtoNestedClosedProjection;
import com.example.datatesting.dto.OrganizationDtoSimpleClosedProjection;
import com.example.datatesting.dto.OrganizationInterfaceBasedProjection;
import com.example.datatesting.entity.Organization;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true) // It is suggested to set readOnly on class level transactional annotation and override it on method level if needed
public interface OrganizationRepository extends JpaRepository<Organization, Long>, CustomOrganizationRepository {
    Optional<Organization> findByName(String name);

    @Query("select o from Organization o join fetch o.people where o.name = :name")
    Optional<Organization> findByNameWithPeople(String name);

    @Query("select o from Organization o where o.id = :id")
    Optional<Organization> findByIdViaExplicitJPQL(Long id);

    @Query(value = "select * from organization where id = :id", nativeQuery = true)
    Optional<Organization> findByIdViaExplicitNativeQuery(Long id);

    @Query("select o.name from Organization o where o.id = :id")
    Optional<String> fetchNameByIdViaExplicitJPQL(Long id);

    @Query(value = "select name from organization where id = :id", nativeQuery = true)
    Optional<String> fetchNameByIdViaExplicitNativeQuery(Long id);

    @Query("select o from Organization o where o.id in :ids")
    List<Organization> fetchAllByIds(List<Long> ids);

    List<OrganizationInterfaceBasedProjection> findAllByMotto(String motto);

    @Query("select o.name as name, o.superSecretName as superSecretName from Organization o where o.motto = :motto")
    List<OrganizationInterfaceBasedProjection> findAllByMottoViaExplicitJPQL(String motto);

    @Query(value = "select name, super_secret_name as superSecretName from organization where motto = :motto", nativeQuery = true)
    List<OrganizationInterfaceBasedProjection> findAllByMottoViaExplicitNativeQuery(String motto);

    <T> Optional<T> findByMotto(String motto, Class<T> type);

    @Query("select o.name as name, p as person from Organization o left join o.people p")
    List<OrganizationDtoNestedClosedProjection> findAllUsingNestedClosedProjection();

    @Query("select o.name as organizationName, p.name as personName, p.nickname as personNickname from Organization o left join o.people p")
    List<OrganizationDtoSimpleClosedProjection> findAllUsingSimpleClosedProjection();

    @Query("select o.id as organizationId, o.name as organizationName, p.id as personId, p.name as personName, p.nickname as personNickname from Organization o left join o.people p")
    List<Object[]> findAllUsingCustomTransformation();
}

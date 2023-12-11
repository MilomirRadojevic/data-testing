package com.example.datatesting.repository;

import com.example.datatesting.entity.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group_ g left join fetch g.users ug left join fetch ug.user where g.id = :id")
    Optional<Group> findWithUsersById(Long id);
}

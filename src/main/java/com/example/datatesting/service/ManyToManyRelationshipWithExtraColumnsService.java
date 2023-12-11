package com.example.datatesting.service;

import com.example.datatesting.entity.Group;
import com.example.datatesting.entity.User;
import com.example.datatesting.repository.GroupRepository;
import com.example.datatesting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManyToManyRelationshipWithExtraColumnsService {
    // modeled as two one to many relationships so same advices apply
    // it can also be modeled as one one to many relationship for efficiency
    // specify join table entity
    // specify composite primary key

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private static final Long USER_ID = 1L;
    private static final Long GROUP_ID = 1L;

    @Transactional
    public void insertUsersAndGroups() {
        User user1 = new User();
        user1.setName("u1 name");
        User user2 = new User();
        user2.setName("u2 name");
        User user3 = new User();
        user3.setName("u3 name");
        User user4 = new User();
        user4.setName("u4 name");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Group group1 = new Group();
        group1.setName("g1 name");
        Group group2 = new Group();
        group2.setName("g2 name");

        groupRepository.save(group1);
        groupRepository.save(group2);

        group1.addUser(user1);
        group1.addUser(user2);
        group1.addUser(user3);
        group1.addUser(user4);

        group2.addUser(user1);
        group2.addUser(user2);
        group2.addUser(user3);
    }

    @Transactional
    public void removeUserFromGroup() {
        Group group = groupRepository.findWithUsersById(GROUP_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        User user = userRepository.findById(USER_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        group.removeUser(user);
    }
}

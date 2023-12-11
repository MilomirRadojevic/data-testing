package com.example.datatesting.controller;

import com.example.datatesting.service.ManyToManyRelationshipService;
import com.example.datatesting.service.ManyToManyRelationshipWithExtraColumnsService;
import com.example.datatesting.service.ManyToOneRelationshipService;
import com.example.datatesting.service.OneToManyRelationshipService;
import com.example.datatesting.service.OneToOneRelationshipService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RelationshipsController {
    private final OneToManyRelationshipService oneToManyRelationshipService;
    private final ManyToOneRelationshipService manyToOneRelationshipService;
    private final ManyToManyRelationshipService manyToManyRelationshipService;
    private final ManyToManyRelationshipWithExtraColumnsService manyToManyRelationshipWithExtraColumnsService;
    private final OneToOneRelationshipService oneToOneRelationshipService;

    @PostConstruct
    void init() {
//        oneToManyRelationshipService.addOrganizationWithPeople();
//
//        manyToOneRelationshipService.addAuthor();
//        manyToOneRelationshipService.addBook();
//
//        manyToManyRelationshipService.addPostsAndTags();
//
//        manyToManyRelationshipWithExtraColumnsService.insertUsersAndGroups();
//
//        oneToOneRelationshipService.addDeveloperAndLinkedinProfile();
    }

    @GetMapping("/relationships/1")
    public void addNewPerson() {
        oneToManyRelationshipService.addNewPerson();
    }

    @GetMapping("/relationships/2")
    public void removeLastPerson() {
        oneToManyRelationshipService.removeLastPerson();
    }

    @GetMapping("/relationships/3")
    public void removeFirstPerson() {
        oneToManyRelationshipService.removeFirstPerson();
    }

    @GetMapping("/relationships/4")
    public void findBooksOfAuthorByIdAndUpdateName() {
        manyToOneRelationshipService.findBooksOfAuthorByIdAndUpdateName();
    }

    @GetMapping("/relationships/5")
    public void findBooksOfAuthorByIdAndAddNewBook() {
        manyToOneRelationshipService.findBooksOfAuthorByIdAndAddNewBook();
    }

    @GetMapping("/relationships/6")
    public void findBooksOfAuthorByIdAndRemoveTheirFirstBook() {
        manyToOneRelationshipService.findBooksOfAuthorByIdAndRemoveTheirFirstBook();
    }

    @GetMapping("/relationships/7")
    public void removeTagFromFirstPost() {
        manyToManyRelationshipService.removeTagFromFirstPost();
    }

    @GetMapping("/relationships/8")
    public void removePost() {
        manyToManyRelationshipService.removePost();
    }

    @GetMapping("/relationships/9")
    public void removeTag() {
        manyToManyRelationshipService.removeTag();
    }

    @GetMapping("/relationships/10")
    public void removeUserFromGroup() {
        manyToManyRelationshipWithExtraColumnsService.removeUserFromGroup();
    }

    @GetMapping("/relationships/11")
    public void findLinkedinProfileByDeveloper() {
        oneToOneRelationshipService.findLinkedinProfileByDeveloper();
    }
}

package com.example.datatesting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuperService {
    private final OneToManyRelationshipService oneToManyRelationshipService;
    private final ManyToOneRelationshipService manyToOneRelationshipService;
    private final ManyToManyRelationshipService manyToManyRelationshipService;
    private final ManyToManyRelationshipWithExtraColumnsService manyToManyRelationshipWithExtraColumnsService;
    private final OneToOneRelationshipService oneToOneRelationshipService;
    private final DirectFetchingService directFetchingService;
    private final ProjectionFetchingService projectionFetchingService;

    public void relationships() {
        oneToManyRelationshipService.addOrganizationWithPeople();
        oneToManyRelationshipService.addNewPerson();
        oneToManyRelationshipService.findWithPeople();
        oneToManyRelationshipService.removeLastPerson();
        oneToManyRelationshipService.removeFirstPerson();

        manyToOneRelationshipService.addAuthor();
        manyToOneRelationshipService.addBook();
        manyToOneRelationshipService.findBooksOfAuthorByIdAndUpdateName();
        manyToOneRelationshipService.findBooksOfAuthorByIdAndAddNewBook();
        manyToOneRelationshipService.findBooksOfAuthorByIdAndRemoveTheirFirstBook();

        manyToManyRelationshipService.addPostsAndTags();
        manyToManyRelationshipService.removeTagFromFirstPost();
        manyToManyRelationshipService.removePost();
        manyToManyRelationshipService.removeTag();

        manyToManyRelationshipWithExtraColumnsService.insertUsersAndGroups();
        manyToManyRelationshipWithExtraColumnsService.removeUserFromGroup();

        oneToOneRelationshipService.addDeveloperAndLinkedinProfile();
        oneToOneRelationshipService.findLinkedinProfileByDeveloper();
    }

    @Transactional(readOnly = true)
    public void directFetching() {
        directFetchingService.addOrganizations(); // in separate transaction
        directFetchingService.fetchById();
        directFetchingService.fetchByIdAndUpdateName(); // in separate transaction
        directFetchingService.fetchWithSessionLevelRepeatableRead();
        directFetchingService.fetchMultipleByIds();
    }

    public void fetchingReadOnlyFlag() {
        directFetchingService.addOrganizations();
        directFetchingService.fetchByNameReadWrite();
        directFetchingService.fetchByNameReadOnly();
    }

    public void projectionFetching() throws JsonProcessingException {
        projectionFetchingService.addOrganizationsAndPeople();
        projectionFetchingService.findAllByMotto();
        projectionFetchingService.findDynamicProjectionsByMotto();

        projectionFetchingService.projectionIncludingManyToOneRelationshipNestedClosedProjection();
        projectionFetchingService.projectionIncludingManyToOneRelationshipSimpleClosedProjection();
        projectionFetchingService.projectionIncludingManyToOneRelationshipSimpleOpenProjection();

        projectionFetchingService.projectionIncludingOneToManyRelationshipNestedClosedProjection();
        projectionFetchingService.projectionIncludingOneToManyRelationshipSimpleClosedProjection();
        projectionFetchingService.projectionIncludingOneToManyRelationshipCustomTransformation();
    }
}

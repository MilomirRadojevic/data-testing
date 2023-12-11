package com.example.datatesting.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuperService extends PersistenceContextInspectingService {
    private final DirectFetchingService directFetchingService;

    public SuperService(OneToManyRelationshipService oneToManyRelationshipService,
                        ManyToOneRelationshipService manyToOneRelationshipService,
                        ManyToManyRelationshipService manyToManyRelationshipService,
                        ManyToManyRelationshipWithExtraColumnsService manyToManyRelationshipWithExtraColumnsService,
                        OneToOneRelationshipService oneToOneRelationshipService,
                        DirectFetchingService directFetchingService,
                        ProjectionFetchingService projectionFetchingService,
                        EntityManager entityManager) {
        super(entityManager);
        this.directFetchingService = directFetchingService;
    }

    @PostConstruct
    void init() {
//        directFetchingService.addOrganizations();
    }

    @Transactional(readOnly = true)
    public void directFetching() {
        directFetchingService.fetchById();
        directFetchingService.fetchByIdAndUpdateName(); // in separate transaction
        directFetchingService.fetchWithSessionLevelRepeatableRead();
        directFetchingService.fetchMultipleByIds();
    }

//    public void fetchingReadWrite() {
//        directFetchingService.fetchByNameReadWrite();
//    }

//    public void fetchingReadOnly() {
//        directFetchingService.fetchByNameReadOnly();
//    }
}

package com.example.datatesting.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

@RequiredArgsConstructor
@Slf4j
// TODO Revisit this
public abstract class PersistenceContextInspectingService {
    @PersistenceContext
    protected final EntityManager entityManager;

    private org.hibernate.engine.spi.PersistenceContext getPersistenceContext() {
        SharedSessionContractImplementor sharedSession = entityManager.unwrap(
            SharedSessionContractImplementor.class
        );

        return sharedSession.getPersistenceContext();
    }

    protected void briefOverviewOfPersistentContextContent() {
        org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();

        int managedEntities = persistenceContext.getNumberOfManagedEntities();
        int collectionEntriesSize = persistenceContext.getCollectionEntriesSize();
        log.info("Total number of managed entities: " + managedEntities);
        log.info("Total number of collection entries: " + collectionEntriesSize);

        Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();
        if (!entitiesByKey.isEmpty()) {
            log.info("\nEntities by key:");
            entitiesByKey.forEach((key, value) -> System.out.println(key + ": " + value));

            // This is causing additional selects, so it is wrong to call it
//            log.info("\nStatus and hydrated state:");
//            for (Object entry : entitiesByKey.values()) {
//                EntityEntry ee = persistenceContext.getEntry(entry);
//                log.info(
//                    "Entity name: " + ee.getEntityName()
//                        + " | Status: " + ee.getStatus()
//                        + " | State: " + Arrays.toString(ee.getLoadedState()));
//            }
//        }
//        if (collectionEntriesSize > 0) {
//            log.info("\nCollection entries:");
//            persistenceContext.forEachCollectionEntry(
//                (k, v) -> log.info("Key:" + k + ", Value:" + (v.getRole() == null ? "" : v)), false
//            );
        }
    }
}

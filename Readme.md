# data-testing
##examples
- equals, hashCode, toString (organization - person / user group - user group id)
- osiv antipattern
- transactional annotation (transactional context)
- one to many (organization - person)
- many to one (author - book)
- many to many (post - tag)
- many to many with extra column (user - group)
- one to one (developer - linkedin profile)
- direct fetching - fetching by id (organization)
  - session level repeatable read
    - if we fetch entity via direct fetching after we already fetched it via direct fetching, entity is loaded from cache
    - if we fetch entity via jpql/native query after we already fetched it via direct fetching, the db snapshot is ignored and entity is loaded from cache
    - if we fetch data via projects after we already fetched entity via direct fetching, then transaction isolations determines where the data is loaded from (non-repeatable reads prevention)
  - read-only mode (organization fetchByNameReadOnly)
    - hydrated state is not loaded into memory
    - no dirty checking
- fetching parent and association in one select (search for examples in code as I forgot to specify them here)
  - join fetch (or entity graph) if we plan to modify data (findWithPeople)
  - projection/dto if we don't
- fetching projections/dtos (organization - person)
  - dynamic projection (findByMotto)
  - entity inside projection (I am skipping this for now)
  - projection including *-to-one relationship (PersonDto)
    - nested closed projection
    - simple closed projection
    - using custom transformer
    - simple open projection
  - projection including associated collection (OrganizationDto)
    - nested closed projection
    - simple closed projection
    - using custom transformer
  - examples for the following are currently todo
    - projection including all entity attributes
      - best way to do it is using jpql and explicit list of columns
    - dto via construction expression
      - avoid using this if entity is inside the dto
    - dto via jpa tuple
    - dto via @SqlResultSetMapping and @NamedNativeQuery
    - dto via ResultTransformer (possibly deprecated in favor of new hibernate stuff)
    - dto via custom ResultTransformer (possibly deprecated in favor of new hibernate stuff)
    - dto via custom Blaze-Persistence entity views
    - pagination with joins (using pageable)
      - eliminating count query
    - page vs slice vs list

##todo
- entity graphs vs join fetch
- second level cache
- get one vs find by id (unproxying a proxy)
- bytecode enhancements
  - dirty checking (reflection vs dirty tracking)
  - lazy loading attributes
  - association management
- publishing events
- batch and bulk operations
- streamable
- identifier (generation strategies)
- auditing
- flyway
- pagination
- query optimization
- optimistic locking
- inheritance
- data types
- hibernate envers
- stored procedures
- database views
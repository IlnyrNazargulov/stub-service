ALTER TABLE stub_request
    DROP COLUMN response;

DELETE
FROM flyway_schema_history
WHERE version = '2024.06.10.01';
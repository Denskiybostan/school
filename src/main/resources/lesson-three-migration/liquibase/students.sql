-- liquibase formated sql

-- changeset denis:1
create index students(
    name text,
    surname text
)
create index faculty(
    name text,
    color text
)

-- liquibase formated sql

-- changeset denis:1
create index students
    on table_name text,
    surname text
)
create index faculty
    on table_name text,
    color text
)

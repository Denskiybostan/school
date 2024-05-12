-- liquibase formated sql

-- changeset denis:1
create unique index students
    on table_name text,
    surname text
)
create unique index faculty
    on table_name text,
    color text
)

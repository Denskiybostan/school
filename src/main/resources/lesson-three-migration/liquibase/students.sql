-- liquibase formated sql

-- changeset denis:1
create index students on student (name);
-- changeset denis:2
create index faculty on faculty (name, color);

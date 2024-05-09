select surname from student;
select * from student where age between 10 and 20;
select * from student where name like '%O%';
select name from student where name is not null;
select * from student order by age;
alter table student add constraint age_check CHECK (age>= 16);
insert  into student(age, name, faculty_id) values (17, 'aaaaa', null );
alter table student add constraint name_unique unique  (name);
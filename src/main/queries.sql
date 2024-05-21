select surname from student;
select * from student where age between 10 and 20;
select name from student where name like '%O%';
select name from student where name is not null;
select * from student order by age;
create table student(
    constraint  name_uniue unique (name)
);
create table faculty(
    constraint faculty_name_color_uniq unique (name, color)
);
insert  into student(age, name, faculty_id) values (17, 'aaaaa', null );
create table student(
    constraint age_check check (age >=16)
);
create table student(
    set int default age(20)
);
insert  into faculty(color, name) values ('blue', 'harry');
update student set faculty_id = 1;
select s.name, s.age, f.name
from student s
join faculty f on f.id = s.faculty.id;

select *
from student s join public.avatar a on student.id = a.student_id
where a.student_id is not null;

select *
from databasechangelog;

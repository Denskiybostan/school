select surname from student;
select * from student where age between 10 and 20;
select * from student where name like '%O%';
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

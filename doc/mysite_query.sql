use webdb;
desc user;

-- join(insert)
insert into user values(null,'관리자','admin@mysite.com','1111','female');

-- select
select no,name,email,password,gender from user;
use webdb;
desc user;

-- join(insert)
insert into user values(null,'관리자','admin@mysite.com','1111','female');

-- select
select no,name,email,password,gender from user;

-- update
update user set name = '배유진', password='1111' , gender='female' where no = 2;

-- login(select)
select no,name from user where email="admin@mysite.com" and password="1111";
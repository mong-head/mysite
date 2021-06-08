use webdb;

insert into guestbook values(null,'a','1234','test',now());
select * from guestbook;
select last_insert_id();

desc user;
select * from user;

alter table user add column role varchar(5) default 'USER';
alter table user modify column role enum('USER','ADMIN') default 'USER';

update user set role='ADMIN' where no = 1;

select * from board
order by group_no desc, order_no asc
;

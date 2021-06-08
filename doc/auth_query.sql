use webdb;

insert into guestbook values(null,'a','1234','test',now());
select * from guestbook;
select last_insert_id();

desc user;
select * from user;

select b.no as no ,title,contents,reg_date as regDate ,hit,group_no as groupNo ,order_no as orderNo ,depth, u.no as userNo, u.name as userName
			from board b
			join user u on u.no = b.user_no
			where title like '%ㅇ%' or contents like '%ㅇ%'
			order by group_no desc , order_no asc
			limit 1, 5
            ;

alter table user add column role varchar(5) default 'USER';
alter table user modify column role enum('USER','ADMIN') default 'USER';

update user set role='ADMIN' where no = 1;
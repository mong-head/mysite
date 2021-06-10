use webdb;
desc board;

-- select (find all)
select b.no,title,contents,reg_date,hit,group_no,order_no,depth,b.user_no, u.name
from board b
join user u on u.no = b.user_no 
order by group_no desc , order_no asc
;

-- select (find by page)
select b.no,title,contents,reg_date,hit,group_no,order_no,depth,b.user_no, u.name
from board b
join user u on u.no = b.user_no 
order by group_no desc , order_no asc
limit 5,5
;

select * from user;
select * from board;

delete from board;

-- insert
insert into board values(null,"점심먹자!", "점심 뭐 먹을까?", now(), 0, 1,0,0,1);
insert into board values(null,"라면?", "라면이 좋을 것 같아 ㅎㅎ", now(), 0, 1,1,1,2);

-- find max group no
select if(max(group_no) is null,'0',max(group_no)+1)from board;

-- find by no
select title,contents from board where user_no = 1;

-- update hit
update board set hit = 1 where no = 1;

-- update
update board set title = '음' , contents = 'ㅎㅎ' where no = 19;

-- search
select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name
from board b
join user u on u.no = b.user_no 
where u.name like '%배유진%'
order by group_no desc , order_no asc
;

select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name, count(*)
from board b
join user u on u.no = b.user_no 
where title like '%mong%' or contents like '%mong%' or u.name like '%mong%'
order by group_no desc , order_no asc
;

select count(*) from board;

select * from board;
select * from board  where order_no > 2 and group_no = 1;

-- update order no
update board set order_no = order_no + 1 where order_no > 2 and group_no = 1;
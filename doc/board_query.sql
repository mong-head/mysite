use webdb;
desc board;

-- select (find all)
select b.no,title,contents,reg_date,hit,group_no,order_no,depth,b.user_no, u.name
from board b
join user u on u.no = b.user_no 
order by group_no desc , order_no asc
;

select * from user;

-- insert
insert into board values(null,"점심먹자!", "점심 뭐 먹을까?", now(), 0, 1,0,0,1);
insert into board values(null,"라면?", "라면이 좋을 것 같아 ㅎㅎ", now(), 0, 1,1,1,2);

-- find max group no
select max(group_no) from board;

-- find by no
select title,contents from board where user_no = 1;
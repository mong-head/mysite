use webdb;

-- list
-- first : default = 0
select no,name,password,message,reg_date as regDate
from guestbook
order by no desc
limit 0,3
;
-- next
select no,name,password,message,reg_date as regDate
from guestbook
where no < 32
order by no desc
limit 0,3
;


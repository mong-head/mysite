use webdb;
desc site;

insert into site values(
	'MySite',
    '안녕하세요. Yujin의  mysite에 오신 것을 환영합니다.',
    '/assets/images/profile.jpg',
    '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n메뉴는 사이트 소개, 방명록, 게시판이 있습니다.\nJava web programming 수업과 Database 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.\n\n')
    ;

select * from site;

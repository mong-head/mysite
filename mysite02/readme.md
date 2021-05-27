# mysite02

* model2

	* Servlet (Controller)
	* Dao (Model)
	* JSP (View)
	
	<img src="https://user-images.githubusercontent.com/52481037/119791848-28cf7a80-bf10-11eb-87bd-e312923c28f5.png" width="60%">

	
* MVC

	* simple version

		<img src="https://user-images.githubusercontent.com/52481037/119789991-8367d700-bf0e-11eb-9be0-2c97b13ade7e.jpg" width="60%">

		* mysite에 오는 여러 종류의 요청 → 각각의 요청관련 servlet을 따로 만듦
   			* user 관련 요청
    		* 게시판(board) 관련 요청

* file

	```text
	com.douzone.mysite.controller
	   | -- MainController
	   | -- UserController
	   | -- GuestController
	com.douzone.mysite.repository
	   | -- UserRepository
	   | -- GuestRepository
	com.douzone.mysite.vo
	   | -- UserVo
	   | -- GuestbookVo
	
	< Action >
	com.douzone.mvc
	   | -- Action (interface)
	   | -- ActionFactory (interface)
	com.douzone.mvc.util
	   | -- MvcUtils
	
	com.douzone.mysite.mvc.main
	   | -- MainActionFactory
	   | -- MainAction
	com.douzone.mysite.mvc.user
	   | -- UserActionFactory
	   | -- JoinAction
	   | -- JoinFormAction
	com.douzone.mysite.mvc.guestbook
	   | -- GuestActionFactory
	   | -- ListAction
	   | -- InsertAction
	   | -- DeleteFormAction
	   | -- DeleteAddAction
	```
	
- 중복된 코드
    - Action 관련

        <img scr="https://user-images.githubusercontent.com/52481037/119794250-53bace00-bf12-11eb-899c-a0565ac4fd91.jpg" width="60%">

        - MVCUtils
            - Controller마다 util에 해당하는 forwarding, redirecting부분은 반복됨
            - join action, delete action 등 여러 action에서 사용
            - 해당 부분은 패키지 따로 만들어 forward, redirect등의 함수 만들어 사용
        - Action
            - MvcUtils 사용
            - join이라는 action을 여러 번 수행가능
            - 그 과정에서 코드중복이 발생할 수 있음
            - 관리하기 쉽도록 Action class를 따로 만듦
        - Action Factory
            - Action을 모아놓음
    - Server Side Include(SSI)
        - jsp부분에서 header, margin, foot 부분은 모든 페이지가 동일함
        - header.jsp, margin.jsp, foot.jsp만들어서 사용
            - header.jsp

                ```jsx
                <%@ page language="java" contentType="text/html; charset=UTF-8"
                    pageEncoding="UTF-8"%>
                		<div id="header">
                			<h1>MySite</h1>
                			<ul>
                				<li><a href="<%=request.getContextPath() %>/user?a=loginform">로그인</a><li>
                				<li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a><li>
                				<li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a><li>
                				<li><a href="<%=request.getContextPath() %>/user?a=logout">로그아웃</a><li>
                				<li>님 안녕하세요 ^^;</li>
                			</ul>
                		</div>
                ```

        - index.jsp에서 include사용

            ```jsx
            <jsp:include page="<%=request.getContextPath() %>/WEB-INF/views/includes/header.jsp" />
            ```
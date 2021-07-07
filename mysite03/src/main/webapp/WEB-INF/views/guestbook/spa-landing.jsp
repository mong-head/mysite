<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	/** guestbook application based on jQuery */
	/*
		과제1 : list
		 - no 기준의 list를 부분적으로 (3~5개씩) 가져와서 리스트 렌더링 (뒤에 붙임)
		 - button event 구현 -> scroll event 로 변경
		 - no 기준 동적 query를 repository에 구현
		 - 렌더링 참고 : /ch08/test/gb/ex1
		
		과제2 : message 등록(add)
		 - validation(message 기반 dialog plugin)
		 - form submit 막기
		 - 데이터 하나를 rendering (prepend)
		 - 렌더링 참고 : /ch08/test/gb/ex2
		
		과제3 : message 삭제(delete)
		 - anchor tag(a tag) 기본 동작 막기
		 - live event 
		 - form 기반 dialog plugin
		 - 응답에 대해서 해당 li element 삭제
		 - password 틀린 경우(삭제실패 : no = 0) 사용자에게 알려주는 UI 처리
		 - 삭제 성공시( no > 0 ), data-no = 10(예제로 10번) 인 li element 삭제  
		 - 렌더링 참고 : /ch08/test/gb/ex3
	*/
	var fetch = function(){
		var no = $("#list-guestbook li:last").data("no");
		if(no == null){
			no = 0;
		}
		console.log('no:',no);
		$.ajax({
			url : "${pageContext.request.contextPath }/guestbook/api/list/"+no,
			dataType : "json", // 받을 때 format
			type : "get", // 요청 method
			success : function(response) {
				console.log(response);
				response.data.forEach(function(vo){
					html = 
							"<li data-no='"+ vo.no +"'><strong>"+ vo.name + "</strong>" +
								"<p>" + vo.message + "</p>" +
								"<strong></strong>" +
								"<a href='' data-no='"+ vo.no + "'>삭제</a>"+
							"</li>";
							
					$("#list-guestbook").append(html);
				});
			}
		});
	}
	
	$(function() {
		$("#btn-fetch").click(function() {
			fetch();
		});
	
		
		// 최초 데이터 가지고오기
		fetch();
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
				<div>
					<button id="btn-fetch">fetch</button>
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
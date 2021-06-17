<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- 복잡 -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<!-- script emdbedded -->
<!-- 
<script>
	/*dom 사용*/
	//window : browser에 있는 전역객체
	//window onload되었을 때 실행
	window.onload = function(){
		console.log("...");
		el = document.getElementById("btn-check"); // DOM api 바로 사용; 이렇게 하기 보다는 dom api mapping 해주는 jquery등을 사용해서 이쁘게 코딩 
		console.log(el);
	}
</script>
-->
<!-- script source 가지고 오기 -->
<script
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"
	type="text/javascript"></script>

<script>
	//console.log(jQuery);
	//$('#btn-check'); //jQuery('#btn-check') 같은 말
	i = 10
	f = function() {
		btn = $('#btn-check');
		btn.click(function() {
			$.ajax({
				url : "${pageContext.request.contextPath }/msg2",
				type : "get",
				dataType : "json",
				error : function(xhr, status, e) {
					console.error(status, e);
				},
				success : function(response) {
					console.log(response);
				}
			});
		});
	}
	//$(f); // 함수객체 f를 호출
	$(function() {
		btn = $('#btn-check');
		btn.click(function() {
			email = $('#email').val(); //변수로 만들 때 var사용
			if (email == "") {
				return;
			}
			$.ajax({
				url : "${pageContext.request.contextPath }/user/api/checkemail?email=" + email,
				type : "get",
				dataType : "json",
				success : function(response) {
					console.log(response);

					if (response.result != "success") {
						console.error(response.message);
						return;
					}

					if (response.data) {
						alert("존재하는 이메일입니다. 다른 이메일 사용하세요.");
						$("#email").val("");
						$("#email").focus();
						return;
					}

					$('#btn-check').hide();
					$('#img-check').show();
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form modelAttribute="userVo" id="join-form" name="joinForm"
					method="post"
					action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<!-- form:input이 밑 줄 대신
					<input id="name"
						name="name" type="text" value="${userVo.name } ">
					-->

					<!-- validation error check -->
					<p style="color: #f00; text-align: left; padding-left: 0">
						<!-- spring tag : 복잡 -->
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message
									code="${errors.getFieldError( 'name' ).codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
					</p>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<!-- ajax로 -->
					<input id="btn-check" type="button" value="중복체크">
					<img id="img-check"
						src="${pageContext.request.contextPath }/assets/images/check.png"
						style="width: 15px; vertical-align: bottom; display: none" />

					<!-- validation error check -->
					<p style="color: #f00; text-align: left; padding-left: 0">
						<!-- form tag : 간편 -->
						<form:errors path="email" />
					</p>

					<label class="block-label"><spring:message
							code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="color: #f00; text-align: left; padding-left: 0">
						<!-- form tag : 간편 -->
						<form:errors path="password" />
					</p>

					<label class="block-label">성별</label>
					여<input type="radio" name="gender" value="female" checked="checked">
					남<input type="radio" name="gender" value="male">

					<label class="block-label">약관동의</label>
					<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
					<label>서비스 약관에 동의합니다.</label>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
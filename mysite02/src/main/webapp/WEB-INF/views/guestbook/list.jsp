<%@page import="com.douzone.mysite.vo.GuestbookVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<GuestbookVo> list = (List<GuestbookVo>)request.getAttribute("list");
	String alert = request.getParameter("alert");
	if("true".equals(alert)){
		out.println("<script>alert('비번 틀림!');</script>");
		out.println("<script>window.location.href=\"http://localhost:8080/mysite02/guestbook\"</script>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath()%>/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
				<%
					int idx=0;
					for(GuestbookVo vo : list) {
						String msg = vo.getMessage();
						msg = msg.replace("\n","<br/>");
				%>
						<li>
							<table>
								<tr>
								<td><%=++idx %></td>
								<td><%=vo.getName() %></td>
								<td><%=vo.getRegDate().split(" ")[0] %></td>
								<td><a href="<%=request.getContextPath()%>/guestbook?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4><%=msg %></td>	
								</tr>
							</table>
							<br>
						</li>
				<%
					}
				%>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
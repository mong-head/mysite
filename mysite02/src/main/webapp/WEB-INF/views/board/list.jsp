<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<select name="looking_for">
					    <option value="title_contents" <c:if test="${'title_contents' eq looking_for}">selected</c:if>>제목+내용</option>
					    <option value="title" <c:if test="${'title' eq looking_for}">selected</c:if>>제목</option>
					    <option value="contents" <c:if test="${'contents' eq looking_for}">selected</c:if>>내용</option>
					    <option value="writer" <c:if test="${'writer' eq looking_for}">selected</c:if>>작성자</option>
					</select>
					<c:choose>
						<c:when test="${not empty kwd }">
							<input type="text" id="kwd" name="kwd" value="${kwd }">
						</c:when>
						<c:otherwise>
							<input type="text" id="kwd" name="kwd" value="">
						</c:otherwise>
					</c:choose>
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align:left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var="vo" varStatus="status">	
					<tr>
						<td>${size - status.index - 5*(pageInfo.currentPageNo-1)}</td>
						<c:choose>
							<c:when test= "${vo.depth == 0 }">
								<td style="text-align:left; padding-left:0px"><a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left; padding-left:${(vo.depth)*15 }px"><img src="assets/images/reply.png"><a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a></td>
							</c:otherwise>
						</c:choose>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
							<c:if test="${authUser.no == vo.userNo }">
								<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}&p=${pageInfo.currentPageNo}&kwd=${kwd}&looking_for=${looking_for}" class="del">삭제</a>
							</c:if>
						</td>
						
					</tr>
					</c:forEach>
				</table>
						
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pageInfo.firstPageNo != 1}">
							<li><a href="${pageContext.request.contextPath }/board?p=${pageInfo.prevPageNo }&kwd=${kwd}&looking_for=${looking_for}">◀</a></li>
						</c:if>
						<c:forEach var="i" begin="${pageInfo.firstPageNo }" end="${pageInfo.lastPageNo }">
							<li <c:if test="${i == pageInfo.currentPageNo}" >class="selected"</c:if>>
								<c:choose>
									<c:when test="${i > pageInfo.totalPage }">
										<c:out value="${i }"/>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath }/board?p=${i }&kwd=${kwd}&looking_for=${looking_for}"><c:out value="${i }"/></a>
									</c:otherwise>			
								</c:choose>					
							</li>
						</c:forEach>
						<c:if test="${pageInfo.lastPageNo < pageInfo.totalPage}">
							<li><a href="${pageContext.request.contextPath }/board?p=${pageInfo.nextPageNo }&kwd=${kwd}&looking_for=${looking_for}">▶</a></li>
						</c:if>
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${not empty authUser }">
					<a href="${pageContext.request.contextPath }/board?a=write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>
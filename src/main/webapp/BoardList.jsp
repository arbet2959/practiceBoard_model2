<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
System.out.println(request.getAttribute("msg")+"11111");
%>
<c:if test="${msg!=null }">
	<script type="text/javascript">
		alert("오류발생")
		/* alert(${msg}); */
	</script>
</c:if>

<div class="container">
<h2>전체 게시글 보기</h2>
	<table width = "700" border = "1">
		<tr height = "40">
			<td align ="right" colspan = "5">
				<input type="button" value="글쓰기" onclick="location.href='BoardWriteForm.jsp'">
			</td>
		</tr>
		<tr height = "40">
			<td width = "50" align ="center">번호 </td>
			<td width = "320" align ="center">제목</td>
			<td width = "100" align ="center">작성자 </td>
			<td width = "150" align ="center">작성일 </td>
			<td width = "80" align ="center">조회수</td>
		</tr>
		<c:set var="number" value="${number }"/>
		<c:forEach var="bean" items="${beans }">
			<tr height = "40">
				<td width = "50" align ="center">${number } </td>
				<td width = "320" align ="left">
					<c:forEach var="j" begin="2" end="${bean.re_step }" >
						&nbsp;&nbsp;&nbsp;
					</c:forEach>
					<a href="BoardInfoCon.do?num=${bean.num }">${bean.subject }</a>
				</td>
				<td width = "100" align ="center">${bean.writer } </td>
				<td width = "150" align ="center">${bean.reg_date } </td>
				<td width = "80" align ="center">${bean.readcount }</td>
			</tr>
			<c:set var="number" value="${number-1 }"/>
		</c:forEach>
		</table>
		<br>
		
	<c:if test="${count>0}">
		<c:set var="pageCount" value="${count/pageSize +(count%pageSize ==0 ? 0 : 1) }"/>
		<c:set var="startPage" value="${1}"/>
		
		<c:if test="${currentPage%10 !=0 }">
			<!-- 형변환 조심-->
			<fmt:parseNumber var="result" value="${currentPage/10}" integerOnly="true" />
			<c:set var="startPage" value="${result*10+1 }"/>
		</c:if>
		<c:if test="${currentPage%10 ==0 }">
			<fmt:parseNumber var="result" value="${currentPage/10}" integerOnly="true" />
			<c:set var="startPage" value="${(result-1)*10+1 }"/>
		</c:if>
		
		<c:set var="pageBlock" value="${10}"/>
		<c:set var="endPage" value="${startPage+pageBlock-1 }"/>
		
		<c:if test="${endPage> pageCount }">
			<c:set var="endPage" value="${ pageCount}"/> 
		</c:if>
		
		<c:if test="${startPage>10 }">
			<a href="BoardListCon.do?pageNum=${startPage-10 }"> [이전] </a>
		</c:if>
		
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="BoardListCon.do?pageNum=${i }" style ="text-decoration:none"> [${i }] </a>
		</c:forEach>
		<c:if test="${endPage<pageCount}">
			<a href="BoardListCon.do?pageNum=${startPage+10 }"> [다음] </a>
		</c:if>
	</c:if>
		
</div>
</body>
</html>
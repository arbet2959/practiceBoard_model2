<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<form action="BoardUpdateProcCon.do" method="post">
	<table	width ="600" border = "1" bordercolor = "gray">
		<tr height="40">
			<td align = "center" width ="120">조회수</td>
			<td align = "center" width ="180">${bean.readcount}</td>
			<td align = "center" width ="120">비밀번호</td>
			<td align = "center" width ="180"><input type="password" name="password" value=""></td>
		</tr>
		<tr height="40">
			<td align = "center" width ="120">작성자</td>
			<td align = "center" width ="180">${bean.writer }</td>
			<td align = "center" width ="120">작성일</td>
			<td align = "center" width ="180">${bean.reg_date }</td>
		</tr>
		<tr height="40">
			<td align = "center" width ="120">이메일</td>
			<td align = "center" colspan="3">${bean.email }</td>
		</tr>
		<tr height="40">
			<td align = "center" width ="150">제목</td>
			<td align = "center" colspan="3" width ="450"><input type="text" name="subject" value="${bean.subject }"></td>
		</tr>
		<tr height="40">
			<td align = "center" colspan="4">내 용</td>
		</tr>
		<tr height="400">
			<td align = "center" colspan="4"><textarea rows="10" cols="60" name="content">${bean.content }</textarea></td>
		</tr>
		<tr height="40">
			<td align = "center" colspan="4">
				<input type="hidden" name="num" value="${bean.num }">
				<input type="hidden" name="realPassword" value="${bean.password }">
				
				<input type= "submit" value="수정하기">&nbsp;&nbsp;
				<input type= "reset"  value="다시 작성">&nbsp;&nbsp;
				<button onclick="location.href='BoardListCon.do'">전체 게시글 보기</button>
				
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>
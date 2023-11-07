<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="container">
<h2>답변글 입력하기</h2>
<form action="BoardReWriterProcCon.do" method="post">
	<table	width ="600" border = "1" bordercolor = "gray" >
		<tr height="40">
			<td align = "center" width ="150">작성자</td>
			<td width="450"><input type= "text" name = "writer" size = "60"></td>
		</tr>
		
		<tr height="40">
			<td align = "center" width ="150">제목</td>
			<td width="450"><input type= "text" name = "subject" value="[re]" size = "60"></td>
		</tr>
		
		<tr height="40">
			<td align = "center" width ="150">이메일</td>
			<td width="450"><input type= "email" name = "email" size = "60"></td>
		</tr>
		
		<tr height="40">
			<td align = "center" width ="150">비밀 번호</td>
			<td width="450"><input type= "password" name = "password" size = "60"></td>
		</tr>
		
		<tr height="40">
			<td align = "center" width ="150">글 내용</td>
			<td width="450"><textarea rows= "10" cols = "60" name = "content"></textarea></td>
		</tr>
		<tr height="40">
			<td align = "center" colspan = "2">
				<input type="hidden" name="ref" value="${ref}">
				<input type="hidden" name="re_step" value="${re_step }">
				<input type="hidden" name="re_level" value="${re_level }">
				<input type= "submit" value="글쓰기">&nbsp;&nbsp;
				<input type= "reset"  value="다시 작성">&nbsp;&nbsp;
				<button onclick="location.href='BoardListCon.do'">전체 게시글 보기</button>
			</td>
		</tr>
	</table>		
</form>
</div>
</body>
</html>
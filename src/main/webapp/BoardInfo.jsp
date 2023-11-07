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
	<table	width ="600" border = "1" bordercolor = "gray" >
		<tr height="40">
			<td align = "center" width ="120">글번호</td>
			<td align = "center" width ="180">${bean.num }</td>
			<td align = "center" width ="120">조회수</td>
			<td align = "center" width ="180">${bean.readcount }</td>
		</tr>
		<tr height="40">
			<td align = "center" width ="120">작성자</td>
			<td align = "center" width ="180">${bean.writer}</td>
			<td align = "center" width ="120">작성일</td>
			<td align = "center" width ="180">${bean.reg_date}</td>
		</tr>
		<tr height="40">
			<td align = "center" width ="120">이메일</td>
			<td align = "center" colspan="3">${bean.email}</td>
		</tr>
		<tr height="40">
			<td align = "center" width ="150">제목</td>
			<td align = "center" colspan="3" width ="450">${bean.subject}</td>
		</tr>

		<tr height="40">
			<td align = "center" colspan="4">내 용</td>
		</tr>
		<tr height="400">
			<td align = "center" colspan="4"><pre>${bean.content}</pre></td>
		</tr>
		<tr height="40">
			<td align = "center" colspan="4">
				<input type="button" value="답글쓰기" 
					onclick="location.href='BoardReWriteCon.do?num=${bean.num}'">
				<input type="button" value="수정하기" 
					onclick="location.href='BoardUpdateCon.do?num=${bean.num}'">
				<input type="button" value="삭제하기" 
					onclick="location.href='BoardDeleteCon.do?num=${bean.num}'">
				<input type="button" value="글목록보기" 
					onclick="location.href='BoardListCon.do'">
			</td>
		</tr>
	</table>
</div>
</body>
</html>
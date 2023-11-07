package BoardWriterProcCon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardWriterProcCon.do")
public class BoardWriterProcCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("text/html; charset=UTF-8");
		String writer = request.getParameter("writer") == null ? "":request.getParameter("writer");
		String subject = request.getParameter("subject")== null ? "":request.getParameter("subject");
		String email = request.getParameter("email")== null ? "":request.getParameter("email");
		String password = request.getParameter("password")== null ? "":request.getParameter("password");
		String content = request.getParameter("content")== null ? "":request.getParameter("content");
		if(writer.equals("") || subject.equals("") || password.equals("") || content.equals("")) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('필수입력항목을 입력해주세요');");
			out.println("location.href='BoardWriteForm.jsp';");
			out.println("</script>");
			return;
		}
		
		BoardBean bean = new BoardBean();
		bean.setWriter(writer);
		bean.setSubject(subject);
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setContent(content);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertBoard(bean);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
		dispatcher.forward(request, response);
	}
}

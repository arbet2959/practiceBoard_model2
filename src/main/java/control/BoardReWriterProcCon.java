package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardReWriterProcCon.do")
public class BoardReWriterProcCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String writer = request.getParameter("writer") == null ? "":request.getParameter("writer");
		String subject = request.getParameter("subject")== null ? "":request.getParameter("subject");
		String email = request.getParameter("email")== null ? "":request.getParameter("email");
		String password = request.getParameter("password")== null ? "":request.getParameter("password");
		String content = request.getParameter("content")== null ? "":request.getParameter("content");
		
		BoardBean bean = new BoardBean();
		bean.setWriter(writer);
		bean.setSubject(subject);
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setContent(content);
		bean.setRef(Integer.parseInt(request.getParameter("ref")));
		bean.setRe_step(Integer.parseInt(request.getParameter("re_step")));
		bean.setRe_level(Integer.parseInt(request.getParameter("re_level")));
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.reInsertBoard(bean);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
		dispatcher.forward(request, response);
	}
}

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

@WebServlet("/BoardUpdateProcCon.do")
public class BoardUpdateProcCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		if(request.getParameter("password")==null) {
			request.setAttribute("msg", "비밀번호미입력");
			RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
			dispatcher.forward(request, response);
			return;
		}
		if(!request.getParameter("realPassword").equals(request.getParameter("password"))) {
			request.setAttribute("msg", "비밀번호불일치");
			RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
			dispatcher.forward(request, response);
			return;
		}
		
		int num = Integer.parseInt(request.getParameter("num"));
		String content = request.getParameter("content");
		String subject = request.getParameter("subject");
		BoardBean bean = new BoardBean();
		bean.setNum(num);
		bean.setContent(content);
		bean.setSubject(subject);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.updateBoard(bean);
		request.setAttribute("msg", "수정완료");
		RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
		dispatcher.forward(request, response);
		
	}
}

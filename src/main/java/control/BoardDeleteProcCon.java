package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;

@WebServlet("/BoardDeleteProcCon.do")
public class BoardDeleteProcCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.deleteBoard(num);
		request.setAttribute("msg", "삭제완료");
		RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon.do");
		dispatcher.forward(request, response);
	
	}
}

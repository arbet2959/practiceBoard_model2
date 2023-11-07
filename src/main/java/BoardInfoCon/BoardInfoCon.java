package BoardInfoCon;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardInfoCon.do")
public class BoardInfoCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean bean = new BoardBean();
		bean = dao.getOneBoard(num, true);
		
		request.setAttribute("bean", bean);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BoardInfo.jsp");
		dispatcher.forward(request, response);
	}
}

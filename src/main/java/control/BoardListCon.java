package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardListCon.do")
public class BoardListCon extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageSize=10; //화면에 보여질 글의 갯수
		String pageNum = request.getParameter("pageNum"); //클라이언트가 누른 페이지
		if(pageNum==null) pageNum="1"; //맨처음에는 1
		int count=0; //전체 게시글 수
		int number=0; // 화면에 보여질 숫자값
		
		int currentPage = Integer.parseInt(pageNum); //현재페이지번호
		BoardDAO dao = BoardDAO.getInstance();
		count = dao.getAllCount();
		
		int startRow = (currentPage-1)*pageSize+1; //페이지 시작번호
		int endRow = currentPage*10;//페이지 마지막번호
		
		ArrayList<BoardBean> beans = dao.getAllBoard(startRow,pageSize);
		number = count- (currentPage-1)*pageSize;
		
		request.setAttribute("beans", beans);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("number", number);
		request.setAttribute("currentPage", currentPage);
		if(request.getAttribute("msg")!=null) {
			System.out.println("msg.. 안쪽");
			System.out.println(request.getAttribute("msg"));
			String msg = (String) request.getAttribute("msg");
			request.setAttribute("msg", msg);
		}
		RequestDispatcher dis = request.getRequestDispatcher("BoardList.jsp");
		dis.forward(request, response);
	}
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql="";
	ArrayList<BoardBean> beans;
	BoardBean bean;
	int res=0;
	
	private BoardDAO() {}
	private static class BoardDAOHolder{
		private static final BoardDAO INSTANCE = new BoardDAO();
	}
	 
	public static BoardDAO getInstance() {
		return BoardDAOHolder.INSTANCE;
	}
	
	private Connection getConnection() {
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public void pstmtClose() {
		try {
			if(pstmt!=null)pstmt.close();
		} catch (Exception e) {	}
		try {
			if(conn!=null)conn.close();
		} catch (Exception e) {	}
	}
	
	public void rsClose() {
		try {
			if(rs!=null)rs.close();
		} catch (Exception e) {	}
		try {
			if(pstmt!=null)pstmt.close();
		} catch (Exception e) {	}
		try {
			if(conn!=null)conn.close();
		} catch (Exception e) {	}
		
	}
	
	public int getAllCount() {
		conn = getConnection();
		int count=0;
		sql = "select count(*) from board";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			rsClose();
		}
		return count;
	}
	
	//start부터 10개리턴받는 메소드
	public ArrayList<BoardBean> getAllBoard(int start, int pageSize) {
		ArrayList<BoardBean> beans = new ArrayList<>();
		conn = getConnection();
		sql = "select * from board order by ref desc, re_step asc, re_level desc limit ?,?"; //오라클이면rownum
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, pageSize);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				beans.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			 rsClose(); 
			
		}
		return beans;
	}
	
	public BoardBean getOneBoard(int num,boolean a) {
		conn = getConnection();
		sql = "select * from board where num = ?";
		BoardBean bean = new BoardBean();
		try {

			if(a) setCountPlus(num); //조회수증가하기
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rsClose();
		}
		return bean;
	}
	
	public void setCountPlus(int num) {
		String readsql = "update board set readcount = readcount+1 where num=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			/* pstmtClose(); */
		}
	}
	
	//새글등록 ref는 가장높은거에서+1 re_step re_level은 새글이니까 1 ref0짜리 글은 공지
	public int insertBoard(BoardBean bean) {
		int ref=0;
		int re_step=1;
		int re_level=1;
		try {
			conn = getConnection();
			String refsql ="select max(ref) from board";
			pstmt = conn.prepareStatement(refsql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1; //최댓값 구한거에 +1
			}else {
				ref = 1;
			}
			
			sql = "insert into board values(default,?,?,?,?,now(),?,?,?,0,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			res = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rsClose();
		}
		return res;
	}
	
	public int reInsertBoard(BoardBean bean) {
		int ref=bean.getRef();
		int re_step=bean.getRe_step();
		int re_level=bean.getRe_level();
		String levelsql="update	board set re_level=re_level+1 where ref=? and re_level>?";
		//단계는 부모단계보다 +1
		//레벨은 부모레벨보다 큰숫자를 전부다 1씩증가시키고 나는 부모레벨+1
		//ref최댓값을구해서 +1한뒤 insert하고 전체보기할떄 정렬방법바꾸는 다른방법도있을듯
		try {
			conn = getConnection();
			pstmt= conn.prepareStatement(levelsql);
			pstmt.setInt(1, re_step);
			pstmt.setInt(2, re_level);
			res = pstmt.executeUpdate();
			pstmt.close();
			
			
			sql = "insert into board values(default,?,?,?,?,now(),?,?,?,0,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step+1);
			pstmt.setInt(7, re_level+1);
			pstmt.setString(8, bean.getContent());
			res = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rsClose();
		}
		return res;
	}
	
	public int updateBoard(BoardBean bean) {
		sql = "update board set subject=? , content=? where num=?";

		try {
			conn = getConnection();

			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getNum());
			res = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rsClose();
		}
		return res;
	}
	
	public int deleteBoard(int num) {
		conn = getConnection();
		sql = "delete from board where num = ?";
		;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			res= pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pstmtClose();
		}
		return res;
	}
}

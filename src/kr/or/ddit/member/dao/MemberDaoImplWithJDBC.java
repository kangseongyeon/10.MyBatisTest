package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;

public class MemberDaoImplWithJDBC implements IMemberDao {
	// 나 자신의 타입 객체 저장을 위한 객체 변수 선언 (static으로 선언한다.)

	private static IMemberDao memDao = new MemberDaoImplWithJDBC();

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private MemberDaoImplWithJDBC() {

	}

	public static IMemberDao getInstance() {
		return memDao;
	}

	@Override
	public int insertMember(MemberVO mv) {
		int cnt = 0;
		try {
			// 옵션...
			// Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = JDBCUtil3.getConnection();

			String sql = " INSERT INTO MYMEMBER (MEM_ID, MEM_NAME, MEM_TEL, MEM_ADDR) " + " VALUES (?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, mv.getMemId());
			pstmt.setString(2, mv.getMemName());
			pstmt.setString(3, mv.getMemTel());
			pstmt.setString(4, mv.getMemAddr());

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 자원반납
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			String sql = " update mymember " + " set mem_name = ?, mem_tel = ?, mem_addr = ? " + " where mem_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemName());
			pstmt.setString(2, mv.getMemTel());
			pstmt.setString(3, mv.getMemAddr());
			pstmt.setString(4, mv.getMemId());

			cnt = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		boolean isExist = false;
		try {
			conn = JDBCUtil3.getConnection();

			String sql = " select count(*) as cnt\r\n" + "from mymember\r\n" + "where mem_id = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memId);

			rs = pstmt.executeQuery();

			int cnt = 0;

			while (rs.next()) {
				cnt = rs.getInt(1);
			}

			if (cnt > 0) {
				isExist = true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// 자원반납
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return isExist;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();

			String sql = " delete from mymember where mem_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			cnt = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			conn = JDBCUtil3.getConnection();

			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from mymember");

			while (rs.next()) {
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");

				// 날짜 & 시간 가져옴
				// LocalDateTime regDt = rs.getTimestamp("reg_dt").toLocalDateTime();

				// 날짜만 가져옴
				LocalDate regDt = rs.getTimestamp("reg_dt").toLocalDateTime().toLocalDate();

				MemberVO mv = new MemberVO();
				mv.setMemId(memId);
				mv.setMemName(memName);
				mv.setMemTel(memTel);
				mv.setMemAddr(memAddr);
				mv.setRegDt(regDt);

				memList.add(mv);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return memList;
	}

	@Override
	public List<MemberVO> searchMember(MemberVO mv) {

		List<MemberVO> memList = new ArrayList<MemberVO>();

		try {
			conn = JDBCUtil3.getConnection();

			String sql = "select * from mymember where 1 = 1";
			if (mv.getMemId() != null && !mv.getMemId().equals("")) {
				sql += " and mem_id = ? ";
			}
			if (mv.getMemName() != null && !mv.getMemName().equals("")) {
				sql += " and mem_name = ? ";
			}
			if (mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				sql += " and mem_tel = ? ";
			}
			if (mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				sql += " and mem_addr like '%' || ? || '%' ";
			}

			pstmt = conn.prepareStatement(sql);

			int paramIndex = 1;
			if (mv.getMemId() != null && !mv.getMemId().equals("")) {
				pstmt.setString(paramIndex++, mv.getMemId());
			}
			if (mv.getMemName() != null && !mv.getMemName().equals("")) {
				pstmt.setString(paramIndex++, mv.getMemName());
			}
			if (mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				pstmt.setString(paramIndex++, mv.getMemTel());
			}
			if (mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				pstmt.setString(paramIndex++, mv.getMemAddr());
			}

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");

				// 날짜 & 시간 가져옴
				// LocalDateTime regDt = rs.getTimestamp("reg_dt").toLocalDateTime();

				// 날짜만 가져옴
				LocalDate regDt = rs.getTimestamp("reg_dt").toLocalDateTime().toLocalDate();

				MemberVO mv2 = new MemberVO();
				mv2.setMemId(memId);
				mv2.setMemName(memName);
				mv2.setMemTel(memTel);
				mv2.setMemAddr(memAddr);
				mv2.setRegDt(regDt);

				memList.add(mv2);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return memList;
	}

}

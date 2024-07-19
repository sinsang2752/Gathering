package com.shinhan.gath.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.gath.dto.AttendMeetDTO;
import com.shinhan.gath.dto.MeetingDTO;
import com.shinhan.gath.util.DBUtil;

public class MeetingDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public List<MeetingDTO> findByGNo (String id,int gNo) {
		List<MeetingDTO> metDtoArr = new ArrayList<>();
		String sql = "SELECT MET_NO, MET_TITLE, MET_PRICE, MET_PLACE, MET_ENT_DATE"
				+ ", TO_CHAR(MET_EXP_DATE, 'yyyy/mm/dd hh24:mi') AS MET_EXP_DATE, MET_CONTENT, MET_MAX_MEM, G_NO, MGR_ID"
				+ ", (SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = met.MET_NO) || '/' || met.MET_MAX_MEM AS J_COUNT"
				+ ", MET_IMG"
				+ ", (SELECT COUNT (*) FROM ATTENDING a WHERE a.MET_NO = met.MET_NO AND AT_ID = ?) AS ATT_CHECK"
				+ " FROM MEETING met"
				+ " WHERE G_NO = ? "
				+ " AND SYSDATE < MET_EXP_DATE";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setInt(2, gNo);
			rs = pst.executeQuery();
			while (rs.next()) {
				metDtoArr.add(makeDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return metDtoArr;
	}
	
	public List<MeetingDTO> findByGNoDel (int gNo) {
		List<MeetingDTO> metDtoArr = new ArrayList<>();
		String sql = "SELECT MET_NO, MET_TITLE, MET_PRICE, MET_PLACE, MET_ENT_DATE"
				+ ", TO_CHAR(MET_EXP_DATE, 'yyyy/mm/dd hh24:mi') AS MET_EXP_DATE, MET_CONTENT, MET_MAX_MEM, G_NO, MGR_ID"
				+ ", (SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = met.MET_NO) || '/' || met.MET_MAX_MEM AS J_COUNT"
				+ " FROM MEETING met"
				+ " WHERE G_NO = ? ";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			rs = pst.executeQuery();
			while (rs.next()) {
				metDtoArr.add(makeDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return metDtoArr;
	}
	
	
	public List<MeetingDTO> yetAttMeet (int gNo, String id) {
		List<MeetingDTO> metDtoArr = new ArrayList<>();
		String sql = "SELECT met.MET_NO, MET_TITLE, MET_PRICE, MET_PLACE, MET_ENT_DATE, TO_CHAR(MET_EXP_DATE, 'yyyy-mm-dd hh24:mi') AS MET_EXP_DATE"
				+ ", MET_CONTENT, MET_MAX_MEM, G_NO, MGR_ID"
				+ ",(SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = met.MET_NO) || '/' || met.MET_MAX_MEM AS J_COUNT"
				+ " FROM MEETING  met"
				+ " WHERE G_NO = ?"
				+ " AND met.MET_NO NOT IN (SELECT MET_NO FROM ATTENDING WHERE AT_ID = ?)"
				+ " AND SYSDATE < MET_EXP_DATE";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			pst.setString(2, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				metDtoArr.add(makeDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return metDtoArr;
	}
	
	public int joinMeet(int metNo, String id) {
		int result = 0;
		String sql = "INSERT INTO ATTENDING VALUES(?,?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, metNo);
			pst.setString(2, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int joinCount (int metNo) {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, metNo);
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}
	
	public int withdrawMeet (int metNo, String id) {
		int result = 0;
		String sql = "DELETE FROM ATTENDING WHERE MET_NO = ? AND AT_ID = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, metNo);
			pst.setString(2, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int delMeet (int metNo) {
		int result = 0;
		String sql = "DELETE FROM MEETING WHERE MET_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, metNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public List<AttendMeetDTO> attendMeet(int gNo, String id) {
		List<AttendMeetDTO> atMeetArr = new ArrayList<>();
		String sql = "SELECT *"
				+ " FROM MEETING met"
				+ " JOIN  ATTENDING att ON(met.MET_NO = att.MET_NO)"
				+ " WHERE met.G_NO = ?"
				+ " AND AT_ID = ?"
				+ " AND SYSDATE < met.MET_EXP_DATE";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			pst.setString(2, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				atMeetArr.add(makeAttMeetDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return atMeetArr;
	}
	
	public int insMeet(MeetingDTO metDto) {
		int result = 0;
		String sql = "INSERT INTO MEETING"
				+ " VALUES(SEQ_MEETING.NEXTVAL, ?, ?, ?, SYSDATE, TO_DATE(?, 'yyyy/mm/dd hh24:mi'), ? , ?, ?, ?, ?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, metDto.getMetTitle());
			pst.setInt(2, metDto.getMetPrice());
			pst.setString(3, metDto.getMetPlace());
			pst.setString(4, metDto.getMetExpDate());
			pst.setString(5, metDto.getMetContent());
			pst.setInt(6, metDto.getMetMaxMem());
			pst.setInt(7, metDto.getGNo());
			pst.setString(8, metDto.getMgrId());
			pst.setString(9, metDto.getMetImg());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public List<Integer> meetDays(String id, String month) {
		List<Integer> days = new ArrayList<>();
		
		String sql = "SELECT TO_CHAR(MET_EXP_DATE, 'dd') AS day"
				+ " FROM MEETING a, ATTENDING b"
				+ " WHERE a.MET_NO = b.MET_NO"
				+ " AND b.AT_ID = ?"
				+ " AND TO_CHAR(MET_EXP_DATE,'yyyy/mm') = TO_CHAR(TO_DATE(?, 'yyyy/mm'), 'yyyy/mm')";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id); 
			pst.setString(2, month);
			rs = pst.executeQuery();
			while(rs.next()) {
				days.add(rs.getInt(1));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return days;
	}
	
	public List<MeetingDTO> toDoMeetList(String meetDay, String id) {
		List<MeetingDTO> meetList = new ArrayList<>();
		String sql = "SELECT a.*"
				+ ", (SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = a.MET_NO) || '/' || a.MET_MAX_MEM AS J_COUNT"
				+ " FROM MEETING a, ATTENDING b"
				+ " WHERE a.MET_NO = b.MET_NO"
				+ " AND TO_CHAR(MET_EXP_DATE, 'yyyy/mm/dd') = TO_CHAR(TO_DATE(?, 'yyyy/mm/dd'), 'yyyy/mm/dd')"
				+ " AND b.AT_ID = ?"
				+ " ORDER BY MET_EXP_DATE";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, meetDay); 
			pst.setString(2, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				meetList.add(makeToDoDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return meetList;
	}
	
	private AttendMeetDTO makeAttMeetDto (ResultSet rs) throws SQLException {
		AttendMeetDTO attendMeetDto = new AttendMeetDTO();
		attendMeetDto.setMetNo(rs.getInt("MET_NO"));
		attendMeetDto.setMetTitle(rs.getString("MET_TITLE"));
		attendMeetDto.setMetPrice(rs.getInt("MET_PRICE"));
		attendMeetDto.setMetPlace(rs.getString("MET_PLACE"));
		attendMeetDto.setMetEntDate(rs.getDate("MET_ENT_DATE"));
		attendMeetDto.setMetExpDate(rs.getString("MET_EXP_DATE"));
		attendMeetDto.setMetContent(rs.getString("MET_CONTENT"));
		attendMeetDto.setMetMaxMem(rs.getInt("MET_MAX_MEM"));
		attendMeetDto.setGNo(rs.getInt("G_NO"));
		attendMeetDto.setAtId(rs.getString("AT_ID"));
		return attendMeetDto;
	}
	
	private MeetingDTO makeDto (ResultSet rs) throws SQLException {
		MeetingDTO meetDto = new MeetingDTO();
		meetDto.setMetNo(rs.getInt("MET_NO"));
		meetDto.setMetTitle(rs.getString("MET_TITLE"));
		meetDto.setMetPrice(rs.getInt("MET_PRICE"));
		meetDto.setMetPlace(rs.getString("MET_PLACE"));
		meetDto.setMetEntDate(rs.getDate("MET_ENT_DATE"));
		meetDto.setMetExpDate(rs.getString("MET_EXP_DATE"));
		meetDto.setMetContent(rs.getString("MET_CONTENT"));
		meetDto.setMetMaxMem(rs.getInt("MET_MAX_MEM"));
		meetDto.setGNo(rs.getInt("G_NO"));
		meetDto.setMgrId(rs.getString("MGR_ID"));
		meetDto.setJCount(rs.getString("J_COUNT"));
		meetDto.setMetImg(rs.getString("MET_IMG"));
		meetDto.setAttCheck(rs.getInt("ATT_CHECK"));
		return meetDto;
	}
	
	private MeetingDTO makeToDoDto (ResultSet rs) throws SQLException {
		MeetingDTO meetDto = new MeetingDTO();
		meetDto.setMetNo(rs.getInt("MET_NO"));
		meetDto.setMetTitle(rs.getString("MET_TITLE"));
		meetDto.setMetPrice(rs.getInt("MET_PRICE"));
		meetDto.setMetPlace(rs.getString("MET_PLACE"));
		meetDto.setMetEntDate(rs.getDate("MET_ENT_DATE"));
		meetDto.setMetExpDate(rs.getString("MET_EXP_DATE"));
		meetDto.setMetContent(rs.getString("MET_CONTENT"));
		meetDto.setMetMaxMem(rs.getInt("MET_MAX_MEM"));
		meetDto.setGNo(rs.getInt("G_NO"));
		meetDto.setMgrId(rs.getString("MGR_ID"));
		meetDto.setJCount(rs.getString("J_COUNT"));
		meetDto.setMetImg(rs.getString("MET_IMG"));
		return meetDto;
	}
}
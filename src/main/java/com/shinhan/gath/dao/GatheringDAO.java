package com.shinhan.gath.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.gath.dto.AttendingMeetDTO;
import com.shinhan.gath.dto.CategoryDTO;
import com.shinhan.gath.dto.GatheringCnoDTO;
import com.shinhan.gath.dto.GatheringDTO;
import com.shinhan.gath.dto.GathmemberDTO;
import com.shinhan.gath.dto.HotGathDTO;
import com.shinhan.gath.dto.ResAttendGathDTO;
import com.shinhan.gath.util.DBUtil;

public class GatheringDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	public List<CategoryDTO> findAllCategory() {
		List<CategoryDTO> cateArr = new ArrayList<>();
		String sql = "SELECT * FROM CATEGORY";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				CategoryDTO cateDto = new CategoryDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				cateArr.add(cateDto);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return cateArr;
	}
	
	public int insCate(String name, String id) {
		int result = 0;
		String sql = "INSERT INTO CATEGORY VALUES (SEQ_CATEGORY.NEXTVAL,?,?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	public int delCate(int cNo) {
		int result = 0;
		String sql = "DELETE FROM CATEGORY WHERE C_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	public List<GatheringCnoDTO> findByCNo(int sel) {
		List<GatheringCnoDTO> gathArr = new ArrayList<>();
		String sql = "SELECT g.* , (SELECT COUNT(*) FROM GATH_MEMBER WHERE G_NO = g.G_NO) AS MEM_CNT"
				+ " FROM GATHERING g "
				+ " WHERE C_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, sel);
			rs = pst.executeQuery();
			while(rs.next()) {
				GatheringCnoDTO cateDto = new GatheringCnoDTO(rs.getInt(1), rs.getString(2),
														rs.getString(3), rs.getString(4),
														rs.getInt(5), rs.getString(6), 
														rs.getInt(7));
				gathArr.add(cateDto);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return gathArr;
	}
	
	public int joinGathering(String id, int gNo) {
		int result = 0;
		String sql = "INSERT INTO GATH_MEMBER(ID, G_NO) VALUES(?, ?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setInt(2, gNo);
			result = pst.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e){
			System.out.println("이미 가입된 모임입니다!");
			result = -1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	public int createGath(GatheringDTO gathDto) {
		int result = 0;
		String sql = "INSERT INTO GATHERING VALUES"
				   + " (SEQ_GATHERING.NEXTVAL, ?, ?, ?, ?, ?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, gathDto.getGTitle());
			pst.setString(2, gathDto.getGIntro());
			pst.setString(3, gathDto.getMgrId());
			pst.setInt(4, gathDto.getCNo());
			pst.setString(5, gathDto.getGImg());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	public List<ResAttendGathDTO> attendGath(String id) {
		List<ResAttendGathDTO> attendArr = new ArrayList<>();
		String sql = "SELECT C_NAME, G_TITLE, G_NO"
				+ " FROM GATHERING gat JOIN GATH_MEMBER mem USING(G_NO)"
				+ " JOIN CATEGORY cat ON(gat.C_NO = cat.C_NO)"
				+ " WHERE mem.ID = ?"
				+ " ORDER BY gat.C_NO, G_NO";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				ResAttendGathDTO AttendDto = new ResAttendGathDTO(rs.getString(1), rs.getString(2), rs.getInt(3));
				attendArr.add(AttendDto);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return attendArr;
	}
	
	public GatheringDTO findByGNo(int gNo) {
		GatheringDTO gathDto = null;
		String sql = "SELECT * FROM GATHERING WHERE G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			rs = pst.executeQuery();
			if (rs.next()) {
				gathDto = new GatheringDTO(rs.getInt(1), rs.getString(2), rs.getString(3), 
										  rs.getString(4), rs.getInt(5), rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return gathDto;
	}
	
	public List<GatheringDTO> findByMgrid(String id) {
		List<GatheringDTO> gathArr = new ArrayList<>();
		String sql = "SELECT * FROM GATHERING WHERE MGR_ID = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				GatheringDTO cateDto = new GatheringDTO(rs.getInt(1), rs.getString(2),
														rs.getString(3), rs.getString(4),
														rs.getInt(5), rs.getString(6));
				gathArr.add(cateDto);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return gathArr;
	}
	
	public List<GathmemberDTO> gathJoinList(int gNo) {
		List<GathmemberDTO> joinList = new ArrayList<>();
		String sql = "SELECT A.ID, A.G_NO, A.JOIN_DATE, B.NAME, B.IMG"
				+ " FROM GATH_MEMBER A, ACCOUNT B"
				+ " WHERE A.ID = B.ID"
				+ " AND G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			rs = pst.executeQuery();
			while(rs.next()) {
				GathmemberDTO joinMem = new GathmemberDTO(rs.getString("ID"), rs.getInt("G_NO")
						,rs.getDate("JOIN_DATE"), rs.getString("NAME"), rs.getString("IMG"));
				joinList.add(joinMem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return joinList;
	}
	
	public int delGath(int gNo) {
		int result = 0;
		String sql = "DELETE FROM GATHERING WHERE G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int updateGath (GatheringDTO gathDto) {
		int result = 0;
		String sql = "UPDATE GATHERING SET G_INTRO = ?, G_IMG = ? WHERE G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, gathDto.getGIntro());
			pst.setString(2, gathDto.getGImg());
			pst.setInt(3, gathDto.getGNo());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int updateGathNoImg (GatheringDTO gathDto) {
		int result = 0;
		String sql = "UPDATE GATHERING SET G_INTRO = ? WHERE G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, gathDto.getGIntro());
			pst.setInt(2, gathDto.getGNo());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int withdrawGath(String id, int gNo) {
		int result = 0;
		String sql = "DELETE FROM GATH_MEMBER WHERE ID = ? AND G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setInt(2, gNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public List<AttendingMeetDTO> atMet(String id) {
		List<AttendingMeetDTO> atMetArr = new ArrayList<>();
		
		String sql = "SELECT gath.G_TITLE, TO_CHAR(met.MET_EXP_DATE, 'MM/dd hh24:mi') EXP_DATE"
				+ ", met.MET_TITLE, met.MET_PRICE, met.MET_PLACE"
				+ ", (SELECT COUNT(*) FROM ATTENDING WHERE MET_NO = met.MET_NO) || '/' || met.MET_MAX_MEM AS J_COUNT"
				+ ", gath.G_IMG, met.MET_IMG"
				+ " FROM MEETING met JOIN ATTENDING att ON(met.MET_NO = att.MET_NO)"
				+ " JOIN GATH_MEMBER gm ON(gm.G_NO = met.G_NO)"
				+ " JOIN GATHERING gath ON (gm.G_NO = gath.G_NO)"
				+ " WHERE TO_DATE(SYSDATE,'yyyy/mm/dd') = TO_DATE(met.MET_EXP_DATE,'yyyy/mm/dd')"
				+ " AND SYSDATE < met.MET_EXP_DATE"
				+ " AND att.AT_ID = gm.ID"
				+ " AND att.AT_ID = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				atMetArr.add(new AttendingMeetDTO(rs.getString("G_TITLE"), rs.getString("EXP_DATE")
						, rs.getString("MET_TITLE"), rs.getInt("MET_PRICE"), rs.getString("MET_PLACE")
						, rs.getString("J_COUNT"), rs.getString("G_IMG"), rs.getString("MET_IMG")));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return atMetArr;
	}
	
	public List<HotGathDTO> hotGathList() {
		List<HotGathDTO> list = new ArrayList<>();
		String sql = "SELECT a.* FROM ("
				+ " SELECT c.C_NAME, g.*, (SELECT COUNT(*) FROM MEETING WHERE G_NO = g.G_NO) AS MET_CNT,"
				+ " (SELECT COUNT(*) FROM GATH_MEMBER WHERE G_NO = g.G_NO) AS MEM_CNT"
				+ " FROM GATHERING g JOIN CATEGORY c ON(g.C_NO = c.C_NO)"
				+ " ORDER BY MET_CNT DESC) a"
				+ " WHERE ROWNUM <= 7";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				list.add(new HotGathDTO(rs.getInt(2), rs.getString(3)
						, rs.getString(4), rs.getInt(6), rs.getString(1)
						, rs.getString(7), rs.getInt(8), rs.getInt(9)));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return list;
	}
	
	public List<CategoryDTO> joinCateList(String id) {
		List<CategoryDTO> list = new ArrayList<>();
		String sql = "SELECT cat.C_NO, C_NAME, C_IMG"
				+ " FROM GATHERING gat JOIN GATH_MEMBER mem USING(G_NO)"
				+ " JOIN CATEGORY cat ON(gat.C_NO = cat.C_NO)"
				+ " WHERE mem.ID = ?"
				+ " GROUP BY cat.C_NO, C_NAME, C_IMG";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				CategoryDTO cate = new CategoryDTO();
				cate.setCNo(rs.getInt("C_NO"));
				cate.setCName(rs.getString("C_NAME"));
				cate.setCImg(rs.getString("C_IMG"));
				list.add(cate);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return list;
	}
	
	public List<HotGathDTO> joinGathList(String id, int cNo) {
		List<HotGathDTO> list = new ArrayList<>();
		String sql = "SELECT gat.G_NO, G_TITLE, G_INTRO, G_IMG, cat.C_NAME, (SELECT COUNT(*) FROM GATH_MEMBER WHERE G_NO = gat.G_NO) AS MEM_CNT"
				+ " FROM GATHERING gat JOIN GATH_MEMBER mem ON(gat.G_NO = mem.G_NO)"
				+ " JOIN CATEGORY cat ON(gat.C_NO = cat.C_NO)"
				+ " WHERE mem.ID = ?"
				+ " AND cat.C_NO = ?"
				+ " ORDER BY mem.join_date DESC";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setInt(2, cNo);
			rs = pst.executeQuery();
			while(rs.next()) {
				HotGathDTO gath = new HotGathDTO();
				gath.setGNo(rs.getInt("G_NO"));
				gath.setGTitle(rs.getString("G_TITLE"));
				gath.setGIntro(rs.getString("G_INTRO"));
				gath.setGImg(rs.getString("G_IMG"));
				gath.setCName(rs.getString("C_NAME"));
				gath.setMemCnt(rs.getInt("MEM_CNT"));
				list.add(gath);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return list;
	}
	
	public List<HotGathDTO> joinGathListAll(String id) {
		List<HotGathDTO> list = new ArrayList<>();
		String sql = "SELECT gat.G_NO, G_TITLE, G_INTRO, G_IMG, cat.C_NAME, (SELECT COUNT(*) FROM GATH_MEMBER WHERE G_NO = gat.G_NO) AS MEM_CNT"
				+ " FROM GATHERING gat JOIN GATH_MEMBER mem ON(gat.G_NO = mem.G_NO)"
				+ " JOIN CATEGORY cat ON(gat.C_NO = cat.C_NO)"
				+ " WHERE mem.ID = ?"
				+ " ORDER BY mem.join_date DESC";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				HotGathDTO gath = new HotGathDTO();
				gath.setGNo(rs.getInt("G_NO"));
				gath.setGTitle(rs.getString("G_TITLE"));
				gath.setGIntro(rs.getString("G_INTRO"));
				gath.setGImg(rs.getString("G_IMG"));
				gath.setCName(rs.getString("C_NAME"));
				gath.setMemCnt(rs.getInt("MEM_CNT"));
				list.add(gath);
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return list;
	}
	
	
}

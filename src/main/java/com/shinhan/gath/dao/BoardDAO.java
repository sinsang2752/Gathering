package com.shinhan.gath.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.gath.dto.BoardDTO;
import com.shinhan.gath.dto.BoardfileDTO;
import com.shinhan.gath.util.DBUtil;

public class BoardDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	public List<BoardDTO> findByGNo(int gNo) {
		List<BoardDTO> boardArr = new ArrayList<>();
		String sql = "SELECT BOARD.*"
				+ ", (SELECT DECODE(COUNT(*), 0, 'X', 'O') FROM BOARD_FILES WHERE B_NO = BOARD.B_NO) AS IS_FILE "
				+ " FROM BOARD WHERE G_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, gNo);
			rs = pst.executeQuery();
			while(rs.next()) {
				boardArr.add(makeDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return boardArr;
	}
	
	public BoardDTO findByBNo(int bNo) {
		BoardDTO boardDto = new BoardDTO();
		String sql = "SELECT BOARD.*"
				+ ", (SELECT DECODE(COUNT(*), 0, 'X', 'O') FROM BOARD_FILES WHERE B_NO = BOARD.B_NO) AS IS_FILE "
				+ " FROM BOARD WHERE B_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bNo);
			rs = pst.executeQuery();
			if(rs.next()) {
				makeDto(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return boardDto;
	}
	
	public int maxBno() {
		int result = 0;
		String sql = "SELECT MAX(B_NO) FROM board";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public List<BoardfileDTO> filesFindByBNo(int bNo) {
		List<BoardfileDTO> fileArr = new ArrayList<>();
		String sql = "SELECT * FROM BOARD_FILES WHERE B_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bNo);
			rs = pst.executeQuery();
			while(rs.next()) {
				fileArr.add(makeFileDto(rs));
			}
		} catch (SQLRecoverableException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return fileArr;
	}
	
	public int insBoard(BoardDTO boardDto) {
		int result = 0;
		String sql = "INSERT INTO BOARD VALUES(SEQ_BOARD.NEXTVAL, ?, ?, ?, ?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, boardDto.getBTitle());
			pst.setString(2, boardDto.getBContent());
			pst.setString(3, boardDto.getMgrId());
			pst.setInt(4, boardDto.getGNo());
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int delBoard(int bNo) {
		int result = 0;
		String sql = "DELETE FROM BOARD WHERE B_NO = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	public int insFile(BoardfileDTO fileDto) {
		int result = 0;
		String sql = "INSERT INTO BOARD_FILES (F_NO, B_NO, F_NAME) VALUES ( SEQ_FILES.NEXTVAL, ?, ?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, fileDto.getBNo());
			pst.setString(2, fileDto.getFName());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	private BoardDTO makeDto(ResultSet boardRs) throws SQLException {
		BoardDTO boardDto = new BoardDTO();
		boardDto.setBNo(boardRs.getInt("B_NO"));
		boardDto.setBTitle(boardRs.getString("B_TITLE"));
		boardDto.setBContent(boardRs.getString("B_CONTENT"));
		boardDto.setMgrId(boardRs.getString("MGR_ID"));
		boardDto.setGNo(boardRs.getInt("G_NO"));
		boardDto.setIsFile(boardRs.getString("IS_FILE"));
		if(boardDto.getIsFile().equals("O")) {
			boardDto.setFiles(filesFindByBNo(boardDto.getBNo()));
		}
		return boardDto;
	}
	
	private BoardfileDTO makeFileDto(ResultSet fileRs) throws SQLException {
		BoardfileDTO fileDto = new BoardfileDTO();
		fileDto.setFNo(fileRs.getInt("F_NO"));
		fileDto.setBNo(fileRs.getInt("B_NO"));
		fileDto.setFName(fileRs.getString("F_NAME"));
		fileDto.setFUploadDate(fileRs.getDate("F_UPLOAD_DATE"));
		return fileDto;
	}
}

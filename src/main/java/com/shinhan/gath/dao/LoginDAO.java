package com.shinhan.gath.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.util.DBUtil;

public class LoginDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	public AccDTO loginCheck(String id, String pw) {
		AccDTO accDto = null;
		String sql = "SELECT * FROM ACCOUNT WHERE id = ? AND pw = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pw);
			rs = pst.executeQuery();
			if(rs.next())
				accDto = new AccDTO(rs.getString(1),rs.getString(2),
							rs.getString(3),rs.getInt(4), rs.getString(5));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return accDto;
	}
	
	public int signUp(AccDTO acc) {
		int result = 1;
		String sql = "INSERT INTO ACCOUNT VALUES(?,?,?,?,?)";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, acc.getId());
			pst.setString(2, acc.getPw());
			pst.setString(3, acc.getName());
			pst.setInt(4, acc.getAuth());
			pst.setString(5, acc.getImg());
			pst.executeUpdate();
		}catch (SQLIntegrityConstraintViolationException e) {
			result = 2;
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
}

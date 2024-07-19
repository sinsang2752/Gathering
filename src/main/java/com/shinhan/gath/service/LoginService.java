package com.shinhan.gath.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.shinhan.gath.dao.LoginDAO;
import com.shinhan.gath.dto.AccDTO;

public class LoginService {
	LoginDAO loginDAO = new LoginDAO();
	
	public int loginCheck(HttpServletRequest req) {
		int result = 0;
		HttpSession session = req.getSession();
		session.removeAttribute("userInfo");
		String id = (String) req.getParameter("userId");
		String pw = (String) req.getParameter("userPw");
		AccDTO accDto = loginDAO.loginCheck(id, pw);
		if (accDto != null) {
			session.setAttribute("userInfo", accDto);
			result = 1;
		}
		
		return result;
	}
	
	public int signUp(MultipartRequest multi) {
		String id = multi.getParameter("id");
		String pw = multi.getParameter("pw");
		String name = multi.getParameter("name");
		String img = multi.getFilesystemName("uploadFile");
		if(img == null) img = "default_profile.png";
		AccDTO accDto = new AccDTO(id,pw,name,0,img);
		return loginDAO.signUp(accDto);
	}
}

package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.service.MeetingService;

/**
 * Servlet implementation class WithdrawMeetServlet
 */
@WebServlet("/withdrawMeet.do")
public class WithdrawMeetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int metNo = Integer.parseInt(request.getParameter("metNo"));
		MeetingService meetService = new MeetingService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		response.setIntHeader("result", meetService.withdrawMeet(metNo, id));
		
	}

}

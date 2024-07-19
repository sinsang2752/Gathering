package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.dto.MeetingDTO;
import com.shinhan.gath.service.MeetingService;

/**
 * Servlet implementation class JoinMeetServlet
 */
@WebServlet("/joinMeet.do")
public class JoinMeetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int metNo = Integer.parseInt(request.getParameter("metNo"));
		int memMax = Integer.parseInt(request.getParameter("memMax"));
		MeetingService meetService = new MeetingService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		MeetingDTO metDto = new MeetingDTO();
		metDto.setMetNo(metNo);
		metDto.setMetMaxMem(memMax);
		
		response.setIntHeader("result", meetService.joinMeet(metDto, id));
		
	}

}

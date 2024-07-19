package com.shinhan.gath.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.dto.GathmemberDTO;
import com.shinhan.gath.dto.MeetingDTO;
import com.shinhan.gath.service.GatheringService;
import com.shinhan.gath.service.MeetingService;

/**
 * Servlet implementation class GetMeetListServlet
 */
@WebServlet("/getMeetList.do")
public class GetMeetListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		MeetingService meetService = new MeetingService();
		GatheringService gathService = new GatheringService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		
		List<GathmemberDTO> gathMemList = gathService.gathJoinList(gNo);
		request.setAttribute("joinCheck", gathService.joinCheck(id, gathMemList));
		
		List<MeetingDTO> meetList = meetService.findByGNo(id, gNo);
		if(meetList.size() != 0) request.setAttribute("meetList", meetList);
		request.getRequestDispatcher("./view/home/meetList.jsp").forward(request, response);
	}

}

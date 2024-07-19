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
 * Servlet implementation class SubGathServlet
 */
@WebServlet("/subGath.do")
public class SubGathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		GatheringService gathService = new GatheringService();
		MeetingService meetService = new MeetingService();
		
		request.setAttribute("gathInfo",  gathService.findByGNo(gNo));
		
		List<MeetingDTO> meetList = meetService.findByGNo(id, gNo);
		if(meetList.size() != 0) request.setAttribute("meetList", meetList);
		
		List<GathmemberDTO> gathMemList = gathService.gathJoinList(gNo);
		request.setAttribute("joinList", gathMemList);
		
		request.setAttribute("joinCheck", gathService.joinCheck(id, gathMemList));
		
		request.getRequestDispatcher("./view/home/subGath.jsp").forward(request, response);
	}

}

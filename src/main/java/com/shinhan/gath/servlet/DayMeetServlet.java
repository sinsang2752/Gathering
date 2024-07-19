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
 * Servlet implementation class DayMeetServlet
 */
@WebServlet("/dayMeet.do")
public class DayMeetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String meetDay = request.getParameter("meetDay");
		MeetingService service = new MeetingService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		request.setAttribute("meetDay", meetDay);
		request.setAttribute("meetList", service.toDoMeetList(meetDay,id));
		
		request.getRequestDispatcher("./view/meet/toDoMeet.jsp").forward(request, response);
	}

}

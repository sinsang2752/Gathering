package com.shinhan.gath.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.service.MeetingService;

@WebServlet("/meetDays.do")
public class MeetDaysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MeetingService meetService = new MeetingService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		String month = request.getParameter("month");

		List<Integer> days = meetService.meetDays(id, month);
		
		JSONObject obj = new JSONObject();
		obj.put("days", days);
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().print(obj);
	}

}

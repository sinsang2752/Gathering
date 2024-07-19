package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.service.GatheringService;

/**
 * Servlet implementation class WithdrawGathServlet
 */
@WebServlet("/withdrawGath.do")
public class WithdrawGathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		GatheringService service = new GatheringService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		
		response.getWriter().append(String.valueOf(service.withdrawGath(id, gNo)));
	}

}

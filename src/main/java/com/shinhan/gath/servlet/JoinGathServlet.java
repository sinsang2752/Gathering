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
 * Servlet implementation class JoinGathServlet
 */
@WebServlet("/joinGath.do")
public class JoinGathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		GatheringService service = new GatheringService();
		AccDTO acc = (AccDTO)session.getAttribute("userInfo");
		String id = acc.getId();
		int result = service.joinGathering(id, gNo);
		response.getWriter().append(String.valueOf(result));
	}

}

package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shinhan.gath.dto.GatheringDTO;
import com.shinhan.gath.service.GatheringService;

/**
 * Servlet implementation class DeleteGathServlet
 */
@WebServlet("/delGath.do")
public class DeleteGathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		
		GatheringService service = new GatheringService();
		
		response.getWriter().append(String.valueOf(service.delGath(gNo)));
		
	}

}

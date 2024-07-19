package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shinhan.gath.service.GatheringService;

@WebServlet("/home.do")
public class HomeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GatheringService gatheringService = new GatheringService();
		gatheringService.findAllCategory(request);
		request.setAttribute("hotGathList", gatheringService.hotGathList());
		request.getRequestDispatcher("./view/home/home.jsp").forward(request, response);
	}

}

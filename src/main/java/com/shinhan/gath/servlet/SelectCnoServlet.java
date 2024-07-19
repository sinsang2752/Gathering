package com.shinhan.gath.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shinhan.gath.service.GatheringService;

/**
 * Servlet implementation class SelectCnoServlet
 */
@WebServlet("/selectCno.do")
public class SelectCnoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cNo = Integer.parseInt(request.getParameter("cNo"));
		String cName = request.getParameter("cateName");
		GatheringService service = new GatheringService();
		request.setAttribute("gathList", service.findByCNo(cNo));
		request.setAttribute("cateName", cName);
		request.getRequestDispatcher("./view/home/subArea.jsp").forward(request, response);
	}

}

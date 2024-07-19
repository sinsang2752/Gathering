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
 * Servlet implementation class updateGathNoImgServlet
 */
@WebServlet("/updateNoImg.do")
public class updateGathNoImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GatheringService service = new GatheringService();
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		String gIntro = request.getParameter("gIntro");
		
		GatheringDTO gath = new GatheringDTO();
		gath.setGNo(gNo);
		gath.setGIntro(gIntro);
		
		response.getWriter().append(String.valueOf(service.updateGathNoImg(gath)));
	}

}

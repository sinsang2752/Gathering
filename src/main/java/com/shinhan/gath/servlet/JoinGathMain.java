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
import com.shinhan.gath.dto.CategoryDTO;
import com.shinhan.gath.dto.HotGathDTO;
import com.shinhan.gath.service.GatheringService;

/**
 * Servlet implementation class JoinGathMain
 */
@WebServlet("/joinGathMain.do")
public class JoinGathMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int joinCno = (request.getParameter("joinCno") == null)? 0 : Integer.parseInt(request.getParameter("joinCno"));
		GatheringService service = new GatheringService();
		HttpSession session = request.getSession();
		AccDTO acc = (AccDTO) session.getAttribute("userInfo");
		String id = (acc == null) ? "" : acc.getId();
		List<HotGathDTO> joinList = null;
		
		List<CategoryDTO> cateList = service.joinCateList(id);
		if(joinCno == 0) joinList = service.joinGathListAll(id);
		else joinList = service.joinGathList(id, joinCno);
		
		request.setAttribute("cateList", cateList);
		request.setAttribute("joinList", joinList);
		
		request.getRequestDispatcher("./view/join/joinGathMain.jsp").forward(request, response);
	}

}

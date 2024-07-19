package com.shinhan.gath.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.shinhan.gath.dto.AccDTO;
import com.shinhan.gath.dto.GatheringDTO;
import com.shinhan.gath.service.GatheringService;

/**
 * Servlet implementation class CreateGathServlet
 */
@WebServlet("/createGath.do")
public class CreateGathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String savePath = "images/gath";
		int uploadFileSizeLimit = 50 * 1024 * 1024;
		String encType = "UTF-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFileSizeLimit
				, encType, new DefaultFileRenamePolicy());
		int result = 0;
		
		GatheringService service = new GatheringService();
		
		String title = multi.getParameter("title");
		String intro = multi.getParameter("intro");
		String img = multi.getFilesystemName("img");
		int cNo = Integer.parseInt(multi.getParameter("cNo"));
		
		HttpSession session = request.getSession();
		AccDTO userInfo = (AccDTO) session.getAttribute("userInfo");
		if(userInfo == null) response.setIntHeader("result", -1);
		
		GatheringDTO gath = new GatheringDTO();
		gath.setGTitle(title);
		gath.setGIntro(intro);
		gath.setGImg(img);
		gath.setCNo(cNo);
		gath.setMgrId(userInfo.getId());
		
		if (img == null) { // 파일이 업로드 되지 않았을때
			System.out.print("파일 업로드 되지 않았음");
		} else { // 파일이 업로드 되었을때
			result = service.createGath(gath);
		}
		
		response.setIntHeader("result", result);
		
	}

}

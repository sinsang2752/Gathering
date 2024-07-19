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
import com.shinhan.gath.dto.MeetingDTO;
import com.shinhan.gath.service.MeetingService;

/**
 * Servlet implementation class CreateGathServlet
 */
@WebServlet("/createMeet.do")
public class CreateMeetServlet extends HttpServlet {
	private  static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String savePath = "images/meet";
		int uploadFileSizeLimit = 50 * 1024 * 1024;
		String encType = "UTF-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFileSizeLimit
				, encType, new DefaultFileRenamePolicy());
		int result = 0;
		
		MeetingService service = new MeetingService();
		
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		int price = Integer.parseInt(multi.getParameter("price"));
		String place = multi.getParameter("place");
		String expDate = multi.getParameter("expDate").replace("T", " ");
		int maxMem = Integer.parseInt(multi.getParameter("maxMem"));
		String fileName = multi.getFilesystemName("img");
		int gNo = Integer.parseInt(multi.getParameter("gNo"));
		
		HttpSession session = request.getSession();
		AccDTO userInfo = (AccDTO) session.getAttribute("userInfo");
		if(userInfo == null) response.setIntHeader("result", -1);

		MeetingDTO meet = new MeetingDTO();
		meet.setMetTitle(title);
		meet.setMetContent(content);
		meet.setMetPrice(price);
		meet.setMetPlace(place);
		meet.setMetExpDate(String.valueOf(expDate));
		meet.setMetMaxMem(maxMem);
		meet.setGNo(gNo);
		meet.setMetImg(fileName);
		meet.setMgrId(userInfo.getId());

		if (fileName == null) { // 파일이 업로드 되지 않았을때
			System.out.print("파일 업로드 되지 않았음");
		} else { // 파일이 업로드 되었을때
			result = service.insMeet(meet);
		}
		
		response.setIntHeader("result", result);
		
	}

}

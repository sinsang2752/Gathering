package com.shinhan.gath.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.shinhan.gath.service.LoginService;
/**
 * Servlet implementation class SingupServlet
 */
@WebServlet("/signup.do")
public class SingupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 여기를 바꿔주면 다운받는 경로가 바뀜
		String savePath = "images/profile";
		// 최대 업로드 파일 크기 50MB로 제한
		int uploadFileSizeLimit = 50 * 1024 * 1024;
		String encType = "UTF-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFileSizeLimit
				, encType, new DefaultFileRenamePolicy());
		
		LoginService loginService = new LoginService();
		int result = 0;
			// 업로드된 파일의 이름 얻기
		String fileName = multi.getFilesystemName("uploadFile");
			if (fileName == null) { // 파일이 업로드 되지 않았을때
				System.out.print("파일 업로드 되지 않았음");
				result = loginService.signUp(multi);
			} else { // 파일이 업로드 되었을때
				result = loginService.signUp(multi);
			}
			response.setIntHeader("result",result);
		}
}

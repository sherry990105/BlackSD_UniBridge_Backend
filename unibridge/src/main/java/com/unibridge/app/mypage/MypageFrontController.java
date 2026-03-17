package com.unibridge.app.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Result;

public class MypageFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MypageFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String target = extractUserRole(requestURI);
		Result result = new Result();
		
		switch (target) {
		case  "auth/mentor":
		case "/auth/mentor": 
			result = new MentorFrontController().execute(request, response);
			break;
		case  "auth/mentee":
		case "/auth/mentee":
			result = new MenteeFrontController().execute(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청한 기능을 사용할 수 없습니다.");
			return;
		}
		
	    if (result != null) {
	        if (result.isRedirect()) {
	            response.sendRedirect(result.getPath());
	        } else {
	            request.getRequestDispatcher(result.getPath()).forward(request, response);
	        }
	    }
	}
	
	private String extractUserRole(String requestUri) {
		if (requestUri.contains("/auth/mentor")) {
			return "/auth/mentor";
		}
		
		if (requestUri.contains("/auth/mentee")) {
			return "/auth/mentee";
		}
		
		return "";
	}
}

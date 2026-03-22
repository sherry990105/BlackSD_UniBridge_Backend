package com.unibridge.api.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.api.ApiResult;
import com.unibridge.api.learningReport.controller.LearningReportController;
import com.unibridge.api.learningReport.controller.LearningReportModifyController;
import com.unibridge.api.learningReport.controller.LearningReportWriteController;

public class MemberFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}

	/* 시작 URL : /api/admin ... */
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ApiResult<?> responseObject = this.handleProcess(request, response);
		if (responseObject == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청한 기능을 사용할 수 없습니다.");
			return;
		}
		
		response.setContentType(responseObject.getContentType());
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter respWriter = response.getWriter();
		respWriter.write(responseObject.getRawData().toString());
	}
	
	private ApiResult<String> handleProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String target = extractTargetPath(requestURI);
		if (!this.checkValidUrl(requestURI)) {
			return null;
		}
		
		ApiResult<String> apiResult = new ApiResult<String>();
		switch (target) {
		case  "signin/checkDupID.mem":
		case "/signin/checkDupID.mem":
			apiResult = new MemberSigninDupController().execute(request, response);
			break;
		case  "signin/checkDupNickname.mem":
		case "/signin/checkDupNickname.mem":
			apiResult = new MemberSigninDupNNController().execute(request, response);
			break;
		default:
			break;
		}
		
		return apiResult;
	}
	
	private String extractTargetPath(String requetUri) {
		String[] uriPaths = requetUri.split("/api/user");
		String   targetUriPath = uriPaths[uriPaths.length - 1];
		return targetUriPath;
	}
	
	private boolean checkValidUrl(String requestUri) {		
		if (!requestUri.endsWith(".mem")) {
			return false;
		}
		
		return true;
	}
}

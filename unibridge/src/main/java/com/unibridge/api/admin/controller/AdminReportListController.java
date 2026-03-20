package com.unibridge.api.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.unibridge.api.ApiExecute;
import com.unibridge.api.ApiResult;
import com.unibridge.api.admin.dao.AdminReportListDAO;
import com.unibridge.api.admin.dto.AdminReportListDTO;

public class AdminReportListController implements ApiExecute<String> {
	AdminReportListDAO adminReportListDAO = new AdminReportListDAO();
	
	@Override
	public ApiResult<String> execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod().toUpperCase();
		ApiResult<String> apiResult = new ApiResult<String>();
		
		switch (method) {
		case "GET":
			this.doGet(request, response, apiResult);
			break;
		case "POST":
			this.doPost(request, response, apiResult);
			break;
		default:
			break;
		}
		return apiResult;
	}

	private void doGet(HttpServletRequest request, HttpServletResponse response, ApiResult<String> apiResult) {
		String requestURI = request.getRequestURI();
		String target = extractTargetPath(requestURI);
		switch (target) {
		case  "reportList/selectLrByMatchingNumber.admin":
		case "/reportList/selectLrByMatchingNumber.admin":
			Integer matchingNumber = Integer.parseInt(request.getParameter("matchingNumber"));
			apiResult = this.selectLrByMatchingNumber(matchingNumber, apiResult);
			break;
		default:
			break;
		}
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response, ApiResult<String> apiResult) {
	}
	
	private ApiResult<String> selectLrByMatchingNumber(int matchingNumber, ApiResult<String> apiResult) {
		List<AdminReportListDTO> learningReports = this.adminReportListDAO.selectLrByMatchingNumber(matchingNumber);
		String strJson = new Gson().toJson(learningReports);
		apiResult.setRawData(strJson);
		apiResult.setContentType("application/json;charset=UTF-8");
		return apiResult;
	}
	
	private String extractTargetPath(String requetUri) {
		String[] uriPaths = requetUri.split("/api/admin");
		String   targetUriPath = uriPaths[uriPaths.length - 1];
		return targetUriPath;
	}
}

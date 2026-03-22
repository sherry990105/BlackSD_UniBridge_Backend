package com.unibridge.api.learningReport.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unibridge.api.ApiExecute;
import com.unibridge.api.ApiResult;
import com.unibridge.api.learningReport.dao.LearningReportModifyDAO;
import com.unibridge.api.learningReport.dto.LrModifyDTO;

public class LearningReportModifyController implements ApiExecute<String> {
	LearningReportModifyDAO learningReportModifyDTO = new LearningReportModifyDAO();
	
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
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response, ApiResult<String> apiResult) throws IOException {
		try {
		    StringBuilder sb = new StringBuilder();
		    String line;
		    try (BufferedReader reader = request.getReader()) {
		        while ((line = reader.readLine()) != null) {
		            sb.append(line);
		        }
		    }
		    String body = sb.toString();
		    Type type = new TypeToken<Map<String, Object>>() {}.getType();
		    Map<String, Object> bodyJson = new Gson().fromJson(body, type); 
		    
			int reportNumber 		= (int)Float.parseFloat(bodyJson.get("reportNumber").toString());
			int subjectNumber 		= (int)Float.parseFloat(bodyJson.get("subjectNumber").toString());
			String subjectTitle	 	= bodyJson.get("subjectTitle").toString();
			String subjectSummary	= bodyJson.get("subjectSummary").toString();
			String subjectContent	= bodyJson.get("subjectContent").toString();
			
			LrModifyDTO modifyDTO = new LrModifyDTO();
			modifyDTO.setLrReportNumber(reportNumber);
			modifyDTO.setLrSubjectNumber(subjectNumber);
			modifyDTO.setLrSubjectTitle(subjectTitle);
			modifyDTO.setLrSubjectSummary(subjectSummary);
			modifyDTO.setLrSubjectContent(subjectContent);
			int modifyResult = this.learningReportModifyDTO.modifyLearningReport(modifyDTO);
			
			Map<String, String> respMap =  new HashMap<String, String>();
			respMap.put("status", modifyResult == 1 ? "ok" : "error");
			
			apiResult.setRawData(new Gson().toJson(respMap));
			apiResult.setContentType("application/json;charset=UTF-8");
		} catch (Exception e) {
			Map<String, String> respMap =  new HashMap<String, String>();
			respMap.put("status", "error");
			
			apiResult.setRawData(new Gson().toJson(respMap));
			apiResult.setContentType("application/json;charset=UTF-8");
			throw e;
		}
	}
}

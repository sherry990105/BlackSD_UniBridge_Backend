package com.unibridge.api.learningReport.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unibridge.api.ApiExecute;
import com.unibridge.api.ApiResult;
import com.unibridge.api.learningReport.dao.LearningReportDAO;
import com.unibridge.api.learningReport.dao.LearningReportWriteDAO;
import com.unibridge.api.learningReport.dto.LrDetailDTO;
import com.unibridge.api.learningReport.dto.LrWriteDTO;

public class LearningReportWriteController implements ApiExecute<String> {
	LearningReportDAO learningReportDAO = new LearningReportDAO();
	LearningReportWriteDAO learningReportWriteDAO = new LearningReportWriteDAO();
	
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
	    		
		int userNumber 			= (int)Float.parseFloat(bodyJson.get("userNumber").toString());
		String userType			= bodyJson.get("userType").toString();
		int subjectNumber 		= (int)Float.parseFloat(bodyJson.get("subjectNumber").toString());
		String subjectTitle	 	= bodyJson.get("subjectTitle").toString();
		String subjectSummary	= bodyJson.get("subjectSummary").toString();
		String subjectContent	= bodyJson.get("subjectContent").toString();
		
		LrDetailDTO detailDTO = this.learningReportDAO.selectLrDetail(userNumber, userType);
		int matchingNumber = detailDTO.getMatchingInfo().getMatchingNumber();
		
		Date matchingStart = detailDTO.getMatchingInfo().getMatchingStart();
		Date currentDate = new Date();
		
		int diffDays = (int)(currentDate.getTime() - matchingStart.getTime()) / (1000 * 60 * 60 * 24);
		int reportWeek = (diffDays / 7) + 1;
		int reportSession = detailDTO.getReports().stream()
				.filter(value -> reportWeek == value.getLrReportWeek())
				.toList()
				.size() + 1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date reportDate = cal.getTime();
		
		LrWriteDTO writeDTO = new LrWriteDTO();
		writeDTO.setMatchingNumber(matchingNumber);
		writeDTO.setLrSubjectNumber(subjectNumber);
		writeDTO.setLrSubjectTitle(subjectTitle);
		writeDTO.setLrSubjectSummary(subjectSummary);
		writeDTO.setLrSubjectContent(subjectContent);
		writeDTO.setLrReportWeek(reportWeek);
		writeDTO.setLrReportSession(reportSession);
		writeDTO.setLrReportDate(reportDate);
		int insertResult = this.learningReportWriteDAO.createLearningReport(writeDTO);
		
		Map<String, String> respMap =  new HashMap<String, String>();
		respMap.put("status", insertResult != -1 ? "ok" : "error");
		
		apiResult.setRawData(new Gson().toJson(respMap));
		apiResult.setContentType("application/json;charset=UTF-8");
	}
}

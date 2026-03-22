package com.unibridge.api.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.unibridge.api.ApiExecute;
import com.unibridge.api.ApiResult;
import com.unibridge.api.member.dao.MemberSigninDupDAO;

public class MemberSigninDupController implements ApiExecute<String> {
	MemberSigninDupDAO memberSigninDupDAO = new MemberSigninDupDAO();
	
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
		Map<String, String> respMap =  new HashMap<String, String>();
		apiResult.setContentType("application/json;charset=UTF-8");
		
		String memberId = request.getParameter("memberId");
		if (memberId == null) {
			respMap.put("status", "error");
			apiResult.setRawData(new Gson().toJson(respMap));
			return;
		}
		
		int result = memberSigninDupDAO.checkDuplicatedID(memberId);
		if (result != 0) {
			respMap.put("status", "duplicated");
			apiResult.setRawData(new Gson().toJson(respMap));
			return;
		}
		
		respMap.put("status", "ok");
		apiResult.setRawData(new Gson().toJson(respMap));
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response, ApiResult<String> apiResult) {
	}
}

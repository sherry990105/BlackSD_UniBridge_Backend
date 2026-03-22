package com.unibridge.app.learningReport.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class LearningReportController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Result outResult = new Result();
		String method = request.getMethod().toUpperCase();
		switch (method) {
		case "GET":
			this.doGet(request, response, outResult);
			break;
		case "POST":
			this.doPost(request, response, outResult);
			break;
		default:
			break;
		}
		return outResult;
	}

	private void doGet(HttpServletRequest request, HttpServletResponse response, Result result) {
		result.setRedirect(false);
		result.setPath("/app/user/learningReport/report.jsp");
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response, Result result) {
	}
}

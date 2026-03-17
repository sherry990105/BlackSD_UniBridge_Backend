package com.unibridge.app.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class SignupController implements Execute {
	MemberDAO memberDAO = new MemberDAO();
	Result outResult = new Result();
	
	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod().toUpperCase();
		switch (method) {
		case "GET":
			this.doGet(request, response);
			break;
		case "POST":
			this.doPost(request, response);
			break;
		default:
			break;
		}
		return outResult;
	}
	
	private void doGet(HttpServletRequest request, HttpServletResponse response) {
		outResult.setRedirect(true);
		outResult.setPath(request.getContextPath() + "/app/user/signup/signup.jsp");
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");
		String userNickname = request.getParameter("userNickname");
		String userPhone = request.getParameter("userPhone");
		String userGender = request.getParameter("userGender");
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(userId);
		memberDTO.setMemberPw(userPw);
		memberDTO.setMemberName(userName);
		memberDTO.setMemberNickname(userNickname);
		memberDTO.setMemberPhone(userPhone);
		memberDTO.setMemberGender(userGender);
		
		int dtoResult = memberDAO.memberSignup(memberDTO);
		
		outResult.setRedirect(true);
		outResult.setPath(request.getContextPath() + "/signin.mem");
	}


}

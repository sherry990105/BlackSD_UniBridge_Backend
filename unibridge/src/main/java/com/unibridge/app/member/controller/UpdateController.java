package com.unibridge.app.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class UpdateController implements Execute {

	private Result outResult = new Result();

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod().toUpperCase();

		switch (method) {
		case "GET":
			doGet(request, response);
			break;
		case "POST":
			doPost(request, response);
			break;
		}

		return outResult;
	}

	private void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		// 1. 세션에서 loginUser 객체를 먼저 꺼냅니다.
		// MemberDTO는 프로젝트 상황에 맞게 캐스팅하세요 (MemberDTO 혹은 UserDTO)
		com.unibridge.app.member.dto.MemberDTO loginUser = (com.unibridge.app.member.dto.MemberDTO) session
				.getAttribute("loginUser");

		String role = null;
		if (loginUser != null) {
			// 2. DTO에 정의된 memberType(또는 role)을 가져옵니다.
			// SignupController 기준으로는 memberType 필드를 사용하고 있으므로 이를 꺼냅니다.
			role = loginUser.getMemberType();
		}

		String path = "/app/user/undetermined/myPage/myPage.jsp"; // 기본값

		// 3. role 값에 따라 경로 분기 (대소문자 구분 없이 비교)
		if (role != null) {
			if ("mentor".equalsIgnoreCase(role)) {
				path = "/app/user/mentor/myPage/myPage.jsp";
			} else if ("mentee".equalsIgnoreCase(role)) {
				path = "/app/user/mentee/myPage/myPage.jsp";
			}
		}

		System.out.println("현재 세션 loginUser 존재 여부: " + (loginUser != null));
		System.out.println("추출된 role: " + role);
		System.out.println("최종 이동 경로: " + path);

		outResult.setPath(path);
		outResult.setRedirect(false);
	}

	// 인증 장치 -> 수정 페이지 넘어가게 별도 설계
	private void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}

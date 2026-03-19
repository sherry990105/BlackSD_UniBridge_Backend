package com.unibridge.app.mentorSearch.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dto.MemberDTO;
import com.unibridge.app.mentorSearch.dao.MentorSearchDAO;
import com.unibridge.app.mentorSearch.dto.MentorSearchDTO;

public class MentorDetailOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Result result = new Result();
		// 1. 세션에서 로그인된 회원 번호 확인
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
		// 2. 로그인 여부 검사
		if (loginUser == null) {
			// 로그인이 안 되어 있다면 로그인 페이지로 강제 이동
			// (contextPath는 프로젝트 기본 경로를 자동으로 잡아줍니다)
			result.setPath(request.getContextPath() + "/signin.mem");
			result.setRedirect(true); // 주소창을 로그인 페이지로 바꾸기 위해 true 설정
			return result; // 이후 로직(DB 조회 등)을 실행하지 않고 바로 종료
		}

		// --- 여기서부터는 로그인된 사용자만 실행됨 ---

		// 3. 파라미터 받기 및 상세 정보 조회
		String memberNumberStr = request.getParameter("memberNumber");
		if (memberNumberStr == null) {
			result.setPath("/mentor/mentorSearchOk.sch"); // 번호 없으면 목록으로
			result.setRedirect(true);
			return result;
		}

		try {
			long memberNumber = Long.parseLong(memberNumberStr);
			MentorSearchDAO dao = new MentorSearchDAO();
			MentorSearchDTO mentor = dao.selectMentorDetail(memberNumber);

			// 4. 데이터 바인딩
			request.setAttribute("mentor", mentor);
			// JSP 경로 확인: /app/user/mentorSearch/mentorDetail.jsp (파일명 맞는지 확인하세요)
			result.setPath("/app/user/mentorSearch/mentorDetail.jsp");
			result.setRedirect(false);

		} catch (NumberFormatException e) {
			result.setPath("/mentor/mentorSearchOk.sch");
			result.setRedirect(true);
		}

		return result;
	}
}

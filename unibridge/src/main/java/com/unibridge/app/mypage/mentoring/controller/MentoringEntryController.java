package com.unibridge.app.mypage.mentoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;

public class MentoringEntryController implements Execute {
	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Result result = new Result();
		MentoringDAO dao = new MentoringDAO();

		// 1. 현재 로그인한 멘토의 회원번호 가져오기
		Object loginUserObj = session.getAttribute("memberNumber");
		long mentorNumber = (loginUserObj == null) ? 21L : Long.parseLong(String.valueOf(loginUserObj));

		// 2. 해당 멘토가 등록한 멘토링이 있는지 확인
		int count = dao.checkAlreadyExists(mentorNumber);

		if (count > 0) {
		    Long mentoringNumber = dao.getMentoringNumberByMentor(mentorNumber);
		    
		    if (mentoringNumber != null) {
		        result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoringView.my?mentoringNumber=" + mentoringNumber);
		    } else {
		        // 혹시 카운트는 있는데 번호를 못 가져올 경우를 대비한 방어 코드
		        result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoringCreate.my");
		    }
		} else {
		    result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoringCreate.my");
		}
		result.setRedirect(true);

		return result;
	}
}
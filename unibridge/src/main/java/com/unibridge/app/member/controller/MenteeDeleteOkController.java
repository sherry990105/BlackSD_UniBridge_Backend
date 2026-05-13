package com.unibridge.app.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;
import com.unibridge.app.mypage.matching.dao.MatchingDAO;
import com.unibridge.app.mypage.matching.dto.matchingDTO;

public class MenteeDeleteOkController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	Result result = new Result();
        HttpSession session = request.getSession();
        
        // 1. 로그인 유저 정보와 입력값 가져오기
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        String inputId = request.getParameter("userId");
        String inputPw = request.getParameter("userPw");
        Boolean isVerified = (Boolean) session.getAttribute("isDeleteVerified");

        // 2. 검증 로직
        boolean isIdMatch = loginUser.getMemberId().equals(inputId);
        boolean isPwMatch = loginUser.getMemberPw().equals(inputPw);

        if (isIdMatch && isPwMatch && isVerified != null && isVerified) {
            // TODO: 실제 DB 삭제(또는 상태변경) 로직 수행
        	
        	// MatchingDAO 객체 생성 (import 필요)
    	    MatchingDAO matchingDAO = new MatchingDAO();
        	
        	// '매칭됨' 상태인 리스트 가져오기
    	    List<matchingDTO> matchingList = matchingDAO.selectMatchingList(loginUser.getMemberNumber());
    	    
    	    // 3. 데이터 유무에 따른 분기 처리
    	    if (matchingList == null || matchingList.isEmpty()) {
    	        System.out.println("매칭된 데이터가 없음 탈퇴 진행");

    	        MemberDAO memberDAO = new MemberDAO();
    	        memberDAO.updateMemberState(loginUser.getMemberNumber());
    	        
    	        session.invalidate(); // 탈퇴 후 세션 무효화
    	        result.setPath(request.getContextPath() + "/index.main");
    	        result.setRedirect(true);	        
    	    } else {
    	        System.out.println("조회된 매칭 수: " + matchingList.size());
    	        for(matchingDTO str : matchingList) {
    	        	System.out.println("매칭 정보 : "+  str );	        	
    	        }
    	        request.setAttribute("loginErrorMsg", "현재 매칭 중인 상태입니다. 매칭 취소 후 탈퇴해 주세요.");
    	        // 다시 탈퇴 페이지로 (Forward)
                result.setPath("/app/user/mentee/myPage/userDelete/userDelete.jsp");
                result.setRedirect(false);
    	    }      	
        } else {
            // 요구사항: "정보가 맞지 않습니다. 다시 확인해주세요."
            if (!isIdMatch || !isPwMatch) {
                request.setAttribute("loginErrorMsg", "정보가 맞지 않습니다. 다시 확인해주세요.");
            }
            if (isVerified == null || !isVerified) {
                request.setAttribute("authErrorMsg", "휴대폰 인증을 완료해주세요.");
            }
            
            // 다시 탈퇴 페이지로 (Forward)
            result.setPath("/app/user/mentee/myPage/userDelete/userDelete.jsp");
            result.setRedirect(false);
        }

        return result;
    }
}
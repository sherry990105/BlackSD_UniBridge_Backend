package com.unibridge.app.mypage.mentoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dto.MemberDTO;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;

public class MentoringEntryController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("-----------------------------------------");
        System.out.println("[Log] MentoringEntryController 진입 (기본값 제거 버전)");
        
        HttpSession session = request.getSession();
        Result result = new Result();
        MentoringDAO dao = new MentoringDAO();
        
        // 1. 세션에서 로그인 유저 번호 추출 (기본값 21L 로직 완전 제거)
        Object loginUserObj = session.getAttribute("loginUser");
        MemberDTO sessionMemberObj = (MemberDTO) loginUserObj;
        System.out.println("[Log] 세션에서 꺼낸 memberNumber 객체: " + sessionMemberObj.getMemberNumber());
        
        Long mentorNumber = null;
        
        if (sessionMemberObj != null) {
            try {
                mentorNumber = Long.valueOf(sessionMemberObj.getMemberNumber());
            } catch (Exception e) {
                System.out.println("[Error] 세션 회원번호 변환 실패.");
            }
        }

        // 2. 판정 로직: 로그인 정보가 없으면 바로 생성(create) 페이지로 이동
        if (mentorNumber == null) {
            System.out.println("[Warn] 로그인 세션이 없습니다. 생성 페이지로 이동합니다.");
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=create");
            result.setRedirect(true);
            System.out.println("-----------------------------------------");
            return result;
        }

        System.out.println("[Log] 조회 대상 mentorNumber: " + mentorNumber);

        // 3. DB에 기존 등록된 데이터가 있는지 확인
        int count = dao.checkAlreadyExists(mentorNumber);
        System.out.println("[Log] 기존 데이터 존재 여부(count): " + count);

        if (count > 0) {
            // 데이터가 확실히 존재하는 경우에만 상세보기(view)로 이동
            Long mentoringNumber = dao.getMentoringNumberByMentor(mentorNumber);
            System.out.println("[Log] 조회된 멘토링 고유 번호(PK): " + mentoringNumber);
            
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=view&mentoringNumber=" + mentoringNumber);
        } else {
            // 작성한 데이터가 없다면 생성(create) 페이지로 이동
            System.out.println("[Log] 작성 내역 없음 -> 생성 페이지로 이동합니다.");
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=create");
        }
        
        result.setRedirect(true);
        System.out.println("[Log] 이동할 최종 주소: " + result.getPath());
        System.out.println("-----------------------------------------");
        return result;
    }
}
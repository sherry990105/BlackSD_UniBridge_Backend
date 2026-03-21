package com.unibridge.app.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;

public class CheckNickOkController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 파라미터 및 세션 정보 가져오기
        String nickname = request.getParameter("nickname");
        HttpSession session = request.getSession();
        
        // 세션에 저장된 본인의 회원번호 (로그인 시 세션에 저장한 키값을 사용하세요)
        Integer memberNumber = (Integer) session.getAttribute("memberNumber");
        
        response.setContentType("text/plain; charset=utf-8");

        // 예외 처리
        if (nickname == null || memberNumber == null) {
            response.getWriter().write("error");
            return null;
        }

        // 2. DAO를 통한 중복 체크 실행
        MemberDAO dao = new MemberDAO();
        int count = dao.checkNickname(nickname, memberNumber);

        // 3. 결과 응답 (0이면 중복 없음, 그 외는 중복)
        if (count == 0) {
            response.getWriter().write("available");
        } else {
            response.getWriter().write("duplicate");
        }

        return null; // AJAX 요청이므로 페이지 이동 없음
    }
}
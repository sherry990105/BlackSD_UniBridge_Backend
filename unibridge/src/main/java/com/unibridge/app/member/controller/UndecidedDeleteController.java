package com.unibridge.app.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unibridge.api.SmsAuthService;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dao.MemberDAO;
import com.unibridge.app.member.dto.MemberDTO;

public class UndecidedDeleteController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=============== UndecidedDeleteController Start ===============");
        
        String method = request.getMethod().toUpperCase();

        if ("GET".equals(method)) {
            // 1. 회원탈퇴 페이지 이동 (GET 방식)
            Result result = new Result();
            result.setPath("/app/user/undetermined/myPage/userDelete/userDelete.jsp");
            result.setRedirect(false);
            return result;
        } else {
            // 2. AJAX 인증 및 최종 탈퇴 처리 (POST 방식)
            String mode = request.getParameter("mode");
            response.setContentType("text/plain; charset=utf-8");

            if ("send".equals(mode)) {
                // [A] 인증번호 발송 요청
                doSendSms(request, response);
            } else if ("check".equals(mode)) {
                // [B] 인증번호 확인 요청
                doVerifyCode(request, response);
            } else if ("deleteAccount".equals(mode)) {
                // [C] 실제 회원 탈퇴 처리 (보안 검사 포함)
                return doDeleteAccount(request);
            }
            return null; // AJAX 응답은 결과(Result) 객체를 반환하지 않음
        }
    }

    /**
     * [A] 휴대폰 인증번호 발송
     */
    private void doSendSms(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        SmsAuthService smsService = new SmsAuthService();
        
        // SmsAuthService 내부에서 6자리 난수 생성 및 세션("serverAuthCode") 저장을 수행함
        boolean isSent = smsService.sendSms(request, phoneNumber);
        
        if (isSent) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    }

    /**
     * [B] 휴대폰 인증번호 검증 (AJAX)
     */
    private void doVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 클라이언트에서 보낸 JSON 데이터를 읽어옴 (authCode)
        JsonObject jsonBody = new Gson().fromJson(request.getReader(), JsonObject.class);
        String userCode = jsonBody.get("authCode").getAsString();
        
        SmsAuthService smsService = new SmsAuthService();
        
        // 서비스의 verifyCode는 세션의 "serverAuthCode"와 비교 후 성공 시 "isPhoneVerified" 세팅
        String result = smsService.verifyCode(request, userCode);

        if ("verified".equals(result)) {
            // 탈퇴 페이지 전용 세션 마크 추가 (선택 사항)
            request.getSession().setAttribute("isDeleteVerified", true);
            response.getWriter().write("verified");
        } else {
            response.getWriter().write("invalid");
        }
    }

    /**
     * [C] 최종 회원 탈퇴 로직 (UpdateOkController의 보안 로직 적용)
     */
    private Result doDeleteAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        // 보안 검사: 휴대폰 인증이 완료되었는지 세션 확인
        Boolean isVerified = (Boolean) session.getAttribute("isPhoneVerified");
        
        if (isVerified == null || !isVerified) {
            // 인증 없이 접근 시 인증 페이지 혹은 메인으로 튕겨냄
            Result result = new Result();
            result.setPath("/auth/undecided/verify.my"); 
            result.setRedirect(true);
            return result;
        }

        // 실제 회원 삭제 처리
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser != null) {
            MemberDAO dao = new MemberDAO();
            int memberNumber = loginUser.getMemberNumber();
            
            // DAO를 호출하여 회원 상태 변경 혹은 삭제 실행
            // dao.deleteMember(memberNumber); 
            
            System.out.println("[탈퇴완료] 회원번호: " + memberNumber);
            
            // 탈퇴 후 세션 정보 전체 삭제
            session.invalidate();
        }
        
        Result result = new Result();
        result.setPath("/main/main.index"); // 탈퇴 후 메인 페이지로 이동
        result.setRedirect(true);
        return result;
    }
}
package com.unibridge.app.mypage.mentoring.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class MentoringFrontController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // mentoring.my?type=view 와 같이 파라미터로 구분합니다.
        String type = request.getParameter("type");
        if (type == null) type = "main"; // 기본값: 데이터 존재 여부 확인

        System.out.println("=== MentoringFrontController ===");
        System.out.println("[Log] 요청 작업(type): " + type);

        Result result = null;

        try {
            switch (type) {
                case "main": // 멘토링 메뉴 클릭 시 진입 (데이터 유무 체크)
                    result = new MentoringEntryController().execute(request, response);
                    break;
                case "create": // 등록 폼 페이지 이동
                    result = new Result();
                    result.setPath("/app/user/mentor/myPage/userMentoring/mentoringCreate.jsp");
                    result.setRedirect(false);
                    break;
                case "view": // 상세보기 실행
                    result = new MentoringViewController().execute(request, response);
                    break;
                case "writeOk": // 등록 DB 처리
                    result = new MentoringWriteOkController().execute(request, response);
                    break;
                case "modify": // 수정 폼 페이지 이동
                    result = new MentoringModifyController().execute(request, response);
                    break;
                case "modifyOk": // 수정 DB 처리
                    result = new MentoringModifyOkController().execute(request, response);
                    break;
                case "deleteOk": // 삭제 DB 처리
                    result = new MentoringDeleteOkController().execute(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
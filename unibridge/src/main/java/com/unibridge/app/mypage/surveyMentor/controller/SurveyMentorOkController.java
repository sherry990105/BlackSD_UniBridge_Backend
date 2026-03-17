package com.unibridge.app.mypage.surveyMentor.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.file.dto.FileDTO;
import com.unibridge.app.mypage.survey.dao.SurveyDAO;
import com.unibridge.app.mypage.surveyMentor.dto.SurveyMentorDTO;

public class SurveyMentorOkController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	// POST 방식이 아닐 경우(주소창 직접 입력 등) 차단
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            Result result = new Result();
            result.setPath(request.getContextPath() + "/app/user/mentor/myPage/userSurvey/userSurvey.jsp");
            result.setRedirect(true);
            return result;
        }
    	
        Result result = new Result();
        SurveyDAO surveyDAO = new SurveyDAO();
        SurveyMentorDTO mentorDTO = new SurveyMentorDTO();
        FileDTO fileDTO = null;

        // 1. 파일 저장 경로 설정 및 폴더 체크
        final String UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/";
        File uploadDir = new File(UPLOAD_PATH);

        // 폴더가 없으면 생성 (이 로직이 Not a directory 에러를 방지합니다)
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println("[LOG] 업로드 폴더를 생성했습니다: " + UPLOAD_PATH);
        }
        
        // 2. MultipartRequest 생성
        //=======================
        String contentType = request.getContentType();

        if (contentType == null || !contentType.toLowerCase().startsWith("multipart/")) {
            throw new RuntimeException("multipart/form-data 요청이 아닙니다.");
        }
        //===========================================
        
        final int FILE_SIZE = 500 * 1024 * 1024; // 500MB 
        MultipartRequest multi = new MultipartRequest(request, UPLOAD_PATH, FILE_SIZE, "UTF-8", new DefaultFileRenamePolicy());

        // 3. 세션에서 유저 식별 정보 가져오기
        //==========================================
        HttpSession session = request.getSession();

        Object memberObj = session.getAttribute("memberNumber");

        int memberNumber;

        if (memberObj == null) {
            // 테스트용
            memberNumber = 21;
            session.setAttribute("memberNumber", memberNumber);
            System.out.println("[TEST] 임시 로그인 memberNumber=21");
        } else {
            memberNumber = (int) memberObj;
        }
        //===================================================
        // 로그인 시 세션에 담았던 "memberNumber"를 가져옵니다.
//        int memberNumber = (int)session.getAttribute("memberNumber");

        // 4. 파일 데이터 수집 (선택 사항)
        String filesystemName = multi.getFilesystemName("surveyFile");
        if (filesystemName != null) {
            fileDTO = new FileDTO();
            fileDTO.setFileName(filesystemName);
            fileDTO.setFileOriginalName(multi.getOriginalFileName("surveyFile"));
            fileDTO.setFilePath("/upload/");
            // 추가 정보가 필요하다면 여기서 세팅 (확장자, 크기 등)
        }

        // 5. 멘토 설문 데이터 수집 (SurveyDTO 상속 필드 포함)
        mentorDTO.setMemberNumber(memberNumber); // 부모 클래스 필드
        mentorDTO.setSurveyType("MENTOR");       // 부모 클래스 필드   
        
        // 멘토 상세 정보 (SurveyMentorDTO 전용 필드)
        mentorDTO.setGradSchool(multi.getParameter("gradSchool"));
        mentorDTO.setGradDepart(multi.getParameter("gradDepart"));
        
        // 학점(grad_score)은 DB에서 NUMBER 타입이므로 파싱 필요
        String gradScoreStr = multi.getParameter("gradScore");
        if(gradScoreStr != null && !gradScoreStr.isEmpty()) {
        	mentorDTO.setGradScore(Double.parseDouble(gradScoreStr));
        }
        
        // 과목 번호 (subject_number)
        String subjectNumStr = multi.getParameter("subjectNumber");
        if(subjectNumStr != null && !subjectNumStr.isEmpty()) {
        	mentorDTO.setSubjectNumber(Integer.parseInt(subjectNumStr));
        }
        
        //모든 필드 세팅 후 로그 출력
        System.out.println("=====[LOG] 세션 멤버 번호: " + memberNumber + "=====");
        System.out.println("=====[LOG] 수집 완료 DTO: " + mentorDTO.toString() + "=====");

        // 6. DAO 호출 (DB 저장 - 트랜잭션 처리됨)
        surveyDAO.insertMentorSurvey(mentorDTO, fileDTO);

        // 7. 결과 반환 (Redirect로 마이페이지 메인 이동) - 임지(추후 마이페이지 계정관리 이동예정)
        result.setPath(request.getContextPath() + "/mypage/main.my");
        result.setRedirect(true); 

        return result;
    }
}
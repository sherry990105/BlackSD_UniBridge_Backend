package com.unibridge.app.mypage.surveyMentee.controller;

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
import com.unibridge.app.mypage.surveyMentee.dto.SurveyMenteeDTO;

public class SurveyMenteeOkController implements Execute {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	// POST 방식이 아닐 경우(주소창 직접 입력 등) 차단
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            Result result = new Result();
            result.setPath(request.getContextPath() + "/app/user/mentee/myPage/userSurvey/userSurvey.jsp");
            result.setRedirect(true);
            return result;
        }
    	
        Result result = new Result();
        SurveyDAO surveyDAO = new SurveyDAO();
        SurveyMenteeDTO menteeDTO = new SurveyMenteeDTO();
        FileDTO fileDTO = null;

        // 1. 파일 저장 경로 설정 및 폴더 체크
        final String UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/";
        File uploadDir = new File(UPLOAD_PATH);

        // 폴더가 없으면 생성 (이 로직이 Not a directory 에러를 방지합니다)
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println("[LOG] 업로드 폴더를 생성했습니다: " + UPLOAD_PATH);
        }
                
        // 2. MultipartRequest 생성 (파일 업로드 실행)
        final int FILE_SIZE = 500 * 1024 * 1024; // 500MB 
        MultipartRequest multi = new MultipartRequest(request, UPLOAD_PATH, FILE_SIZE, "UTF-8", new DefaultFileRenamePolicy());

        // 3. 세션에서 유저 정보 가져오기
        HttpSession session = request.getSession();
        int memberNumber = (int)session.getAttribute("memberNumber");

        // 4. 파일 데이터 수집 (선택 사항)
        String filesystemName = multi.getFilesystemName("surveyFile");
        if (filesystemName != null) {
            fileDTO = new FileDTO();
            fileDTO.setFileName(filesystemName);
            fileDTO.setFileOriginalName(multi.getOriginalFileName("surveyFile"));
            fileDTO.setFilePath("/upload/");
            // 필요 시 fileSize, fileExtension 추가 세팅
        }

        // 5. 멘티 설문 데이터 수집
        menteeDTO.setMemberNumber(memberNumber);
        menteeDTO.setSurveyType("MENTEE");
        menteeDTO.setMenteeSchool(multi.getParameter("menteeSchool"));
        menteeDTO.setMenteeGrade(Integer.parseInt(multi.getParameter("menteeGrade")));
        menteeDTO.setMenteeHopeuni(multi.getParameter("menteeHopeuni"));
        menteeDTO.setMenteeHopemajor(multi.getParameter("menteeHopemajor"));
        // subjectNumber가 null일 경우를 대비해 예외처리나 기본값 고려 가능
        String subjectNumStr = multi.getParameter("subjectNumber");
        if(subjectNumStr != null) {
            try {
				menteeDTO.setSubjectNumber(Integer.parseInt(subjectNumStr));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("입력이 잘못되었습니다.");
				e.printStackTrace();
			}
        }
        
        //모든 필드 세팅 후 로그 출력
        System.out.println("=====[LOG] 세션 멤버 번호: " + memberNumber + "=====");
        System.out.println("=====[LOG] 수집 완료 DTO: " + menteeDTO.toString() + "=====");

        // 6. DAO 호출 (DB 저장)
        surveyDAO.insertMenteeSurvey(menteeDTO, fileDTO);

        // 7. 결과 반환 (Redirect로 마이페이지 메인 이동) - 임지(추후 마이페이지 계정관리 이동예정)
        result.setPath(request.getContextPath() + "/mypage/main.my");
        result.setRedirect(true); 

        return result;
    }
}

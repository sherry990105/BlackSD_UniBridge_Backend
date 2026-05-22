package com.unibridge.app.mypage.mentoring.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.member.dto.MemberDTO;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;
import com.unibridge.app.mypage.mentoring.dto.MentoringDTO;

public class MentoringWriteOkController implements Execute {
	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("[Log] MentoringWriteOkController 시작");

		MentoringDAO mentoringDAO = new MentoringDAO();
		MentoringDTO mentoringDTO = new MentoringDTO();
		Result result = new Result();

		HttpSession session = request.getSession();
		Object loginUserObj = session.getAttribute("loginUser");
		MemberDTO loginUser = (MemberDTO) loginUserObj;
		long mentorNumber =  loginUser.getMemberNumber();

		String uploadPath = request.getServletContext().getRealPath("/") + "upload/mentoring/";
		int fileSizeLimit = 1024 * 1024 * 10; // 10MB

		System.out.println("====== [검증 로그: 등록 시도 유저] ======");
		System.out.println("[Log] 세션에서 나온 객체: " + loginUserObj);
		System.out.println("[Log] 최종 결정된 mentorNumber (멘토ID): " + mentorNumber);
		System.out.println("=======================================");
        
		try {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSizeLimit, "UTF-8",
					new DefaultFileRenamePolicy());

			// 3. 파일 정보 추출 및 DTO 세팅
			Enumeration<String> files = multi.getFileNames();

			if (files.hasMoreElements()) {
			    String name = files.nextElement();
			    File file = multi.getFile(name);
			    
			    if (file != null) {
			        String originalFileName = multi.getOriginalFileName(name); 
			        long size = file.length();
			        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			        
			        mentoringDTO.setFileOriginalName(originalFileName); 
			        mentoringDTO.setFileExtension(extension); 
			        mentoringDTO.setFileSize(size); 
			        
			        System.out.println("[Log] 파일 정보 추출 완료: " + originalFileName);
			    }
			}

			// 4. 일반 데이터 세팅
			mentoringDTO.setMentorNumber(mentorNumber); 
			mentoringDTO.setSubjectNumber(Integer.parseInt(multi.getParameter("mentoringSubject"))); 
			mentoringDTO.setMentoringTitle(multi.getParameter("mentoringTitle")); 
			mentoringDTO.setMentoringGoal(multi.getParameter("mentoringPurpose")); 
			mentoringDTO.setMentoringDetail(multi.getParameter("mentoringCurriculum")); 

			// 5. 중복 체크
			int existingCount = mentoringDAO.checkAlreadyExists(mentorNumber); 
			System.out.println("[Log] 중복 등록 체크 결과 (기존 등록 수): " + existingCount);
            
			if (existingCount > 0) {
				System.out.println("[Warn] 이미 멘토링이 존재하므로 등록을 차단합니다. 멘토ID: " + mentorNumber);
				result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my?error=already_exists");
				result.setRedirect(true);
				return result;
			}

			// 6. DB Insert 실행
			System.out.println("[Log] DB Insert 실행 직전 DTO 상태: " + mentoringDTO.toString());
			mentoringDAO.insert(mentoringDTO); 
			System.out.println("[Log] DB Insert 실행 완료 성공!");

			// 7. 등록된 멘토링 고유 번호 다시 조회
			System.out.println("[Log] 멘토ID(" + mentorNumber + ")로 방금 등록된 멘토링 PK 조회 시도...");
			long createdNumber = mentoringDAO.getMentoringNumberByMentor(mentorNumber);
			System.out.println("[Log] DB에서 최종 조회된 멘토링 PK 번호: " + createdNumber);

			// 성공 시 상세보기 페이지로 이동
			result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=view&mentoringNumber=" + createdNumber);
			result.setRedirect(true);
			System.out.println("[Log] 이동할 최종 Path: " + result.getPath());
			return result;

		} catch (Exception e) {
			System.out.println("[Critical Error] MentoringWriteOkController에서 예외가 터졌습니다!");
			e.printStackTrace(); // 콘솔에 정확한 에러 원인(SQL 에러 등)을 출력합니다.
			
			result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my?error=exception");
			result.setRedirect(true);
			return result;
		}
	}
}
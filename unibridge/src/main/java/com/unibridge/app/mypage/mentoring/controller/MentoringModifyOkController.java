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
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;
import com.unibridge.app.mypage.mentoring.dto.MentoringDTO;

public class MentoringModifyOkController implements Execute {
	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MentoringDAO dao = new MentoringDAO();
		MentoringDTO dto = new MentoringDTO();
		Result result = new Result();

		//저장 경로 및 설정 (mentoring 폴더 사용)
		String uploadPath = request.getServletContext().getRealPath("/") + "upload/mentoring/";
		int fileSize = 1024 * 1024 * 10; // 10MB

		try {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// MultipartRequest 생성
			MultipartRequest multi = new MultipartRequest(request, uploadPath, fileSize, "UTF-8",
					new DefaultFileRenamePolicy());

			//기본 파라미터 수집
			long mentoringNumber = Long.parseLong(multi.getParameter("mentoringNumber"));
			HttpSession session = request.getSession();
			long mentorNumber = Long.parseLong(String.valueOf(
					session.getAttribute("memberNumber") != null ? session.getAttribute("memberNumber") : 21L));

			// 파일 수정 로직
			Enumeration<String> files = multi.getFileNames();
			String finalFileName = null;

			if (files.hasMoreElements()) {
				String name = files.nextElement();
				String systemName = multi.getFilesystemName(name);
				String originalName = multi.getOriginalFileName(name);

				if (systemName != null) {
				    finalFileName = System.currentTimeMillis() + "_" + mentorNumber + "_" + originalName;
				    File oldFile = new File(uploadPath + systemName);
				    File newFile = new File(uploadPath + finalFileName);
				    
				    boolean isRenamed = oldFile.renameTo(newFile);
				    System.out.println("파일 변경 성공 여부: " + isRenamed); // 이게 false면 파일 저장 안 됨
				    
				    if(isRenamed) {
				        dto.setFileOriginalName(finalFileName);
				    } else {
				        // 실패했다면 원본 이름이라도 넣어줌 (임시 방편)
				        dto.setFileOriginalName(systemName);
				    }
				}
			}

			// 5. DTO 데이터 세팅
			dto.setMentoringNumber(mentoringNumber);
			dto.setMentoringTitle(multi.getParameter("mentoringTitle"));
			dto.setMentoringGoal(multi.getParameter("mentoringPurpose"));
			dto.setMentoringDetail(multi.getParameter("mentoringCurriculum"));
			
			String subjectParam = multi.getParameter("mentoringSubject");
			int subjectNumber = 0; // 기본값

			if (subjectParam != null && !subjectParam.isEmpty() && !subjectParam.equals("none")) {
			    try {
			        subjectNumber = Integer.parseInt(subjectParam);
			    } catch (NumberFormatException e) {
			        // 숫자가 아닐 경우 기본값 유지
			        subjectNumber = 0; 
			    }
			}
			dto.setSubjectNumber(subjectNumber);

			// 새 파일이 업로드된 경우에만 파일명 세팅
			if (finalFileName != null) {
				dto.setFileOriginalName(finalFileName);
			}

			// 6. DB 업데이트 실행
			dao.update(dto);

			result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=view&mentoringNumber="
					+ mentoringNumber);
			result.setRedirect(true);

		} catch (Exception e) {
			e.printStackTrace();
			result.setPath(request.getContextPath() + "/mvc/auth/mentor/myPage.my");
			result.setRedirect(true);
		}
		return result;
	}
}
package com.unibridge.app.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdNoticeBoardDAO;
import com.unibridge.app.admin.dto.AdNoticeBoardDTO;
import com.unibridge.app.file.dao.FileDAO;
import com.unibridge.app.file.dto.FileDTO;

public class AdminNoticeEditOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdNoticeBoardDAO boardDAO = new AdNoticeBoardDAO();
		AdNoticeBoardDTO boardDTO = new AdNoticeBoardDTO();
		FileDAO fileDAO = new FileDAO();
		Result result = new Result();

		final String UPLOAD_PATH = "C:/upload/DATA/";
		final int FILE_SIZE = 1024 * 1024 * 5;
		// 1024 byte = 1KB
		// 1024 KB = 1MB * 5 = 5MB

		
		// MultipartParser 실행
		MultipartParser parser = new MultipartParser(request, FILE_SIZE);
		parser.setEncoding("utf-8");
		System.out.println("MultipartParser 초기화 완료");

		int boardNumber = 0;
		Integer fileNumber = null;
		boolean isFileUpload = false;

		// 파일, 텍스트 데이터 처리
		Part part; // multipart 요청안에 들어있는 각 조각을 의미(boardNumber, boardTitle, boardContent, file)
		while ((part = parser.readNextPart()) != null) {
			System.out.println("Part : " + part.getClass().getSimpleName());

			if (part.isParam()) { // 현재 part가 일반 텍스트 데이터 인지 확인
				// 텍스트 파라미터 처리
				ParamPart paramPart = (ParamPart) part; // part는 부모타입 Part로 선언, 텍스트전용메소드는 ParamPart에 있기 때문에 다운캐스팅
				String paramName = paramPart.getName(); // input의 name 속성값
				String paramValue = paramPart.getStringValue(); // 사용자가 실제로 입력한 값

				System.out.println("파라미터 : " + paramName + " = " + paramValue);
				
				// 1. 기존 게시글의 파일 번호를 먼저 가져옴

				if ("boardNumber".equals(paramName)) {
					boardNumber = Integer.parseInt(paramValue);
					boardDTO.setNoticeboardNumber(boardNumber); // dto에 변경될 게시글 번호 저장
					// 1. 기존 게시글의 파일 번호를 먼저 가져옴
					fileNumber = boardDAO.fileNumberSelect(boardNumber);
					
				} else if ("boardTitle".equals(paramName)) {
					boardDTO.setBoardTitle(paramValue); // dto에 변경되는 제목 저장
					
				} else if ("boardContent".equals(paramName)) {
					boardDTO.setBoardContent(paramValue); // dto에 변경되는 내용 저장
				}
				
			} else if (part.isFile() && !isFileUpload) { // 파일 처리 조건(파일 파트이고, 아직 파일 처리를 하지 않은 경우)
				// 파일명 중복 방지를 위한 시스템 파일명 생성
				FilePart filePart = (FilePart) part; 
				filePart.setRenamePolicy(new DefaultFileRenamePolicy()); 
				String originalFileName = filePart.getFileName(); 
				
				System.out.println("새로운 파일 명 : " + originalFileName);
				
				
				if(originalFileName != null) {
					
					System.out.println("새로운 파일 삽입");
					
					String systemName = System.currentTimeMillis() + "_" + originalFileName;
					File file = new File(UPLOAD_PATH, systemName);
					
					System.out.println("파일 상태 : " + file);
					
					filePart.writeTo(file);
					
					FileDTO fileDTO = new FileDTO();
					fileDTO.setFileName(systemName);
					fileDTO.setFileOriginalName(originalFileName);
					fileDTO.setFilePath(UPLOAD_PATH);
					fileDTO.setFileSize(file.length());
					fileDTO.setFileExtension(originalFileName.substring(originalFileName.lastIndexOf(".")));
					
					
					if (fileNumber != null && fileNumber > 0) {
						System.out.println("기존 파일이 있는 경우");
						fileDTO.setFileNumber(fileNumber);
						fileDAO.update(fileDTO); 
						System.out.println("기존 파일 정보 수정 완료: " + fileNumber);
					} else {
						System.out.println("기존 파일이 없는 경우");
						fileNumber = fileDAO.insertFileIfExists(fileDTO);
						boardDTO.setFileNumber(fileNumber); // 새 파일 번호를 게시글에 연결
						System.out.println("새로운 파일 등록 완료: " + fileNumber);
					}
					
				}
				
				
		// 게시글 업데이트 실행
		boardDTO.setAdminNumber((Integer) request.getSession().getAttribute("adminNumber"));
		//게시글에 파일이 원래 없었을 경우
		if(boardDTO.getFileNumber() == null) {
			boardDTO.setFileNumber(fileNumber);
		}
		
		boardDAO.updateBoard(boardDTO);
		System.out.println("게시글 수정 완료");

		// 수정 완료 후 페이지 이동
		result.setPath(request.getContextPath()+"/noticeList.admin");
		result.setRedirect(true);


	}

		}
		return result;
	}
}

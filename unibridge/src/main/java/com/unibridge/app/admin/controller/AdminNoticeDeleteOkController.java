package com.unibridge.app.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.admin.dao.AdNoticeBoardDAO;
import com.unibridge.app.file.dao.FileDAO;

public class AdminNoticeDeleteOkController implements Execute {

	@Override
	public Result execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("게시글 삭제 서블릿 접근 완료");
		AdNoticeBoardDAO boardDAO = new AdNoticeBoardDAO();
		Result result = new Result();
		FileDAO fileDAO = new FileDAO();


		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		System.out.println(boardNumber);
		
		boardDAO.deleteBoard(boardNumber);

		if(boardDAO.fileNumberSelect(boardNumber) != null) {
			fileDAO.delete(boardDAO.fileNumberSelect(boardNumber));
		}
		
		System.out.println("게시글 삭제 완료");
		
		String path = request.getContextPath() + "/app/admin/adminNotice/noticeList.admin";
		result.setPath(path);
		result.setRedirect(true);
		
		return result;
	}

}

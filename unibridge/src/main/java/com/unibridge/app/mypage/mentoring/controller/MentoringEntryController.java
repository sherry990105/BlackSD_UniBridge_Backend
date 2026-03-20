package com.unibridge.app.mypage.mentoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.unibridge.app.Execute;
import com.unibridge.app.Result;
import com.unibridge.app.mypage.mentoring.dao.MentoringDAO;

public class MentoringEntryController implements Execute {
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Result result = new Result();
        MentoringDAO dao = new MentoringDAO();

        Long mentorNumber = (Long) session.getAttribute("memberNumber");
        if (mentorNumber == null) mentorNumber = 21L; 

        int count = dao.checkAlreadyExists(mentorNumber);

        if (count > 0) {
            Long mentoringNumber = dao.getMentoringNumberByMentor(mentorNumber);
            // 데이터가 있으면 상세보기로 보냄 (type=view)
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=view&mentoringNumber=" + mentoringNumber);
        } else {
            // 데이터가 없으면 등록으로 보냄 (type=create)
            result.setPath(request.getContextPath() + "/mvc/auth/mentor/mentoring.my?type=create");
        }
        
        result.setRedirect(true);
        return result;
    }
}
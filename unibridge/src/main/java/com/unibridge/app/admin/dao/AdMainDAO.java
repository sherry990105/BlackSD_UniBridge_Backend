package com.unibridge.app.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.app.admin.dto.AdMainDTO;
import com.unibridge.config.MyBatisConfig;

public class AdMainDAO {
	   public SqlSession sqlSession;

	   public AdMainDAO() {
	      sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	   }
	   
	// 메인페이지
	   //유저 총합
	   public int memberTotal() {
	      int memberTotal = sqlSession.selectOne("admin.membertotal");
	      return memberTotal;
	   }
	   
	   //멘토 총합
	   public int mentorTotal() {
	      int mentorTotal = sqlSession.selectOne("admin.mentortotal");
	      return mentorTotal;
	   }
	   
	   //멘티 총합
	   public int menteeTotal() {
	      int menteeTotal = sqlSession.selectOne("admin.menteetotal");
	      return menteeTotal;
	   }

	   //멘티게시판 총합
	   public int menteeBoardTotal() {
		   return sqlSession.selectOne("admin.menteeboardtotal");
		   
	   }
	   //멘토게시판 총합
	   public int mentorBoardTotal() {
		   return sqlSession.selectOne("admin.mentorboardtotal");
		   
	   }
	   
	   //공지게시판 총합
	   public int noticeTotal() {
		   return sqlSession.selectOne("admin.noticetotal");
		   
	   }
	   
	   //게시판 총합
	   public int boardTotal() {
		   return menteeBoardTotal() +  mentorBoardTotal() + noticeTotal();
	   }
	   
	   //매칭 총합
	   public int matchingTotal() {
		   int totalMatching = sqlSession.selectOne("admin.matchingtotal");
		   return totalMatching;
	   }
	   
	   
	   //최신 게시판
	   public List<AdMainDTO> recentboard() {
	      List<AdMainDTO> boardList = sqlSession.selectList("admin.recentboard");
	      return boardList;
	   }
	   
	   //최신 유저
	   public List<AdMainDTO> recentmember() {
	      List<AdMainDTO> memberList = sqlSession.selectList("admin.recentMember");
	      return memberList;
	   }
	   
}

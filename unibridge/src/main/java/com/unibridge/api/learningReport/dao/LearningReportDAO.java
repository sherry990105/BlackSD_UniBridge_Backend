package com.unibridge.api.learningReport.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.unibridge.api.learningReport.dto.LrDTO;
import com.unibridge.api.learningReport.dto.LrDetailDTO;
import com.unibridge.api.learningReport.dto.LrMatchingInfoDTO;
import com.unibridge.api.learningReport.dto.LrSubjectDTO;
import com.unibridge.config.MyBatisConfig;

public class LearningReportDAO {
	public SqlSession sqlSession;

	public LearningReportDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public LrDetailDTO selectLrDetail(int mentorNumber) {
		LrDetailDTO lrDetailDTO = new LrDetailDTO();
		LrMatchingInfoDTO matchingDetailDTO = this.sqlSession.selectOne("api.learningReport.selectMatching", mentorNumber);
		if (matchingDetailDTO == null) {
			return lrDetailDTO;
		}
		
		lrDetailDTO.setReports(this.sqlSession.selectList("api.learningReport.selectAll", matchingDetailDTO.getMatchingNumber()));
		lrDetailDTO.setMatchingInfo(matchingDetailDTO);
		return lrDetailDTO;
	}
	
	public LrMatchingInfoDTO selectMathcingInfo(int mentorNumber) {
		return this.sqlSession.selectOne("api.learningReport.selectMatching", mentorNumber);
	}
	
	public List<LrSubjectDTO> selectLrAllSubjects() {
		return this.sqlSession.selectList("api.learningReport.selectSubjects");
	}
}

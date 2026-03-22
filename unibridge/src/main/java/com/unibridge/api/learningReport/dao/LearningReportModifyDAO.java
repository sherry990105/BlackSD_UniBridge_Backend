package com.unibridge.api.learningReport.dao;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.api.learningReport.dto.LrModifyDTO;
import com.unibridge.config.MyBatisConfig;

public class LearningReportModifyDAO {
	public SqlSession sqlSession;

	public LearningReportModifyDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public int modifyLearningReport(LrModifyDTO modifyDTO) {
		return this.sqlSession.insert("api.learningReportModify.update", modifyDTO);
	}
}

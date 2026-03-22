package com.unibridge.api.learningReport.dao;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.api.learningReport.dto.LrWriteDTO;
import com.unibridge.config.MyBatisConfig;

public class LearningReportWriteDAO {
	public SqlSession sqlSession;

	public LearningReportWriteDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public int createLearningReport(LrWriteDTO writeDTO) {
		return this.sqlSession.insert("api.learningReportWrite.insert", writeDTO);
	}
}

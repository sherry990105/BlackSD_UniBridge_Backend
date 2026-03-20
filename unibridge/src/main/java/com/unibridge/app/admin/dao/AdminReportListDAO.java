package com.unibridge.app.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.app.admin.dto.AdminReportListDTO;
import com.unibridge.config.MyBatisConfig;

public class AdminReportListDAO {
	public SqlSession sqlSession;

	public AdminReportListDAO() {
	   sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public List<AdminReportListDTO> selectReportDetail(int matchingNumber) {
		return sqlSession.selectList("mvc.adminReportList.selectLrByMatchingNumber", matchingNumber);
	}
}

package com.unibridge.api.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.api.admin.dto.AdminReportListDTO;
import com.unibridge.config.MyBatisConfig;

public class AdminReportListDAO {
	public SqlSession sqlSession;

	public AdminReportListDAO() {
	   sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public List<AdminReportListDTO> selectLrByMatchingNumber(int matchingNumber) {
		return sqlSession.selectList("mvc.adminReportList.selectLrByMatchingNumber", matchingNumber);
	}
}

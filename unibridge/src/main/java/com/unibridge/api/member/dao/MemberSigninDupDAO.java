package com.unibridge.api.member.dao;

import org.apache.ibatis.session.SqlSession;

import com.unibridge.api.learningReport.dto.LrModifyDTO;
import com.unibridge.config.MyBatisConfig;

public class MemberSigninDupDAO {
	public SqlSession sqlSession;

	public MemberSigninDupDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public int checkDuplicatedID(String memberId) {
		return this.sqlSession.selectOne("api.signin.checkDupID", memberId);
	}
	
	public int checkDuplicatedNickname(String memberNickname) {
		return this.sqlSession.selectOne("api.signin.checkDupNickname", memberNickname);
	}
}

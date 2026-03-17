package com.unibridge.app.admin.dto;

public class AdminDTO {
//	CREATE TABLE UB_ADMIN (
//		    admin_number      NUMBER         NOT NULL,
//		    admin_id          VARCHAR2(64)   NOT NULL, 
//		    admin_pw          VARCHAR2(255)  NOT NULL,  
//		    admin_nickname    VARCHAR2(100)  NOT NULL,                          
//		    CONSTRAINT pk_ub_admin PRIMARY KEY (admin_number)
//		);
	
	private int adminNumber;
	private String adminId;
	private String adminPw;
	private String adminNickname;

	public int getAdminNumber() {
		return adminNumber;
	}
	public String getAdminId() {
		return adminId;
	}
	public String getAdminPw() {
		return adminPw;
	}
	public String getAdminNickname() {
		return adminNickname;
	}
	
	
}

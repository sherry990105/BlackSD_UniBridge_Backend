package com.unibridge.api.admin.dto;

import java.io.Serializable;
import java.util.Date;

//	SELECT 
//		mth.matching_number AS "matching_number",
//		mtr.member_name     AS "mentor_name",
//		mte.member_name     AS "mentee_name",
//		mth.matching_start  AS "matching_start",
//		ulr.report_id		AS "lr_report_id",
//		ulr.report_week		AS "lr_report_week",
//		ulr.report_session	AS "lr_report_session",
//		ulr.report_date		AS "lr_report_date",
//		ulr.subject_number	AS "lr_subject_number",
//		ulr.subject_title	AS "lr_subject_title",
//		ulr.subject_summary AS "lr_subject_summary",
//		ulr.subject_content	AS "lr_subject_content"
//	FROM UB_MATCHING mth
//	INNER JOIN UB_MEMBER mtr
//		ON mth.mentor_number = mtr.member_number
//	INNER JOIN UB_MEMBER mte
//		ON mth.mentee_number = mte.member_number
//	INNER JOIN UB_LEARNING_REPORT ulr
//		ON mth.matching_number = ulr.matching_number
//	WHERE mth.matching_state != '매칭됨'
//		AND mth.matching_number = 19;
//	ORDER BY mth.matching_number;

public class AdminReportListDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int matchingNumber;
	private String mentorName;
	private String menteeName;
	private Date matchingStart;
	
	private int 	lrReportNumber;
	private int 	lrReportWeek;
	private int 	lrReportSession;
	private Date 	lrReportDate;
	private int 	lrSubjectNumber;
	private String	lrSubjectTitle;
	private String 	lrSubjectSummary;
	private String	lrSubjectContent;
	
	public AdminReportListDTO() {
		super();
	}
	
	public AdminReportListDTO(int matchingNumber, String mentorName, String menteeName, Date matchingStart,
			int lrReportNumber, int lrReportWeek, int lrReportSession, Date lrReportDate, int lrSubjectNumber,
			String lrSubjectTitle, String lrSubjectSummary, String lrSubjectContent) {
		super();
		this.matchingNumber = matchingNumber;
		this.mentorName = mentorName;
		this.menteeName = menteeName;
		this.matchingStart = matchingStart;
		this.lrReportNumber = lrReportNumber;
		this.lrReportWeek = lrReportWeek;
		this.lrReportSession = lrReportSession;
		this.lrReportDate = lrReportDate;
		this.lrSubjectNumber = lrSubjectNumber;
		this.lrSubjectTitle = lrSubjectTitle;
		this.lrSubjectSummary = lrSubjectSummary;
		this.lrSubjectContent = lrSubjectContent;
	}
	
	@Override
	public String toString() {
		return "AdminReportListDTO [matchingNumber=" + matchingNumber + ", mentorName=" + mentorName + ", menteeName="
				+ menteeName + ", matchingStart=" + matchingStart + ", lrReportNumber=" + lrReportNumber
				+ ", lrReportWeek=" + lrReportWeek + ", lrReportSession=" + lrReportSession + ", lrReportDate="
				+ lrReportDate + ", lrSubjectNumber=" + lrSubjectNumber + ", lrSubjectTitle=" + lrSubjectTitle
				+ ", lrSubjectSummary=" + lrSubjectSummary + ", lrSubjectContent=" + lrSubjectContent + ", toString()="
				+ super.toString() + "]";
	}
	
	public int getMatchingNumber() {
		return matchingNumber;
	}
	public void setMatchingNumber(int matchingNumber) {
		this.matchingNumber = matchingNumber;
	}
	public String getMentorName() {
		return mentorName;
	}
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
	public String getMenteeName() {
		return menteeName;
	}
	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}
	public Date getMatchingStart() {
		return matchingStart;
	}
	public void setMatchingStart(Date matchingStart) {
		this.matchingStart = matchingStart;
	}
	public int getLrReportNumber() {
		return lrReportNumber;
	}
	public void setLrReportNumber(int lrReportNumber) {
		this.lrReportNumber = lrReportNumber;
	}
	public int getLrReportWeek() {
		return lrReportWeek;
	}
	public void setLrReportWeek(int lrReportWeek) {
		this.lrReportWeek = lrReportWeek;
	}
	public int getLrReportSession() {
		return lrReportSession;
	}
	public void setLrReportSession(int lrReportSession) {
		this.lrReportSession = lrReportSession;
	}
	public Date getLrReportDate() {
		return lrReportDate;
	}
	public void setLrReportDate(Date lrReportDate) {
		this.lrReportDate = lrReportDate;
	}
	public int getLrSubjectNumber() {
		return lrSubjectNumber;
	}
	public void setLrSubjectNumber(int lrSubjectNumber) {
		this.lrSubjectNumber = lrSubjectNumber;
	}
	public String getLrSubjectTitle() {
		return lrSubjectTitle;
	}
	public void setLrSubjectTitle(String lrSubjectTitle) {
		this.lrSubjectTitle = lrSubjectTitle;
	}
	public String getLrSubjectSummary() {
		return lrSubjectSummary;
	}
	public void setLrSubjectSummary(String lrSubjectSummary) {
		this.lrSubjectSummary = lrSubjectSummary;
	}
	public String getLrSubjectContent() {
		return lrSubjectContent;
	}
	public void setLrSubjectContent(String lrSubjectContent) {
		this.lrSubjectContent = lrSubjectContent;
	}
}

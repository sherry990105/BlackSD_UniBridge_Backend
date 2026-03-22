package com.unibridge.api.learningReport.dto;

import java.util.Date;

public class LrWriteDTO {
	private int  	matchingNumber;
	private Date 	matchingStart;
	private int 	lrSubjectNumber;
	private String	lrSubjectName;
	private String	lrSubjectTitle;
	private String 	lrSubjectSummary;
	private String	lrSubjectContent;
	private int 	lrReportWeek;
	private int 	lrReportSession;
	private Date 	lrReportDate;
	
	public LrWriteDTO() {
		super();
	}

	public LrWriteDTO(int matchingNumber, Date matchingStart, int lrSubjectNumber, String lrSubjectName,
			String lrSubjectTitle, String lrSubjectSummary, String lrSubjectContent, int lrReportWeek,
			int lrReportSession, Date lrReportDate) {
		super();
		this.matchingNumber = matchingNumber;
		this.matchingStart = matchingStart;
		this.lrSubjectNumber = lrSubjectNumber;
		this.lrSubjectName = lrSubjectName;
		this.lrSubjectTitle = lrSubjectTitle;
		this.lrSubjectSummary = lrSubjectSummary;
		this.lrSubjectContent = lrSubjectContent;
		this.lrReportWeek = lrReportWeek;
		this.lrReportSession = lrReportSession;
		this.lrReportDate = lrReportDate;
	}

	@Override
	public String toString() {
		return "LrWriteDTO [matchingNumber=" + matchingNumber + ", matchingStart=" + matchingStart
				+ ", lrSubjectNumber=" + lrSubjectNumber + ", lrSubjectName=" + lrSubjectName + ", lrSubjectTitle="
				+ lrSubjectTitle + ", lrSubjectSummary=" + lrSubjectSummary + ", lrSubjectContent=" + lrSubjectContent
				+ ", lrReportWeek=" + lrReportWeek + ", lrReportSession=" + lrReportSession + ", lrReportDate="
				+ lrReportDate + ", toString()=" + super.toString() + "]";
	}

	public int getMatchingNumber() {
		return matchingNumber;
	}
	public void setMatchingNumber(int matchingNumber) {
		this.matchingNumber = matchingNumber;
	}
	public Date getMatchingStart() {
		return matchingStart;
	}
	public void setMatchingStart(Date matchingStart) {
		this.matchingStart = matchingStart;
	}
	public int getLrSubjectNumber() {
		return lrSubjectNumber;
	}
	public void setLrSubjectNumber(int lrSubjectNumber) {
		this.lrSubjectNumber = lrSubjectNumber;
	}
	public String getLrSubjectName() {
		return lrSubjectName;
	}
	public void setLrSubjectName(String lrSubjectName) {
		this.lrSubjectName = lrSubjectName;
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
}

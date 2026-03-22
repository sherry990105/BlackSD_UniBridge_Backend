package com.unibridge.api.learningReport.dto;

import java.io.Serializable;
import java.util.Date;

public class LrDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int matchingNumber;
	private int mentorNumber;
	private int menteeNumber;
	private String mentorName;
	private String menteeName;
	private Date matchingStart;
	
	private int 	lrReportNumber;
	private int 	lrReportWeek;
	private int 	lrReportSession;
	private Date 	lrReportDate;
	private int 	lrSubjectNumber;
	private String	lrSubjectName;
	private String	lrSubjectTitle;
	private String 	lrSubjectSummary;
	private String	lrSubjectContent;
	
	public LrDTO() {
		super();
	}

	public LrDTO(int matchingNumber, int mentorNumber, int menteeNumber, String mentorName, String menteeName,
			Date matchingStart, int lrReportNumber, int lrReportWeek, int lrReportSession, Date lrReportDate,
			int lrSubjectNumber, String lrSubjectName, String lrSubjectTitle, String lrSubjectSummary,
			String lrSubjectContent) {
		super();
		this.matchingNumber = matchingNumber;
		this.mentorNumber = mentorNumber;
		this.menteeNumber = menteeNumber;
		this.mentorName = mentorName;
		this.menteeName = menteeName;
		this.matchingStart = matchingStart;
		this.lrReportNumber = lrReportNumber;
		this.lrReportWeek = lrReportWeek;
		this.lrReportSession = lrReportSession;
		this.lrReportDate = lrReportDate;
		this.lrSubjectNumber = lrSubjectNumber;
		this.lrSubjectName = lrSubjectName;
		this.lrSubjectTitle = lrSubjectTitle;
		this.lrSubjectSummary = lrSubjectSummary;
		this.lrSubjectContent = lrSubjectContent;
	}

	@Override
	public String toString() {
		return "LrDTO [matchingNumber=" + matchingNumber + ", mentorNumber=" + mentorNumber + ", menteeNumber="
				+ menteeNumber + ", mentorName=" + mentorName + ", menteeName=" + menteeName + ", matchingStart="
				+ matchingStart + ", lrReportNumber=" + lrReportNumber + ", lrReportWeek=" + lrReportWeek
				+ ", lrReportSession=" + lrReportSession + ", lrReportDate=" + lrReportDate + ", lrSubjectNumber="
				+ lrSubjectNumber + ", lrSubjectName=" + lrSubjectName + ", lrSubjectTitle=" + lrSubjectTitle
				+ ", lrSubjectSummary=" + lrSubjectSummary + ", lrSubjectContent=" + lrSubjectContent + "]";
	}

	public int getMatchingNumber() {
		return matchingNumber;
	}
	public void setMatchingNumber(int matchingNumber) {
		this.matchingNumber = matchingNumber;
	}
	public int getMentorNumber() {
		return mentorNumber;
	}
	public void setMentorNumber(int mentorNumber) {
		this.mentorNumber = mentorNumber;
	}
	public int getMenteeNumber() {
		return menteeNumber;
	}
	public void setMenteeNumber(int menteeNumber) {
		this.menteeNumber = menteeNumber;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

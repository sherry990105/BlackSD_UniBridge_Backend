package com.unibridge.api.learningReport.dto;

public class LrModifyDTO {
	private int 	lrReportNumber;
	private int 	lrSubjectNumber;
	private String	lrSubjectName;
	private String	lrSubjectTitle;
	private String 	lrSubjectSummary;
	private String	lrSubjectContent;
	
	public LrModifyDTO() {
		super();
	}
	
	public LrModifyDTO(int lrReportNumber, int lrSubjectNumber, String lrSubjectName, String lrSubjectTitle,
			String lrSubjectSummary, String lrSubjectContent) {
		super();
		this.lrReportNumber = lrReportNumber;
		this.lrSubjectNumber = lrSubjectNumber;
		this.lrSubjectName = lrSubjectName;
		this.lrSubjectTitle = lrSubjectTitle;
		this.lrSubjectSummary = lrSubjectSummary;
		this.lrSubjectContent = lrSubjectContent;
	}
	
	@Override
	public String toString() {
		return "LrUpdateDTO [lrReportNumber=" + lrReportNumber + ", lrSubjectNumber=" + lrSubjectNumber
				+ ", lrSubjectName=" + lrSubjectName + ", lrSubjectTitle=" + lrSubjectTitle + ", lrSubjectSummary="
				+ lrSubjectSummary + ", lrSubjectContent=" + lrSubjectContent + ", toString()=" + super.toString()
				+ "]";
	}
	
	public int getLrReportNumber() {
		return lrReportNumber;
	}
	public void setLrReportNumber(int lrReportNumber) {
		this.lrReportNumber = lrReportNumber;
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
}
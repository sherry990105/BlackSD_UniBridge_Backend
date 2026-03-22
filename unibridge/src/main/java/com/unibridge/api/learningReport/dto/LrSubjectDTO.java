package com.unibridge.api.learningReport.dto;

public class LrSubjectDTO {
	private int 	subjectNumber;
	private String 	subjectName;
	
	public LrSubjectDTO() {
		super();
	}
	
	public LrSubjectDTO(int subjectNumber, String subjectName) {
		super();
		this.subjectNumber = subjectNumber;
		this.subjectName = subjectName;
	}
	
	@Override
	public String toString() {
		return "LrSubjectDTO [subjectNumber=" + subjectNumber + ", subjectName=" + subjectName + ", toString()="
				+ super.toString() + "]";
	}
	
	public int getSubjectNumber() {
		return subjectNumber;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectNumber(int subjectNumber) {
		this.subjectNumber = subjectNumber;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}

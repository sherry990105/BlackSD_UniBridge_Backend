package com.unibridge.api.learningReport.dto;

import java.util.Date;

public class LrMatchingInfoDTO {
	private int matchingNumber;
	private Date matchingStart;
	private Date matchingEnd;
	private String matchingState;
	private String subjectName;
	private int payId;
	
	public LrMatchingInfoDTO() {
		super();
	}
	
	public LrMatchingInfoDTO(int matchingNumber, Date matchingStart, Date matchingEnd, String matchingState,
			String subjectName, int payId) {
		super();
		this.matchingNumber = matchingNumber;
		this.matchingStart = matchingStart;
		this.matchingEnd = matchingEnd;
		this.matchingState = matchingState;
		this.subjectName = subjectName;
		this.payId = payId;
	}
	
	@Override
	public String toString() {
		return "LearningReportMatchingInfoDTO [matchingNumber=" + matchingNumber + ", matchingStart=" + matchingStart
				+ ", matchingEnd=" + matchingEnd + ", matchingState=" + matchingState + ", subjectName=" + subjectName
				+ ", payId=" + payId + ", toString()=" + super.toString() + "]";
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
	public Date getMatchingEnd() {
		return matchingEnd;
	}
	public void setMatchingEnd(Date matchingEnd) {
		this.matchingEnd = matchingEnd;
	}
	public String getMatchingState() {
		return matchingState;
	}
	public void setMatchingState(String matchingState) {
		this.matchingState = matchingState;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
}

package com.unibridge.api.learningReport.dto;

import java.util.List;

public class LrDetailDTO {
	private LrMatchingInfoDTO 	matchingInfo;
	private List<LrDTO> 		reports;
	
	public LrDetailDTO() {
		super();
	}
	
	public LrDetailDTO(LrMatchingInfoDTO matchingInfo, List<LrDTO> reports) {
		super();
		this.matchingInfo = matchingInfo;
		this.reports = reports;
	}
	
	@Override
	public String toString() {
		return "LrDetailDTO [matchingInfo=" + matchingInfo + ", reports=" + reports + ", toString()=" + super.toString()
				+ "]";
	}
	
	public LrMatchingInfoDTO getMatchingInfo() {
		return matchingInfo;
	}
	public void setMatchingInfo(LrMatchingInfoDTO matchingInfo) {
		this.matchingInfo = matchingInfo;
	}
	public List<LrDTO> getReports() {
		return reports;
	}
	public void setReports(List<LrDTO> reports) {
		this.reports = reports;
	}
}

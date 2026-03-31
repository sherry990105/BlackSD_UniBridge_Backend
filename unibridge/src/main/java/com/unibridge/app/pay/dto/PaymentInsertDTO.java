package com.unibridge.app.pay.dto;

public class PaymentInsertDTO {
	private int payId;
	private int mentorNumber;
	private int menteeNumber;
	
	public PaymentInsertDTO() {
		super();
	}
	
	public PaymentInsertDTO(int payId, int mentorNumber, int menteeNumber) {
		super();
		this.payId = payId;
		this.mentorNumber = mentorNumber;
		this.menteeNumber = menteeNumber;
	}

	@Override
	public String toString() {
		return "PaymentInsertDTO [payId=" + payId + ", mentorNumber=" + mentorNumber + ", menteeNumber=" + menteeNumber
				+ ", toString()=" + super.toString() + "]";
	}

	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
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
}

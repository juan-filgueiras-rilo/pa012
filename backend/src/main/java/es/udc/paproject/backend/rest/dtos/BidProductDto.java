package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class BidProductDto {

	//Product Info
	private Long remainingTime;
	private BigDecimal currentPrice;
	
	//Bid Info
	private String state;
	
	public BidProductDto() {}
	
	public BidProductDto(Long remainingTime, BigDecimal currentPrice, String state) {
		this.remainingTime = remainingTime;
		this.currentPrice = currentPrice;
		this.state = state;
	}

	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

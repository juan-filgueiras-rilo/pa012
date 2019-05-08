package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import es.udc.paproject.backend.model.entities.Bid.BidStatus;

public class BidProductDto {

	//Product Info
	private Long remainingTime;
	private BigDecimal currentPrice;
	private BigDecimal minPrice;
	
	//Bid Info
	private BidStatus bidStatus;
	
	public BidProductDto() {}
	
	public BidProductDto(Long remainingTime, BigDecimal currentPrice, BigDecimal minPrice, BidStatus bidStatus) {
		this.remainingTime = remainingTime;
		this.currentPrice = currentPrice;
		this.minPrice = minPrice;
		this.bidStatus = bidStatus;
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

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BidStatus getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(BidStatus bidStatus) {
		this.bidStatus = bidStatus;
	}

}

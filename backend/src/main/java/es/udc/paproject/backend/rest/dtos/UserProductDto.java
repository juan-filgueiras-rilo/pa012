package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class UserProductDto {

	private Long id;
	private String name;
	private Long remainingTime;
	private BigDecimal currentPrice;
	private String winnerEmail;
	
	public UserProductDto() {}

	public UserProductDto(Long id, String name, Long remainingTime,
			BigDecimal currentPrice, String winnerEmail) {
		this.id = id;
		this.name = name;
		this.remainingTime = remainingTime;
		this.currentPrice = currentPrice;
		this.winnerEmail = winnerEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getWinnerEmail() {
		return winnerEmail;
	}

	public void setWinnerEmail(String winnerEmail) {
		this.winnerEmail = winnerEmail;
	}
}

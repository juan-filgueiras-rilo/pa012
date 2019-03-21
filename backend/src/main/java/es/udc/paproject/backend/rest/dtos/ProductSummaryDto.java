package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductSummaryDto {

	private Long id;
	private Long categoryId;
	private String name;
	private BigDecimal currentPrice;
	private Long remainingTime;
	
	public ProductSummaryDto() {}
	
	public ProductSummaryDto(Long id, Long categoryId, String name, 
			BigDecimal currentPrice, Long remainingTime) {
		
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.currentPrice = currentPrice;
		this.remainingTime = remainingTime;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}
}

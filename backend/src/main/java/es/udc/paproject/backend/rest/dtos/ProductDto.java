package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductDto {

	private Long id;
	private Long categoryId;
	private String name;
	private String description;
	private String userName;
	private Long creationTime;
	private Long remainingTime;
	private BigDecimal initialPrice;
	private BigDecimal currentPrice;
	private BigDecimal minPrice;
	private String shipmentInfo;
	
	public ProductDto() {}

	public ProductDto(Long id, Long categoryId, String name, String description,
			String userName, Long creationTime,Long remainingTime,
			BigDecimal initialPrice, BigDecimal currentPrice,
			BigDecimal minPrice, String shipmentInfo) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.userName = userName;
		this.creationTime = creationTime;
		this.remainingTime = remainingTime;
		this.initialPrice = initialPrice;
		this.currentPrice = currentPrice;
		this.minPrice = minPrice;
		this.shipmentInfo = shipmentInfo;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public BigDecimal getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(BigDecimal initialPrice) {
		this.initialPrice = initialPrice;
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

	public void setMidPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public String getShipmentInfo() {
		return shipmentInfo;
	}

	public void setShipmentInfo(String shipmentInfo) {
		this.shipmentInfo = shipmentInfo;
	}
}

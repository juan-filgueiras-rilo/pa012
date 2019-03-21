package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductDetailDto {

	private Long id;
	private String categoryName;
	private String name;
	private String description;
	private String userName;
	private Long creationTime;
	private Long remainingTime;
	private BigDecimal initialPrice;
	private BigDecimal currentPrice;
	private String shipmentInfo;
	
	public ProductDetailDto() {}

	public ProductDetailDto(Long id, String categoryName, String name, String description,
			String userName, Long creationTime,Long remainingTime,
			BigDecimal initialPrice, BigDecimal currentPrice,
			String shipmentInfo) {
		this.id = id;
		this.categoryName = categoryName;
		this.name = name;
		this.description = description;
		this.userName = userName;
		this.creationTime = creationTime;
		this.remainingTime = remainingTime;
		this.initialPrice = initialPrice;
		this.currentPrice = currentPrice;
		this.shipmentInfo = shipmentInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getShipmentInfo() {
		return shipmentInfo;
	}

	public void setShipmentInfo(String shipmentInfo) {
		this.shipmentInfo = shipmentInfo;
	}
}

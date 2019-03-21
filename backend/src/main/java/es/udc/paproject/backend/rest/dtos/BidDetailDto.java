package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

import es.udc.paproject.backend.model.entities.Bid.BidState;

public class BidDetailDto {

	//Product Info
	private Long productId;
	private String categoryName;
	private String name;
	private String description;
	private String userName;
	private Long creationTime;
	private Long remainingTime;
	private BigDecimal initialPrice;
	private BigDecimal currentPrice;
	private String shipmentInfo;
	
	//Bid Info
	private Long id;
	private BigDecimal quantity;
	private BidState state;
	private Long date;
	
	public BidDetailDto() {}

	
	public BidDetailDto(Long productId, String categoryName, String name, String description, String userName,
			Long creationTime, Long remainingTime, BigDecimal initialPrice, BigDecimal currentPrice,
			String shipmentInfo, Long id, BigDecimal quantity, BidState state, Long date) {
		this.productId = productId;
		this.categoryName = categoryName;
		this.name = name;
		this.description = description;
		this.userName = userName;
		this.creationTime = creationTime;
		this.remainingTime = remainingTime;
		this.initialPrice = initialPrice;
		this.currentPrice = currentPrice;
		this.shipmentInfo = shipmentInfo;
		this.id = id;
		this.quantity = quantity;
		this.state = state;
		this.date = date;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BidState getState() {
		return state;
	}

	public void setState(BidState state) {
		this.state = state;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}

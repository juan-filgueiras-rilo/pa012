package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

import es.udc.paproject.backend.model.entities.Bid.BidStatus;

public class BidDto {
	
	private BigDecimal quantity;
	private Long productId;
	private String productName;
	private BidStatus bidStatus;
	private Long date;
	
	public BidDto() {}

	public BidDto(BigDecimal quantity, String productName, 
			Long productId, BidStatus bidStatus, Long date) {
		this.quantity = quantity;
		this.productId = productId;
		this.productName = productName;
		this.bidStatus = bidStatus;
		this.date = date;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BidStatus getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(BidStatus bidStatus) {
		this.bidStatus = bidStatus;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}

package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

import es.udc.paproject.backend.model.entities.Bid.BidStatus;

public class BidDto {
	
	private Long id;
	private BigDecimal quantity;
	private String productName;
	private BidStatus bidStatus;
	private Long date;
	
	public BidDto() {}

	public BidDto(Long id, BigDecimal quantity, String productName, 
			BidStatus bidStatus, Long date) {
		this.id = id;
		this.quantity = quantity;
		this.productName = productName;
		this.bidStatus = bidStatus;
		this.date = date;
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

package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class BidDto {
	
	private Long id;
	private BigDecimal quantity;
	private String productName;
	private String state;
	private Long date;
	
	public BidDto() {}

	public BidDto(Long id, BigDecimal quantity, String productName, 
			String state, Long date) {
		this.id = id;
		this.quantity = quantity;
		this.productName = productName;
		this.state = state;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}

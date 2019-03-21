package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BidParamsDto {

	private Long productId;
	private BigDecimal quantity;
	
	public BidParamsDto() {}
	
	public BidParamsDto(Long productId, BigDecimal quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	@NotNull
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@NotNull
	@Min((long)0.1)
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
}

package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BidParamsDto {

	private Long productId;
	private BigDecimal quantity;
	
	public BidParamsDto() {}
	
	public BidParamsDto(Long productId, BigDecimal quantity) {
		this.productId = productId;
		this.quantity = quantity.setScale(2, RoundingMode.HALF_EVEN);
	}

	@NotNull
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@NotNull
	@DecimalMin("0.1")
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity.setScale(2, RoundingMode.HALF_EVEN);
	}
	
}

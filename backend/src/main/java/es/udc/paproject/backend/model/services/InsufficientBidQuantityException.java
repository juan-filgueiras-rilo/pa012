package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public class InsufficientBidQuantityException extends Exception {
	
	private BigDecimal quantity; 
	
	public InsufficientBidQuantityException(BigDecimal quantity) {
		super("Bid's price must be more than " + quantity + "€");
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity.setScale(2, RoundingMode.HALF_EVEN);
	}

}

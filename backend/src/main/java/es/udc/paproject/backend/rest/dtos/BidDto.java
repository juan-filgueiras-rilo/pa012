package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import es.udc.paproject.backend.model.entities.Bid.BidState;

public class BidDto {
	
	public enum BidDtoState {WON, LOST, WINNING};
	
	private Long id;
	private BigDecimal quantity;
	private String productName;
	private BidDtoState state;
	private Long date;
	
	public BidDto() {}

	public BidDto(Long id, BigDecimal quantity, String productName, 
			BidState state, boolean isActive, Long date) {
		this.id = id;
		this.quantity = quantity;
		this.productName = productName;
		if(state == BidState.LOST) {
			this.state = BidDtoState.LOST;
		} else if(state == BidState.WINNING) {
			if(isActive) {
				this.state = BidDtoState.WINNING;
			} else {
				this.state = BidDtoState.WON;
			}
		}
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

	public BidDtoState getState() {
		return state;
	}

	public void setState(BidDtoState state) {
		this.state = state;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}

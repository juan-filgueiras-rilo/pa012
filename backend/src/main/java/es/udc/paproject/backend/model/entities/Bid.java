package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;

public class Bid {
	
	private enum StateType {WON, LOST, WINNING};
	
	private float bidQuantity;
	private float minPrice;
	private StateType state;
	private LocalDateTime date;
	private User idUser;
	private Product idProduct;
	
	private Bid() {}
	
	private Bid(float bidQuantity, float minPrice, StateType state, LocalDateTime date, User idUser, Product idProduct) {
		
		this.bidQuantity = bidQuantity;
		this.minPrice = minPrice;
		this.state = state;
		this.date = date;
		this.idUser = idUser;
		this.idProduct = idProduct; 
	}

	public float getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(float bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}

	public Product getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Product idProduct) {
		this.idProduct = idProduct;
	}
	

}

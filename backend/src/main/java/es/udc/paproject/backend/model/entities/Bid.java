package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bid {
	
	public enum StateType {WON, LOST, WINNING};
	
	private long id;
	private float bidQuantity;
	private float minPrice;
	private StateType state;
	private LocalDateTime date;
	private User user;
	private Product product;
	
	private Bid() {}
	
	private Bid(float bidQuantity, float minPrice, StateType state, LocalDateTime date, User user, Product product) {
		
		this.bidQuantity = bidQuantity;
		this.minPrice = minPrice;
		this.state = state;
		this.date = date;
		this.user = user;
		this.product = product; 
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	

	public void setId(long id) {
		this.id = id;
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

	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}

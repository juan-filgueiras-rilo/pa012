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
	
	public enum BidState {WON, LOST, WINNING};
	
	private long id;
	private float quantity;
	private Product product;
	private User user;
	private BidState state;
	private LocalDateTime date;
	
	
	
	public Bid() {}
	
	public Bid(Float quantity, BidState state, LocalDateTime date, User user, Product product) {
		
		this.quantity = quantity;
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
	
	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public BidState getState() {
		return state;
	}

	public void setState(BidState state) {
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

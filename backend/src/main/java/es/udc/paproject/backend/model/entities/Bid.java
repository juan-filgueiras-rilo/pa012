package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bid {
	
	public enum BidState {LOST, WINNING};
	
	private long id;
	private BigDecimal quantity;
	private Product product;
	private User user;
	private BidState state;
	private LocalDateTime date;
	
	public Bid() {}
	
	public Bid(BigDecimal quantity, BidState state, User user, Product product) {
		
		this.quantity = quantity;
		this.state = state;
		this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
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
	
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity.setScale(2, RoundingMode.HALF_EVEN);
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

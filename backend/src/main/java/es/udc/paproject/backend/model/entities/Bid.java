package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import javax.persistence.Id;

@Entity
public class Bid {
	
	private Long id;
	private BigDecimal quantity;
	private Product product;
	private User user;
	private LocalDateTime date;
	
	public enum BidStatus {
		WINNING,
		WON,
		LOST
	};
	
	public Bid() {}
	
	public Bid(BigDecimal quantity, User user, Product product) {
		
		this.quantity = quantity;
		this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		this.user = user;
		this.product = product; 
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
		this.quantity = quantity.setScale(2, RoundingMode.HALF_EVEN);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="productId")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Transient
	public boolean isWinning() {
		return this.product.getWinningBid().getId() == this.id;
	}
	
	@Transient
	public BidStatus getBidStatus() {
		if(this.isWinning()) {
			if(this.getProduct().isActive()) {
				return BidStatus.WINNING;
			} else {
				return BidStatus.WON;
			}
		} else {
			return BidStatus.LOST;
		}
	}
	
}

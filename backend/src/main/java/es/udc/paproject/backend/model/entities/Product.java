package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {
	
	private long id;
	private String name;
	private String descriptionProduct;
	private long duration;
	private LocalDateTime creationTime;
	private BigDecimal currentPrice;
	private BigDecimal initialPrice;
	private String shipmentInfo;
	private Category category;
	private User user;
	private Optional<Bid> winningBid;
		
	public Product() {}
	
	public Product(String name, String descriptionProduct, long duration, 
			LocalDateTime creationTime, BigDecimal initialPrice, String shipmentInfo,
			Category category, User user) {
		this.name = name;
		this.descriptionProduct = descriptionProduct; 
		this.duration = duration; 
		this.creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		this.initialPrice = initialPrice;
		this.initialPrice = currentPrice; 
		this.shipmentInfo = shipmentInfo;
		this.category = category;
		this.user = user;
		this.winningBid = Optional.empty();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionProduct() {
		return descriptionProduct;
	}

	public void setDescriptionProduct(String descriptionProduct) {
		this.descriptionProduct = descriptionProduct;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	public BigDecimal getCurrentPrince() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice.setScale(2, RoundingMode.HALF_EVEN);
	}
	public BigDecimal getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(BigDecimal initialPrice) {
		this.initialPrice = initialPrice.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getShipmentInfo() {
		return shipmentInfo;
	}

	public void setShipmentInfo(String shipmentInfo) {
		this.shipmentInfo = shipmentInfo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
	public boolean isActive() {
		return (this.creationTime.plusMinutes(this.duration).isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
	}

	public Optional<Bid> getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(Bid winningBid) {
		this.winningBid = Optional.of(winningBid);
	}
	
	
	
}

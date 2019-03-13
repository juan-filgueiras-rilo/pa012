package es.udc.paproject.backend.model.entities;

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
	private float currentPrice;
	private float initialPrice;
	private String shipmentInfo;
	private Category category;
	private User user;
	private Optional<Bid> winningBid;
		
	public Product() {}
	
	public Product(String name, String descriptionProduct, 
			LocalDateTime creationTime,float currentPrice, float initialPrice, String shipmentInfo,
			Category category, User user) {
		this.name = name;
		this.descriptionProduct = descriptionProduct; 
		this.creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		this.currentPrice = currentPrice;
		this.initialPrice = initialPrice;
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

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public float getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(float initialPrice) {
		this.initialPrice = initialPrice;
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

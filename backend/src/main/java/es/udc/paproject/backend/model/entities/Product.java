package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

@Entity
public class Product {
	
	private Long id;
	private String name;
	private String descriptionProduct;
	private Long duration;
	private LocalDateTime creationTime;
	private LocalDateTime endDate;
	private BigDecimal currentPrice;
	private BigDecimal initialPrice;
	private String shipmentInfo;
	private Category category;
	private User user;
	private Bid winningBid;
	private String winningUserEmail;
	private Long version;

	public Product() {}
	
	public Product(String name, String descriptionProduct, Long duration, 
			BigDecimal initialPrice, String shipmentInfo,
			Category category, User user) {
		this.name = name;
		this.descriptionProduct = descriptionProduct; 
		this.duration = duration; 
		this.creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		this.endDate = creationTime.plusMinutes(duration).truncatedTo(ChronoUnit.SECONDS);
		this.initialPrice = initialPrice;
		this.currentPrice = this.initialPrice.add(BigDecimal.ZERO);
		this.shipmentInfo = shipmentInfo;
		this.category = category;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public BigDecimal getCurrentPrice() {
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
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="categoryId")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="winningBidId")
	@BatchSize(size=10)
	public Bid getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(Bid winningBid) {
		this.winningBid = winningBid;
	}
	
	public String getWinningUserEmail() {	
		return winningUserEmail;
	}

	public void setWinningUserEmail(String winningUserEmail) {
		this.winningUserEmail = winningUserEmail;
	}
	
	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Transient
	public BigDecimal getMinPrice() {
		if(this.currentPrice.compareTo(this.initialPrice) == 0) {
			return this.initialPrice;
		}
		return this.currentPrice.add(new BigDecimal(0.01)).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@Transient
	public boolean isActive() {
		return (this.endDate.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
	}
	
	@Transient
	public Long getRemainingTime() {
		long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), this.endDate);
		if (secondsLeft > 0) {
			return Math.max(Math.floorDiv(secondsLeft,60), 1);
		} else {
			return Long.valueOf(0);
		}
//		return this.endDate.(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli())
//				.truncatedTo(ChronoUnit.SECONDS).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 60000;
	}
	
}

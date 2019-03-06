package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {
	
	private long id;
	private String name;
	private String descriptionProduct;
	private long duration;
	private LocalDateTime creationTime;

	private float initialPrice;
	private String shipmentInfo;
	private Category category;
	private User user;

	public Product() {}
	
	public Product(String name, String descriptionProduct,
			long bidTime, float initialPrice, String shipmentInfo,
			Category category, User user) {
		this.name = name;
		this.descriptionProduct = descriptionProduct; 
		this.duration = duration;
		this.creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		this.initialPrice = initialPrice;
		this.shipmentInfo = shipmentInfo;
		this.category = category;
		this.user = user;
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
	
}

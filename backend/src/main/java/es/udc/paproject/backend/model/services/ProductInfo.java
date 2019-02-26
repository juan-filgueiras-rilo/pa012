package es.udc.paproject.backend.model.services;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.User;

public class ProductInfo {

	private String name;
	private String descriptionProduct;
	private User user;
	private float initialPrice;
	private long bidTime;
	private Category category;
	private String shipmentInfo;
	private List<BidInfo> bidInfos;

	public ProductInfo(String name, String descriptionProduct, long bidTime, float initialPrice, String shipmentInfo,
			Category category, User user, List<BidInfo> bidInfos2) {
		this.name = name;
		this.descriptionProduct = descriptionProduct; 
		this.bidTime = bidTime;
		this.initialPrice = initialPrice;
		this.shipmentInfo = shipmentInfo;
		this.category = category;
		this.user = user;
		this.bidInfos = bidInfos2;
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

	public long getBidTime() {
		return bidTime;
	}

	public void setBidTime(long bidTime) {
		this.bidTime = bidTime;
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
	
	public List<BidInfo> getBidInfos() {
		return bidInfos;
	}

	public void setBidInfos(List<BidInfo> bidInfos) {
		this.bidInfos = bidInfos;
	}

}

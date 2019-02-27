package es.udc.paproject.backend.model.entities;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	public enum RoleType {USER};

	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private RoleType role;
	private HashSet<Bid> bids = null;
	private HashSet<Product> products = null;

	public User() {}

	public User(String userName, String password, String firstName, String lastName, String email) {

		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.bids = new HashSet<Bid>();
		this.products = new HashSet<Product>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
	@OneToMany(mappedBy="user")
	public HashSet<Bid> getBids() {
		return bids;
	}

	public void setBids(HashSet<Bid> bids) {
		this.bids = bids;
	}

	public void addBid(Bid bid) {
		this.bids.add(bid);
	}
	
	@OneToMany(mappedBy="user")
	public HashSet<Product> getProducts() {
		return products;
	}

	public void setProducts(HashSet<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}

}

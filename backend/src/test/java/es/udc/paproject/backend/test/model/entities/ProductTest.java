package es.udc.paproject.backend.test.model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.udc.paproject.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.ProductService;
import es.udc.paproject.backend.model.services.UserService;

public class ProductTest {
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserService userService;
	
	private Product createProduct(String name, Long duration) {
		return new Product(name, "description", duration, new BigDecimal(10),
				"shipment", new Category(), new User());
	}
	
	private User signUpUser(String userName) {
		
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
		
		try {
			userService.signUp(user);
		} catch (DuplicateInstanceException e) {
			throw new RuntimeException(e);
		}
		
		return user;
		
	}

	@Test
	public void testIsActive() throws InstanceNotFoundException {
		
		Product product = createProduct("producto", (long) 10);
		assertTrue(product.isActive());
		
	}
	
	@Test
	public void testIsNotActive() {
		Product product = createProduct("producto", (long) 10);
		product.setCreationTime(LocalDateTime.now().minusMinutes(11));
		assertFalse(product.isActive());
	}
	
	@Test
	public void testRemainingTime() throws InstanceNotFoundException {
		Product product = createProduct("producto", (long) 10);
		assertEquals((long)10, Math.floorDiv(product.getRemainingTime(), 60000));
	}
	
}

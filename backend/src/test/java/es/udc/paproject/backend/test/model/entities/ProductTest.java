package es.udc.paproject.backend.test.model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public class ProductTest {
	
	private Product createProduct(String name, Long duration) {
		return new Product(name, "description", duration, new BigDecimal(10),
				"shipment", new Category(), new User());
	}
	
	@Test
	public void testIsActive() throws InstanceNotFoundException {
		Product product = createProduct("producto", (long) 10);
		assertTrue(product.isActive());
		
	}
	
	@Test
	public void testIsNotActive() {
		Product product = createProduct("producto", (long) 10);
		product.setEndDate((LocalDateTime.now().minusMinutes(100)));
		assertFalse(product.isActive());
	}
	
	@Test
	public void testRemainingTime() throws InstanceNotFoundException {
		Product product = createProduct("producto", (long) 40);
		assertEquals(new Long(40), product.getRemainingTime());
	}

	@Test
	public void testRemainingTimeIsZero() throws InstanceNotFoundException {
		Product product = createProduct("producto", (long) 1);
		product.setEndDate(product.getEndDate().minusMinutes(1));
		assertEquals(new Long(0), product.getRemainingTime());
	}
	
}

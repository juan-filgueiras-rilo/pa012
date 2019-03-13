package es.udc.paproject.backend.test.model.entities;

import org.junit.Test;

import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public class BidTest {

	private Product createProduct(long id, String name, long bidTime, float initialPrice) {	
		return new Product();
	}
	
	@Test
	public void testBid() {
		Product product1 = createProduct(1, "product1", 90, 12);
		Product product2 = createProduct(2, "product2", 180, 10);
		
		Bid bid = new Bid();
		
	}
}

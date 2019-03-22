package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.paproject.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;
import es.udc.paproject.backend.model.services.InsufficientBidQuantityException;
import es.udc.paproject.backend.model.services.ProductService;
import es.udc.paproject.backend.model.services.UnauthorizedBidException;
import es.udc.paproject.backend.model.services.UnauthorizedWinningUser;
import es.udc.paproject.backend.model.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BidServiceTest {
	
	private final Long NON_EXISTENT_ID = new Long(-1);

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private UserService userService;
		
	private User signUpUser(String userName) {
		
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
		
		try {
			userService.signUp(user);
		} catch (DuplicateInstanceException e) {
			throw new RuntimeException(e);
		}
		
		return user;
		
	}

		
	//Intentar hacer una puja con un producto que no existe
	@Test(expected = InstanceNotFoundException.class)
	public void testNotFoundException() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		bidService.createBid(NON_EXISTENT_ID, null, null);
	}
	
	//Intentar hacer una puja de un producto que ya ha expirado
	/*@Test(expected = ExpiratedProductDateException.class)
	public void testExpirateProductException() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		User user = signUpUser("user");
		
		Product producto = createProduct("Producto", 120, new BigDecimal(10), category1, user);
		
		bidService.createBid(user.getId(), producto.getId(), new BigDecimal(1));
		
	}*/
	
	//Hacer test para cada caso del createBid
	@Test
	public void testCreateBidNewIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		
		Product product = productService.addProduct(user1.getId(), "product1", "descripcion", (long)10, 
				new BigDecimal(10), "Info", category1.getId());
		
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(10));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12));
		
		
		assertEquals(product.getCurrentPrice().stripTrailingZeros(), new BigDecimal(10.5));
		assertEquals(bid2.getState(), Bid.BidState.WINNING);
		assertEquals(bid1.getState(), Bid.BidState.LOST);
		
	}
	
	@Test
	public void testFirstBid() throws InstanceNotFoundException, ExpiratedProductDateException, 
	UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		Bid bid = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));

		assertEquals(product.getCurrentPrice(), new BigDecimal(10));
		
	}
	
	@Test
	public void testLowerThanBigQuantity() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		User user4 = signUpUser("user4");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(1000));
		Bid bid3 = bidService.createBid(user4.getId(), product.getId(), new BigDecimal(500));
		
		assertEquals(new BigDecimal(500.5).setScale(2, RoundingMode.HALF_EVEN), product.getCurrentPrice());
		
	}
	
	@Test
	public void testLowerThanBigQuantity2() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		User user4 = signUpUser("user4");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(20));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(2000));
		Bid bid3 = bidService.createBid(user4.getId(), product.getId(), new BigDecimal(650.3));
		
		assertEquals(new BigDecimal(650.8).setScale(2, RoundingMode.HALF_EVEN), product.getCurrentPrice());
		
	}
	
	@Test
	public void testPriceLowerThanIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12.3));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(12.3).setScale(2, RoundingMode.HALF_EVEN));
		
	}
	
	@Test
	public void testIrregularIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");	
		User user4 = signUpUser("user4");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12.3));
		Bid bid3 = bidService.createBid(user4.getId(), product.getId(), new BigDecimal(15));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(12.80).setScale(2, RoundingMode.HALF_EVEN));
		assertEquals(product.getWinningBid().getUser(), user4);
		assertEquals(bid1.getState(), Bid.BidState.LOST);
		assertEquals(bid2.getState(), Bid.BidState.LOST);
	}
	
	@Test
	public void testOlderWinner() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");	
		User user4 = signUpUser("user4");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12));
		
		assertEquals(product.getWinningBid().getUser(), user2);
		assertEquals(bid1.getState(), Bid.BidState.WINNING);
	}
	
	@Test
	public void testNotEnoughQuantityToIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");	
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(14));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(13.7));
		
		assertEquals(new BigDecimal(14.0).setScale(2, RoundingMode.HALF_EVEN), product.getCurrentPrice());
	}
	
	//excepcion
	//El propietario del producto no puede pujar sobre él 
	@Test(expected = UnauthorizedBidException.class)
	public void testUnauthorizedBidException() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");	
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		bidService.createBid(user1.getId(), product.getId(), new BigDecimal(10));
	}
	
	//Si se puja menos que la puja que va ganando salta excepción
	@Test(expected = InsufficientBidQuantityException.class)
	public void testInsufficientBidQuantityException() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(15));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(2));

	}
	
	@Test(expected = UnauthorizedWinningUser.class)
	public void testUnauthorizedWinningUser() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",
				(long) 120, new BigDecimal(10), "pergv", category1.getId());
		
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(15));
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(20));

	}
	
	
	//Consultar las pujas realizadas por un usuario (getUserBids)
	@Test
	public void testGetUserBids() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser, DuplicateInstanceException {

		Category category1 = new Category("category1");
		
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		
		Product product1 = productService.addProduct(user1.getId(), "product1", "descripcion", (long)10, 
				new BigDecimal(10), "Info", category1.getId());
		
		Product product2 = productService.addProduct(user1.getId(), "product2", "descripcion", (long)10, 
				new BigDecimal(20), "Info", category1.getId());
	
		
		Bid bid = bidService.createBid(user2.getId(), product2.getId(), new BigDecimal(50));
		Bid bid2 = bidService.createBid(user2.getId(), product1.getId(), new BigDecimal(60));
		
		Block<Bid> blockExpected = new Block<>(Arrays.asList(bid, bid2), false);
		assertEquals(blockExpected, bidService.getUserBids(user2.getId(),0,2));	
	}
	

}

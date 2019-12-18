package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import es.udc.paproject.backend.model.entities.BidDao;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.AuctionService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;
import es.udc.paproject.backend.model.services.InsufficientBidQuantityException;
import es.udc.paproject.backend.model.services.UnauthorizedBidException;
import es.udc.paproject.backend.model.services.UnauthorizedWinningUserException;
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
	private ProductDao productDao;
	
	@Autowired
	private BidDao bidDao;

	@Autowired
	private AuctionService bidService;

	@Autowired
	private CatalogService productService;

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

	// Intentar hacer una puja con un producto que no existe
	@Test(expected = InstanceNotFoundException.class)
	public void testNotFoundException() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {
		bidService.createBid(NON_EXISTENT_ID, null, null);
	}

	// Intentar hacer una puja de un producto que ya ha expirado
	@Test(expected = ExpiratedProductDateException.class)
	public void testExpirateProductException() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);
		User user = signUpUser("user");

		Long product = productService.addProduct(user.getId(), "product1", "descripcion", (long) 10, new BigDecimal(10),
				"Info", category1.getId());

		Product productDetail = productService.getProductDetail(product);
		productDetail.setEndDate(productDetail.getCreationTime());
		try {
			bidService.createBid(user.getId(), product, new BigDecimal(1));
		} catch (ExpiratedProductDateException e) {
			assertEquals(e.getId(), product);
			throw e;
		}
	}

	// Hacer test para cada caso del createBid
	@Test
	public void testCreateBidNewIncrement() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");

		Long product = productService.addProduct(user1.getId(), "product1", "descripcion", (long) 10,
				new BigDecimal(10), "Info", category1.getId());

		Product productDetail = productService.getProductDetail(product);
		//Nos aseguramos de que esta bid no se tiene en cuenta despues de devolver los datos de la puja
		Bid bid1 = new Bid();
		bid1.setQuantity(new BigDecimal(100));
		bid1.setDate(LocalDateTime.now());
		bid1.setUser(new User());
		bid1.setProduct(new Product());
		
		bid1 = bidService.createBid(user2.getId(), product, new BigDecimal(10));
		Bid bid2 = bidService.createBid(user3.getId(), product, new BigDecimal(12));

		assertEquals(productDetail.getCurrentPrice().stripTrailingZeros(), new BigDecimal(10.5));
		assertEquals(bid2.getBidStatus(), Bid.BidStatus.WINNING);
		assertEquals(bid1.getBidStatus(), Bid.BidStatus.LOST);
		assertTrue(bid2.isWinning());
		assertFalse(bid1.isWinning());
		productDetail.setEndDate(productDetail.getEndDate().minusMinutes(10));
		productDao.save(productDetail);
		assertEquals(bid2.getBidStatus(), Bid.BidStatus.WON);

	}

	@Test
	public void testFirstBid() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);
		assertNull(productDetail.getWinnerEmail());
		bidService.createBid(user2.getId(), product, new BigDecimal(12));
		
		assertEquals(productDetail.getCurrentPrice(), new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
		assertEquals(productDetail.getWinnerEmail(), "user2@user2.com");

	}

	@Test
	public void testLowerThanBigQuantity() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		User user4 = signUpUser("user4");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		bidService.createBid(user2.getId(), product, new BigDecimal(12));
		bidService.createBid(user3.getId(), product, new BigDecimal(1000));
		bidService.createBid(user4.getId(), product, new BigDecimal(500));

		assertEquals(new BigDecimal(500.5).setScale(2, RoundingMode.HALF_EVEN), productDetail.getCurrentPrice());

	}

	@Test
	public void testLowerThanBigQuantity2() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		User user4 = signUpUser("user4");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		bidService.createBid(user2.getId(), product, new BigDecimal(20));
		bidService.createBid(user3.getId(), product, new BigDecimal(2000));
		bidService.createBid(user4.getId(), product, new BigDecimal(650.3));

		assertEquals(new BigDecimal(650.8).setScale(2, RoundingMode.HALF_EVEN), productDetail.getCurrentPrice());

	}

	@Test
	public void testPriceLowerThanIncrement() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		bidService.createBid(user2.getId(), product, new BigDecimal(12));
		bidService.createBid(user3.getId(), product, new BigDecimal(12.3));

		assertEquals(productDetail.getCurrentPrice(), new BigDecimal(12.3).setScale(2, RoundingMode.HALF_EVEN));

	}

	@Test
	public void testIrregularIncrement() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		User user4 = signUpUser("user4");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		Bid bid1 = bidService.createBid(user2.getId(), product, new BigDecimal(12));
		Bid bid2 = bidService.createBid(user3.getId(), product, new BigDecimal(12.3));
		bidService.createBid(user4.getId(), product, new BigDecimal(15));

		assertEquals(productDetail.getCurrentPrice(), new BigDecimal(12.80).setScale(2, RoundingMode.HALF_EVEN));
		assertEquals(productDetail.getWinningBid().getUser(), user4);
		assertEquals(bid1.getBidStatus(), Bid.BidStatus.LOST);
		assertEquals(bid2.getBidStatus(), Bid.BidStatus.LOST);
		assertFalse(bid1.isWinning());
		assertFalse(bid2.isWinning());
	}

	@Test
	public void testOlderWinner() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		Bid bid1 = bidService.createBid(user2.getId(), product, new BigDecimal(12));
		bidService.createBid(user3.getId(), product, new BigDecimal(12));

		assertEquals(productDetail.getWinningBid().getUser(), user2);
		// assertEquals(bid1.getState(), Bid.BidState.WINNING);
		assertTrue(bid1.isWinning());
	}

	@Test
	public void testNotEnoughQuantityToIncrement() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		Product productDetail = productService.getProductDetail(product);

		bidService.createBid(user2.getId(), product, new BigDecimal(14));
		bidService.createBid(user3.getId(), product, new BigDecimal(13.7));

		assertEquals(new BigDecimal(14.0).setScale(2, RoundingMode.HALF_EVEN), productDetail.getCurrentPrice());
	}

	// excepcion
	// El propietario del producto no puede pujar sobre él
	@Test(expected = UnauthorizedBidException.class)
	public void testUnauthorizedBidException() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {
		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());
		try {
			bidService.createBid(user1.getId(), product, new BigDecimal(10));
		} catch (UnauthorizedBidException e) {
			UnauthorizedBidException exception = new UnauthorizedBidException(e.getId());
			assertEquals(e.getId(), exception.getId());
			throw e;
		}
	}

	// Si se puja menos que la puja que va ganando salta excepción
	@Test(expected = InsufficientBidQuantityException.class)
	public void testInsufficientBidQuantityException() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {
		Category category1 = new Category("Category");
		BigDecimal insQuantity = new BigDecimal(2);
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());

		bidService.createBid(user2.getId(), product, new BigDecimal(15));
		try {
			bidService.createBid(user3.getId(), product, insQuantity);
		} catch (InsufficientBidQuantityException e) {
			Product p = productDao.findById(product).get();
			assertEquals((Double) e.getQuantity(),
					(Double) p.getMinPrice().setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			throw e;
		}
	}

	@Test(expected = UnauthorizedWinningUserException.class)
	public void testUnauthorizedWinningUser() throws InstanceNotFoundException, ExpiratedProductDateException,
			UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUserException {
		Category category1 = new Category("Category");
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");

		Long product = productService.addProduct(user1.getId(), "name", "reger", (long) 120, new BigDecimal(10),
				"pergv", category1.getId());

		bidService.createBid(user2.getId(), product, new BigDecimal(15));
		try {
			bidService.createBid(user2.getId(), product, new BigDecimal(20));
		} catch (UnauthorizedWinningUserException e) {
			Product p = productDao.findById(product).get();
			assertEquals(e.getId(),p.getWinningBid().getId());
			throw e;
		}
	}

	// Consultar las pujas realizadas por un usuario (getUserBids)
	@Test
	public void testGetUserBids()
			throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException,
			InsufficientBidQuantityException, UnauthorizedWinningUserException, DuplicateInstanceException {

		Category category1 = new Category("category1");

		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");

		Long product1 = productService.addProduct(user1.getId(), "product1", "descripcion", (long) 10,
				new BigDecimal(10), "Info", category1.getId());

		Long product2 = productService.addProduct(user1.getId(), "product2", "descripcion", (long) 10,
				new BigDecimal(20), "Info", category1.getId());

		Bid bid = bidService.createBid(user2.getId(), product2, new BigDecimal(50));
		Bid bid2 = bidService.createBid(user2.getId(), product1, new BigDecimal(60));

		Block<Bid> blockExpected = new Block<>(Arrays.asList(bid, bid2), false);
		Block<Bid> blockResult = bidService.getUserBids(user2.getId(), 0, 2);
		assertEquals(blockExpected, blockExpected);
		assertNotEquals(blockExpected, new Block<>(new ArrayList<>(), false));
		assertNotEquals(blockExpected, new Object());
		assertNotEquals(blockExpected, null);
		assertNotEquals(blockExpected, new Block<>(Arrays.asList(bid, bid2), true));
		assertNotEquals(blockExpected, new Block<>(Arrays.asList(bid), false));
		assertNotEquals(new Block<>(null, false), blockExpected);
		assertNotEquals(blockExpected, new Block<>(null, false));
		assertEquals(new Block<>(null, true), new Block<>(null, true));
		assertEquals(blockExpected, blockResult);
		assertEquals(blockExpected.getItems(), blockResult.getItems());
		assertEquals(blockExpected.hashCode(), blockResult.hashCode());
		assertNotEquals(new Block<>(null, true).hashCode(), blockResult.hashCode());
		assertEquals(blockExpected.getExistMoreItems(), blockResult.getExistMoreItems());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testGetUserBidsNonExistentUser() throws InstanceNotFoundException {

		bidService.getUserBids(NON_EXISTENT_ID, 0, 2);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateBidNonExistentProduct() throws InstanceNotFoundException, UnauthorizedBidException,
			ExpiratedProductDateException, InsufficientBidQuantityException, UnauthorizedWinningUserException {

		Category category1 = new Category("category1");

		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		bidService.createBid(user1.getId(), NON_EXISTENT_ID, new BigDecimal(50));
	}
}

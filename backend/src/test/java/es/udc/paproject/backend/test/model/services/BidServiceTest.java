package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
	
	private Product createProduct(String name, long duration, BigDecimal initialPrice,
			Category category, User user) {	
		return new Product(name, "descriptionProduct", duration,
				initialPrice,"shipmentInfo", category, user);
	}
	
	private User createUser (String userName, String password, String firstName, 
			String lastName, String email) {
		return new User(userName, password, firstName, lastName, email);
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
	public void testGetUserProducts() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser, DuplicateInstanceException {

		Category category1 = new Category("category1");
		
		categoryDao.save(category1);

		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		
		Product product1 = createProduct("Product 1", 120, new BigDecimal(10), category1, user1);
		
		productService.addProduct(user1.getId(), product1.getName(),
				product1.getDescriptionProduct(), product1.getDuration(), 
				product1.getInitialPrice(), product1.getShipmentInfo(), product1.getCategory().getId());
		
		bidService.createBid(user3.getId(), product1.getId(), new BigDecimal(50));
		Bid bid = bidService.createBid(user2.getId(), product1.getId(), new BigDecimal(60));
		
		Block<Product> productBlock = productService.getUserProducts(user1.getId());
		
		Bid winningBid = productBlock.getItems().get(0).getWinningBid();
		
		assertEquals(bid.getUser().getEmail(), winningBid.getUser().getEmail());
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
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 
				
		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(10));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12));
		
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(10.5));
		assertEquals(bid2.getState(), Bid.BidState.WINNING);
		assertEquals(bid1.getState(), Bid.BidState.LOST);
		
	}
	
	@Test
	public void testFirstBid() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		
		Product product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		
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
		
		Product product = createProduct("Producto", 120, new BigDecimal(10), category1, user1);
		productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(1000));
		bidService.createBid(user4.getId(), product.getId(), new BigDecimal(500));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(500.5));
		
	}
	
	@Test
	public void testPriceLowerThanIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");
		
		Product product = createProduct("Producto", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12.3));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(12.3));
		
	}
	
	@Test
	public void testIrregularIncrement() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		User user3 = signUpUser("user3");	
		User user4 = signUpUser("user4");
		
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		Bid bid1 = bidService.createBid(user2.getId(), product.getId(), new BigDecimal(12));
		Bid bid2 = bidService.createBid(user3.getId(), product.getId(), new BigDecimal(12.3));
		Bid bid3 = bidService.createBid(user4.getId(), product.getId(), new BigDecimal(15));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(12.8));
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
		
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

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
		User user4 = signUpUser("user4");
		
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(14));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(13.7));
		
		assertEquals(product.getCurrentPrice(), new BigDecimal(14));
	}
	
	//excepcion
	//El propietario del producto no puede pujar sobre él 
	@Test(expected = UnauthorizedBidException.class)
	public void testUnauthorizedBidException() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");	
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product= productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

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
		
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 

		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(15));
		bidService.createBid(user3.getId(), product.getId(), new BigDecimal(2));

	}
	
	@Test(expected = UnauthorizedWinningUser.class)
	public void testUnauthorizedWinningUser() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category1 = new Category("Category");
		categoryDao.save(category1);
		
		User user1 = signUpUser("user1");
		User user2 = signUpUser("user2");
		
		Product product = createProduct("Product", 120, new BigDecimal(10), category1, user1);
		product = productService.addProduct(user1.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category1.getId()); 
	
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(15));
		bidService.createBid(user2.getId(), product.getId(), new BigDecimal(20));

	}
	
	
	//Consultar las pujas realizadas por un usuario (getUserBids)
	@Test 
	public void testGetUserBids() throws InstanceNotFoundException, ExpiratedProductDateException, UnauthorizedBidException, InsufficientBidQuantityException, UnauthorizedWinningUser {
		Category category = new Category("Categoy");
		
		
		User user1 = createUser("Claudia", "password134!", "Claudia", "Alvarez", "c.alvarez@gmail.com");
		User user2 = createUser("Usuario2", "1324234", "Usuario", "Apellido", "usuario@udc.es");
		
		//El usuario 2 pone anuncio de 3 productos
		Product product = createProduct("Product", 120, new BigDecimal(10), category, user2);
		product = productService.addProduct(user2.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category.getId()); 

		Product product2 = createProduct("Product2", 120, new BigDecimal(10), category, user2);
		product2 = productService.addProduct(user2.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category.getId()); 
	
		Product product3 = createProduct("Product3", 120, new BigDecimal(10), category, user2);
		product3 = productService.addProduct(user2.getId(), "name", "reger",(long) 120, new BigDecimal(10), "pergv", category.getId()); 
	

		//El usuario1 puja por los tres productos
		bidService.createBid(user1.getId(), product.getId(), new BigDecimal(10));
		bidService.createBid(user1.getId(), product3.getId(), new BigDecimal(20));
		bidService.createBid(user1.getId(), product2.getId(), new BigDecimal(5));
	
		Block<Bid> block = bidService.getUserBids(user1.getId());
		
		//Muestra primero las pujas que les falta mas tiempo para que concluyan
		//Saldría el primer producto porque les falta mas tiempo
		assertEquals(product, block.getItems().get(0));
		
	}
	

}

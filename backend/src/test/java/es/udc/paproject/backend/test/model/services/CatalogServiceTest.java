package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.BidDao;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {
	
	private final Long NON_EXISTENT_ID = new Long(-1);
	
	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private BidService bidService;

	private Product createProduct(String name, long duration, LocalDateTime creationTime, float currentPrice, float initialPrice,
			Category category, User user) {	
		return new Product(name, "descriptionProduct", duration, creationTime, initialPrice,
				currentPrice, "shipmentInfo", category, user);
	}
	
	private User createUser (String userName, String password, String firstName, 
			String lastName, String email) {
		return new User(userName, password, firstName, lastName, email);
	}

	//Añadimos un producto
	@Test
	public void addProductTest() throws InstanceNotFoundException {
		
		Category category1 = new Category("category 1");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		
		Category category2 = new Category("category 2");
		User user2 = createUser("nombre", "2002125", "Pablo", "Rodriguez", "pablo@udc.es");

		
		
		Product product1 = createProduct("Product 1", 10,LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
				10, 10, category1, user1);
		Product product2 = createProduct("Product 2",10, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 
				12, 12, category2, user2);
		
		productDao.save(product1);
		productDao.save(product2);
			
		assertEquals(product1, catalogService.addProduct(product1.getId(), product1.getName(),
				product1.getDescriptionProduct(), product1.getDuration(), product1.getCreationTime(), product1.getInitialPrice(), 
				product1.getCurrentPrice(), product1.getShipmentInfo(), product1.getCategory()));
		
		
		assertEquals(product2, catalogService.addProduct(product2.getId(), product2.getName(),
				product2.getDescriptionProduct(), product2.getDuration(), product2.getCreationTime(), product2.getInitialPrice(), 
				product2.getCurrentPrice(), product2.getShipmentInfo(), product2.getCategory()));
		
	}
	
	//Buscar un producto por ID que no exista
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentProduct() throws InstanceNotFoundException {
		catalogService.findProducts(NON_EXISTENT_ID, "", 1, 1);
	}
	
	//Buscar el producto por Keywords
	@Test
	public void testFindProductsByKeywords() throws InstanceNotFoundException {
		
		Category category = new Category("category 1");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		Product product1 = createProduct("Product 1", 50,LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
				10, 10, category, user1);

		User user2 = createUser("nombre", "2002125", "Pablo", "Rodriguez", "pablo@udc.es");
		Product product2 = createProduct("Product X", 60,LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 
				12, 12, category, user2);
		
		User user3 = createUser("nombre", "2002125", "Juaneyer", "Reina", "juanreina@udc.es");
		Product product3 = createProduct("Another", 70, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 
				12, 12, category, user3);
		
		categoryDao.save(category);
		productDao.save(product1);
		productDao.save(product2);
		productDao.save(product3);
		
		Block<Product> expectedBlock = new Block<>(Arrays.asList(product1, product2), false);
		
		assertEquals(expectedBlock, catalogService.findProducts(null, "PrOdu", 0, 2));
		
	}
	
	//Buscar un producto un producto por categorias
	@Test
	public void testFindProductsByCategory() throws InstanceNotFoundException {
		
		Category category1 = new Category("category 1");
		Category category2 = new Category("category 2");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		Product product1 = createProduct("Product 1", 80, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
				10, 10, category1, user1);

		User user2 = createUser("nombre", "2002125", "Pablo", "Rodriguez", "pablo@udc.es");
		Product product2 = createProduct("Product X", 90, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 
				12, 12, category2, user2);
		
		
		categoryDao.save(category1);
		categoryDao.save(category2);
		productDao.save(product1);
		productDao.save(product2);
		
		Block<Product> expectedBlock = new Block<>(Arrays.asList(product1), false);
		
		assertEquals(expectedBlock, catalogService.findProducts(category1.getId(), null, 0, 1));
	}
	
	//Si buscamos producto y no ponemos ni keywords ni categoria nos muestra todos los productos
	@Test
	public void testFindAllProducts() throws InstanceNotFoundException {
		
		Category category1 = new Category("category 1");
		Category category2 = new Category("category 2");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		Product product1 = createProduct("Product 1", 100, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
				10, 10, category1, user1);

		User user2 = createUser("nombre", "2002125", "Pablo", "Rodriguez", "pablo@udc.es");
		Product product2 = createProduct("Product X", 101, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 
				12, 12, category2, user2);
		
		categoryDao.save(category1);
		categoryDao.save(category2);
		productDao.save(product1);
		productDao.save(product2);
		
		Block<Product> expectedBlock = new Block<>(Arrays.asList(product1, product2), false);
		
		assertEquals(expectedBlock, catalogService.findProducts(null, null, 0, 2));
		assertEquals(expectedBlock, catalogService.findProducts(null, "", 0, 2));

	}
	
	
	//Obtener las categorias
	@Test 
	public void testGetCategories() {
		
		Category category1 = new Category("category1");
		Category category2 = new Category("category2");
		
		categoryDao.save(category1);
		categoryDao.save(category2);
		
		assertEquals(category1, catalogService.getCategories(category1));
		assertEquals(category2, catalogService.getCategories(category2));
	}
	
	 
	
	//Obtener el detalle de los productos
	@Test
	public void testGetProductDetail() {
		Category category1 = new Category("category 1");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		Product product1 = createProduct("Product 1", 200, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
				10, 10, category1, user1);
		
		productDao.save(product1);
		
		Product expectedProduct = catalogService.getProductDetail(product1.getId());
		
		assertEquals(product1, expectedProduct);
		assertEquals(product1.getName(), expectedProduct.getName());
		assertEquals(product1.getCategory(), expectedProduct.getCategory());
		assertEquals(product1.getDescriptionProduct(), expectedProduct.getDescriptionProduct());
		
	}
	
	//Consultar los productos anunciados 
	@Test
	public void testGetUserProducts() throws InstanceNotFoundException {
		
		Category category1 = new Category("category 1");
		Category category2 = new Category("category 2");
		
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		Product product1 = createProduct("Product 1", 120, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 10, 10, category1, user1);
		Product product2 = createProduct("Product X", 110, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 12, 12, category2, user1);
		
		categoryDao.save(category1);
		categoryDao.save(category2);
		
		catalogService.addProduct(product1.getId(), product1.getName(),
				product1.getDescriptionProduct(), product1.getDuration(), product1.getCreationTime(), product1.getInitialPrice(), 
				product1.getCurrentPrice(), product1.getShipmentInfo(), product1.getCategory());
		
		catalogService.addProduct(product2.getId(), product2.getName(),
				product2.getDescriptionProduct(), product2.getDuration(), product2.getCreationTime(), product2.getInitialPrice(), 
				product2.getCurrentPrice(), product2.getShipmentInfo(), product2.getCategory());
		
		Block<Product> catalogS = catalogService.getUserProducts(user1.getId());
		
		assertEquals(product1, catalogS.getItems().get(0));
	}
	
	@Test
	public void testGetUserProductsEmail() throws InstanceNotFoundException, ExpiratedProductDateException {

		Category category1 = new Category("category 1");
		
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		User user2 = createUser("Pablo", "Mara123", "Pablo", "Regueiro", "super10pablo@udc.es");
		User user3 = createUser("Juan", "Mara123", "Juan", "Regueiro", "matriculas@udc.es");
		
		Product product1 = createProduct("Product 1", 120, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 10, 10, category1, user1);
		
		
		catalogService.addProduct(product1.getId(), product1.getName(),
				product1.getDescriptionProduct(), product1.getDuration(), product1.getCreationTime(), product1.getInitialPrice(), 
				product1.getCurrentPrice(), product1.getShipmentInfo(), product1.getCategory());
		
		bidService.createBid(user3.getId(), product1.getId(), (float)50);
		bidService.createBid(user2.getId(), product1.getId(), (float)60);
		
		Block<Product> catalogS = catalogService.getUserProducts(user1.getId());
		
		Optional<Bid> bidGanadora = catalogS.getItems().get(0).getWinningBid();
		
		assertEquals(bidService.getUserBids(user2.getId()), bidGanadora.get().getUser().getEmail());
		
		
	}
}
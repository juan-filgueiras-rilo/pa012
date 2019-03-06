package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.CatalogService;

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

	private Product createProduct(String name, long bidTime, float initialPrice,
			Category category, User user) {	
		return new Product(name, "descriptionProduct", bidTime, initialPrice,
				"shipmentInfo", category, user);
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

		
		
		Product product1 = createProduct("Product 1", 180, 10, category1, user1);
		Product product2 = createProduct("Product 2", 240, 12, category2, user2);
		
		productDao.save(product1);
		productDao.save(product2);
		
		assertEquals(product1, catalogService.addProduct(product1.getId(), product1));
		assertEquals(product2, catalogService.addProduct(product2.getId(), product2));
		
	}
	
	//Insertar un producto que no existe
	@Test (expected = InstanceNotFoundException.class)
	public void addProductTestException() throws InstanceNotFoundException {
		Category category1 = new Category("category 1");
		User user1 = createUser("juanluispm", "123456", "Juan", "Boquete", "juan@udc.es");
		
		Product product = createProduct("Product 1", 180, 10, category1, user1);

		catalogService.addProduct(NON_EXISTENT_ID, product);
	}
	
	//Buscar el producto por ID
	@Test
	public void testFindProducts() {
		
	}
	
	//Buscar un producto por ID y por keywords
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
	
}

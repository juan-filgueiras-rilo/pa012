package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;

public class BidServiceTest {
	
	private final Long NON_EXISTENT_ID = new Long(-1);
	
	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
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
	
	//Intentar hacer una puja con un producto que no exite
	@Test(expected = InstanceNotFoundException.class)
	public void testNotFoundException() throws InstanceNotFoundException, ExpiratedProductDateException {
		bidService.createBid(NON_EXISTENT_ID, null, null);
	}
	
	//Intentar hacer una puja de un producto que ya ha expirado
	@Test(expected=ExpiratedProductDateException.class)
	public void testExpirateProductException() throws InstanceNotFoundException, ExpiratedProductDateException {
		
		Category category1 = new Category("Category");
		User user = createUser("Carmen", "password134!", "Carmen", "Fernandez", "c.fernandez@gmail.com");
		
		Product producto = createProduct("Producto", 120, LocalDateTime.of(2000, 1, 1, 10, 20), 10, 10, category1, user);
		
		bidService.createBid(user.getId(), producto.getId(), (float) 1);
		
	}
	//Hacer test para cada caso del createBid
	
	//Consultar las pujas realizadas por un usuario (getUserBids)
	@Test 
	public void testGetUserBids() throws InstanceNotFoundException, ExpiratedProductDateException {
		Category category = new Category("Categoy");
		
		
		User usuario = createUser("Claudia", "password134!", "Claudia", "Alvarez", "c.alvarez@gmail.com");
		User usuario2 = createUser("Usuario2", "1324234", "Usuario", "Apellido", "usuario@udc.es");
		
		//El usuario 2 pone anuncio de 3 productos
		Product producto1 = createProduct("Producto 1", 120, LocalDateTime.now(), 10, 10, category, usuario2);
		Product producto2 = createProduct("Producto 2", 110, LocalDateTime.now(), 10, 10, category, usuario2);
		Product producto3 = createProduct("Producto 3", 100, LocalDateTime.now(), 10, 10, category, usuario2);

		//El usuario1 puja por los tres productos
		bidService.createBid(usuario.getId(), producto1.getId(), (float) 10);
		bidService.createBid(usuario.getId(), producto2.getId(), (float) 20);
		bidService.createBid(usuario.getId(), producto3.getId(), (float) 5);
	
		Block<Bid> bidS = bidService.getUserBids(usuario.getId());
		
		//Muestra primero las pujas que les falta mas tiempo para que concluyan
		//Saldría el primer producto porque les falta mas tiempo
		assertEquals(producto1, bidS.getItems().get(0));
		
	}
	

}

package es.udc.paproject.backend.test.model.quickcheck;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;
import es.udc.paproject.backend.model.services.CatalogServiceImpl;
import es.udc.paproject.backend.model.services.PermissionChecker;

@RunWith(JUnitQuickcheck.class)
public class BackendProperties {
    
	@Mock
	private ProductDao productDao;
	
	@Mock
	private CategoryDao categoryDao;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PermissionChecker permissionChecker;
	
	@InjectMocks
	private CatalogServiceImpl catalogService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	Category CATEGORY = new Category("cat");
	User USER = new User("user", "password", "fn", "ln","user@user.com");
	
	@Before
	public void setup() throws InstanceNotFoundException {
		USER.setId(1L);
		CATEGORY.setId(1L);
		MockitoAnnotations.initMocks(this);
		when(categoryDao.findById(1L)).thenReturn(Optional.of(CATEGORY));
		when(categoryDao.findById(0L)).thenReturn(Optional.empty());
		when(userDao.findById(1L)).thenReturn(Optional.of(USER));
		when(permissionChecker.checkUser(0L)).thenThrow(new InstanceNotFoundException("", ""));
		when(productDao.save(any(Product.class))).thenAnswer(new Answer<Product>() {

		    public Product answer(InvocationOnMock invocation) {
			    Product product = (Product) invocation.getArgument(0);

		        product.setId(1L);
		    	return product;
		    }
		});
		when(productDao.findById(any(Long.class))).thenAnswer(new Answer<Product>() {

		    public Product answer(InvocationOnMock invocation) {
			    Product product = (Product) invocation.getArgument(0);

		        product.setId(1L);
		    	return product;
		    }
		});
	}
	
	@Property(trials=1000)
	public void addProductTest(@From(ProductGenerator.class) Product product) throws InstanceNotFoundException {
		if(product.getCategory().getId() <= 0) {
			exception.expect(InstanceNotFoundException.class);
			catalogService.addProduct(product.getUser().getId(), product.getName(), product.getDescriptionProduct(), product.getDuration(), product.getInitialPrice(), product.getShipmentInfo(), product.getCategory().getId());
		}
		if(product.getUser().getId() <= 0) {
			exception.expect(InstanceNotFoundException.class);
			catalogService.addProduct(product.getUser().getId(), product.getName(), product.getDescriptionProduct(), product.getDuration(), product.getInitialPrice(), product.getShipmentInfo(), product.getCategory().getId());
		}
		Long id = catalogService.addProduct(product.getUser().getId(), product.getName(), product.getDescriptionProduct(), product.getDuration(), product.getInitialPrice(), product.getShipmentInfo(), product.getCategory().getId());
		assertEquals(id,(Long)1L);
	}
	
}
package jetm;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;
import es.udc.paproject.backend.model.services.CatalogServiceImpl;
import es.udc.paproject.backend.model.services.PermissionChecker;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class OneMinuteTest {

	private static EtmMonitor etmMonitor;

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
	Product PRODUCT = new Product("NOMBRE", "DESCRIPCION", 10L, new BigDecimal(10), "SHIPMENTINFO", CATEGORY, USER);

	@Before
	public void setupMocks() throws InstanceNotFoundException {
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
		when(productDao.findById(any(Long.class))).thenReturn(Optional.of(PRODUCT));
		when(productDao.findByUserIdOrderByEndDateDesc(any(Long.class), any(PageRequest.class))).thenReturn(new SliceImpl<>(new ArrayList<Product>(),PageRequest.of(0, 10), true));
	}
	
	@Test
	public void test() throws InstanceNotFoundException {
		// configure measurement framework
		setup();

		// execute business logic

		EtmPoint addProductPoint = etmMonitor.createPoint("CatalogService:addProduct");
		catalogService.addProduct(1L, "NOMBRE", "DESCRIPCION", 10L, new BigDecimal(10), "SHIPMENTINFO", 1L);
		addProductPoint.collect();
		
		EtmPoint getDetailsPoint = etmMonitor.createPoint("CatalogService:getProductDetails");
		catalogService.getProductDetail(1L);
		getDetailsPoint.collect();
		
		EtmPoint getUserProductsPoint = etmMonitor.createPoint("CatalogService:getUserProducts");
		catalogService.getUserProducts(1L, 0, 10);
		getUserProductsPoint.collect();
		
		// visualize results
		etmMonitor.render(new SimpleTextRenderer());

		// shutdown measurement framework
		tearDown();
	}

	private static void setup() {
		BasicEtmConfigurator.configure();
		etmMonitor = EtmManager.getEtmMonitor();
		etmMonitor.start();
	}

	private static void tearDown() {
		etmMonitor.stop();
	}

}
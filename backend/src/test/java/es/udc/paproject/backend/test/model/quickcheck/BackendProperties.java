package es.udc.paproject.backend.test.model.quickcheck;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
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
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Property(trials=1000)
	public void addProductTest(@From(ProductGenerator.class) Product params) throws InstanceNotFoundException {
		
		if(params.getCategory().getId() <= 0) {
			exception.expect(InstanceNotFoundException.class);
			catalogService.addProduct(params.getUser().getId(), params.getName(), params.getDescriptionProduct(), params.getDuration(), params.getInitialPrice(), params.getShipmentInfo(), params.getCategory().getId());
		}
		if(params.getUser().getId() <= 0) {
			exception.expect(InstanceNotFoundException.class);
			catalogService.addProduct(params.getUser().getId(), params.getName(), params.getDescriptionProduct(), params.getDuration(), params.getInitialPrice(), params.getShipmentInfo(), params.getCategory().getId());
		}
	}
}
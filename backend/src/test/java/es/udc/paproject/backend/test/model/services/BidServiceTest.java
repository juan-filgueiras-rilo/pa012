package es.udc.paproject.backend.test.model.services;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.services.CatalogService;

public class BidServiceTest {
	
	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CatalogService catalogService;
	
	

}

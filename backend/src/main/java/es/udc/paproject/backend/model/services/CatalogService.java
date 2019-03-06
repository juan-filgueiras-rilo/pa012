package es.udc.paproject.backend.model.services;

import java.util.HashSet;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;

public interface CatalogService {
		
	Product addProduct(Long id, String name, String descriptionProduct, Long bidTime, Float initialPrice,
			String shipmentInfo, Category category) throws InstanceNotFoundException;
	
	Block<Product> findProducts(Long id, String keywords, int page, int size);
	
	HashSet<Category> getCategories(Category category);
	
	Product getProductDetail(Long productId);

}

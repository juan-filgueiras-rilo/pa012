package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.util.List;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;

public interface ProductService {
		
	Product addProduct(Long userId, String name, String descriptionProduct, Long duration, BigDecimal initialPrice, String shipmentInfo,
			Long categoryId) throws InstanceNotFoundException;
	
	Block<Product> findProducts(Long categoryId, String keywords, int page, int size);
	
	List<Category> findAllCategories();
	
	Product getProductDetail(Long productId) throws InstanceNotFoundException;
	
	Block<Product> getUserProducts(Long userId, int page, int size) throws InstanceNotFoundException;
}

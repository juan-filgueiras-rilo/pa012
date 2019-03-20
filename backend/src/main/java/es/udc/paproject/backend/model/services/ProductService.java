package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;

public interface ProductService {
		
	Product addProduct(Long id, String name, String descriptionProduct, Long duration, LocalDateTime creationTime, BigDecimal initialPrice,
			String shipmentInfo, Category category) throws InstanceNotFoundException;
	
	Block<Product> findProducts(Long id, String keywords, int page, int size) throws InstanceNotFoundException;
	
	HashSet<Category> getCategories(Category category);
	
	Product getProductDetail(Long productId);
	
	Block<Product> getUserProducts(Long userId) throws InstanceNotFoundException;

}
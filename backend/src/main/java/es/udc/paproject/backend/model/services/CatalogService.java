package es.udc.paproject.backend.model.services;

import java.util.HashSet;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public interface CatalogService {
	
	Product addProduct(Long id, User products);
	
	HashSet<Product> findProducts(String keywords, Category category);
	
	HashSet<Category> getCategories(Category category);
	
	Product getProductDetail(Product product);		

}

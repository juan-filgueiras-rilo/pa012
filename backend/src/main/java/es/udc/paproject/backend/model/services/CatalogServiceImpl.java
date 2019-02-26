package es.udc.paproject.backend.model.services;

import java.util.HashSet;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public class CatalogServiceImpl implements CatalogService{

	@Override
	public Product addProduct(User userName, User products, User bids) {
		
	}

	@Override
	public HashSet<Product> findProducts(String keywords, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<Category> getCategories(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductDetail(Product product) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

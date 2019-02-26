package es.udc.paproject.backend.model.services;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	

	/*@Override
	public Product addProduct(Long id, User products) {
		
		User user = permissionChecker.checkUserExists(id);
	}*/
	

	@Override
	public HashSet<Product> findProducts(String keywords, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<Category> getCategories(Category category) {

		Iterable<Category> categories = categoryDao.findAll(new Sort(Sort.Direction.ASC, "name"));
		HashSet<Category> categoriesAsList = new HashSet<>();
		
		categories.forEach(c -> categoriesAsList.add(c));
		
		return categoriesAsList;
	}

	@Override
	public Product getProductDetail(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product addProduct(Long id, User products) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}

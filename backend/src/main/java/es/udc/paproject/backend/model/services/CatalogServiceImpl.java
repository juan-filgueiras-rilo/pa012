package es.udc.paproject.backend.model.services;

import java.util.HashSet;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Product addProduct(Long id, String name, String descriptionProduct, Long bidTime, Float initialPrice, String shipmentInfo, Category category) throws InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(id);
		Product product = new Product(id, name, descriptionProduct, bidTime, initialPrice, shipmentInfo, category, user);
		
		product = productDao.save(product);
		return product;
	}

	@Override
	public Block<Product> findProducts(Long id, String keywords, int page, int size) {
		
		Slice<Product> slice = productDao.find(id, keywords, page, size);
		
		return new Block<>(slice.getContent(), slice.hasNext());
	}

	@Override
	public HashSet<Category> getCategories(Category category) {

		Iterable<Category> categories = categoryDao.findAll(new Sort(Sort.Direction.ASC, "name"));
		HashSet<Category> categoriesAsList = new HashSet<>();
		
		categories.forEach(c -> categoriesAsList.add(c));
		
		return categoriesAsList;
	}

	@Override
	public Product getProductDetail(Long productId) {
		 
		Optional<Product> optProduct;
		
		optProduct = productDao.findById(productId);
		
		return optProduct.get();
	}
	
}

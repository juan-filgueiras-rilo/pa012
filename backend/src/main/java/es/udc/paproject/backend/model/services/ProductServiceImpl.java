package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Product addProduct(Long userId, String name, String descriptionProduct, Long duration, LocalDateTime creationTime, BigDecimal initialPrice, String shipmentInfo, Category category) throws InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(userId);
		Product product = new Product(name, descriptionProduct, duration, creationTime, initialPrice,  shipmentInfo, category, user);
		
		product = productDao.save(product);
		return product;
	}

	@Override
	public Block<Product> findProducts(Long categoryId, String keywords, int page, int size) throws InstanceNotFoundException {
		
		Slice<Product> slice = productDao.find(categoryId, keywords, page, size);
		
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

	@Override
	public Block<Product> getUserProducts(Long userId) throws InstanceNotFoundException {
		
		permissionChecker.checkUserExists(userId);
		
		Slice<Product> slice = productDao.findProductsByUserIdOrderByCreationTimeDesc(userId);
		
		return new Block<>(slice.getContent(), slice.hasNext());
	
	}
	
}

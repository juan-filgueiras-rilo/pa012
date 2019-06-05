package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Long addProduct(Long userId, String name, 
			String descriptionProduct, Long duration, 
			BigDecimal initialPrice, String shipmentInfo,
			Long categoryId) 
			throws InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(userId);
		Optional<Category> optCategory = categoryDao.findById(categoryId);
		if (!optCategory.isPresent()) {
			throw new InstanceNotFoundException("Category not found with id: ", categoryId);
		}
		Product product = new Product(name, descriptionProduct, duration, initialPrice,  shipmentInfo, optCategory.get(), user);
		
		product.setWinningUserEmail("hola");
		
		product = productDao.save(product);
		return product.getId();
	}

	@Override
	@Transactional(readOnly=true)
	public Block<Product> findProducts(Long categoryId, String keywords, int page, int size) {
		
		Slice<Product> slice = productDao.find(categoryId, keywords, page, size);
		
		return new Block<>(slice.getContent(), slice.hasNext());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Category> findAllCategories() {

		Iterable<Category> categories = categoryDao.findAll(new Sort(Sort.Direction.ASC, "name"));
		List<Category> categoriesAsList = new ArrayList<>();
		
		categories.forEach(c -> categoriesAsList.add(c));
		
		return categoriesAsList;
	}

	@Override
	@Transactional(readOnly=true)
	public Product getProductDetail(Long id) throws InstanceNotFoundException {
		 
		Optional<Product> optProduct;
		
		optProduct = productDao.findById(id);
		
		if (!optProduct.isPresent()) {
			throw new InstanceNotFoundException("project.entities.product", id);
		}
		
		return optProduct.get();
	}

	@Override
	@Transactional(readOnly=true)
	public Block<Product> getUserProducts(Long userId, int page, int size) throws InstanceNotFoundException {
		
		permissionChecker.checkUserExists(userId);
		
		Slice<Product> slice = productDao.findByUserIdOrderByEndDateDesc(userId, PageRequest.of(page, size));
		
		return new Block<>(slice.getContent(), slice.hasNext());
	
	}
	
}

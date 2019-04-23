package es.udc.paproject.backend.model.entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;



public class CustomizedProductDaoImpl implements CustomizedProductDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Slice<Product> find(Long categoryId, String text, int page, int size) {
		
		String[] keywords = text == null ? new String[0] : text.split("\\s");
		String queryString = "SELECT p FROM Product p";
		
		//queryString += " WHERE endDate";
		
		if (categoryId != null || keywords.length > 0) {
			queryString += " WHERE ";
		}
		
		if (categoryId != null) {
			queryString += "p.category.id = :categoryId";
		}
		
		if (keywords.length != 0) {
			
			if (categoryId != null) {
				queryString += " AND ";
			}
			
			for (int i = 0; i<keywords.length-1; i++) {
				queryString += "LOWER(p.name) LIKE LOWER(:keyword" + i + ") AND ";
			}
			
			queryString += "LOWER(p.name) LIKE LOWER(:keyword" + (keywords.length-1) + ")";
			
		}
		
		queryString += " ORDER BY p.name";
		
		Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);
		
		if (categoryId != null) {
			query.setParameter("categoryId", categoryId);
		}
		
		if (keywords.length != 0) {
			for (int i = 0; i<keywords.length; i++) {
				query.setParameter("keyword" + i, '%' + keywords[i] + '%');
			}
	
		}
		
		List<Product> products = query.getResultList();
		boolean hasNext = products.size() == (size+1);
		
		if (hasNext) {
			products.remove(products.size()-1);
		}
		
		return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
		
	}
	

}

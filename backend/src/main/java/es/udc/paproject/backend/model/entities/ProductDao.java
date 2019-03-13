package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, Long>, CustomizedProductDao {

	Slice<Product> findProductsByUserIdOrderByCreationTimeDesc(Long userId);
}

package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, Long>, CustomizedProductDao {

	Slice<Product> findByUserIdOrderByEndDateDesc(Long userId, Pageable pageable);
}

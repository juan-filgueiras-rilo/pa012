package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedProductDao {
	
	Slice<Product> find(Long id, String text, int page, int size);

}

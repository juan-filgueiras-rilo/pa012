package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BidDao extends PagingAndSortingRepository<Bid, Long> {

	public Slice<Bid> findByUserId(Long userId,Pageable pageable);
}

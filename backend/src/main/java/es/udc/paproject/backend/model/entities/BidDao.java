package es.udc.paproject.backend.model.entities;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.paproject.backend.model.entities.Bid.BidState;

public interface BidDao extends PagingAndSortingRepository<Bid, Long> {

	public Slice<Bid> findByUserId(Long userId,Pageable pageable);
}

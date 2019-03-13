package es.udc.paproject.backend.model.entities;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.paproject.backend.model.entities.Bid.BidState;

public interface BidDao extends PagingAndSortingRepository<Bid, Long> {

	public Optional<Bid> findByState(BidState state);
	public Product findByStateAndProduct(BidState state, Product product);
}

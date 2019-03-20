package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;

public interface BidService {
	
	Bid createBid(Long id, Long productId, BigDecimal quantity) throws ExpiratedProductDateException, InstanceNotFoundException;
	
	Block<Bid> getUserBids(Long userId) throws InstanceNotFoundException;
	
}

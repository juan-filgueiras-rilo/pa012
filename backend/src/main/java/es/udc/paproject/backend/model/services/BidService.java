package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Product;

public interface BidService {
	
	Bid createBid(Long id, Long productId, Float quantity) throws ExpiratedProductDateException, InstanceNotFoundException;
	
	Block<Bid> getUserBids(Long userId) throws InstanceNotFoundException;
	
}

package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Product;

public interface BidService {
	
	Bid findBid(Long id, Bid bid);
	Bid createBid(Long id, Long productId, Float quantity) throws ExpiratedBidDateException, InstanceNotFoundException;
}

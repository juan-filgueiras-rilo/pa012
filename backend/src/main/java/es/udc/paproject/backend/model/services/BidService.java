package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Product;

public interface BidService {
	
	Bid createBid(Long id, Product product, Bid bid) throws ExpiratedBidDateException, InstanceNotFoundException;
	
	
	Bid findBid(Long id, Bid bid);
	
	
	

}

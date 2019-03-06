package es.udc.paproject.backend.model.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.BidDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;

public class BidServiceImpl implements BidService {

	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BidDao bidDao;
	
	
	@Override
	public Bid createBid(Long id, Product product, Bid bid) throws ExpiratedBidDateException, InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(id);		
		
		if (LocalDateTime.now().isAfter(bid.getDate())) {
			throw new ExpiratedBidDateException(id);
		}
		
		bidDao.save(bid);
		
		
		if(product.getUser() == user) {
			throw new 
		}
		/*Comparamos con el precio inicial que sea mayor
		 * Un señor no puede pujar sobre si mismo
		 * Fecha expirada que ya la tenemos*/
		product.addBid(bid);
		
		
		
		
		
		return null;
	}

	@Override
	public Bid findBid(Long id, Bid bid) {
		// TODO Auto-generated method stub
		return null;
	}

}

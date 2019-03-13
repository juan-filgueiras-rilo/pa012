package es.udc.paproject.backend.model.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Bid.BidState;
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
	public Bid createBid(Long id, Long productId, Float quantity) throws ExpiratedBidDateException, InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(id);		
		Optional<Product> optProduct = productDao.findById(productId);
		Product product;
		Bid bid, winningBid;
		
		try {
			product = optProduct.get();
		} catch (NoSuchElementException e) {
			throw new InstanceNotFoundException("Product not found with id: ", productId);
		}
		
		if (!product.isActive()) {
			throw new ExpiratedBidDateException(id);
		}
		
		if(product.getUser() == user) {
			//throw new 
		}

		bid = new Bid(quantity, BidState.WINNING, user, product);

		Optional<Bid> optWinningBid = bidDao.findByState(BidState.WINNING);
		if (optWinningBid.isPresent()) {
			
			winningBid = optWinningBid.get();
			Float productCurrentPrice = product.getCurrentPrice();
			Float winningQuantity = winningBid.getQuantity();
			
			if (bid.getQuantity() > (productCurrentPrice+0.5)) {
				if (bid.getQuantity() > winningQuantity) { 
					winningBid.setState(BidState.LOST);
					bid.getProduct().setCurrentPrice(winningQuantity+(float)0.5);
				} else {
					bid.setState(BidState.LOST);
					productCurrentPrice = bid.getQuantity()+(float)0.5;
				}
			//TODO si pujo lo mismo?!?!??!?!?!?!?!?!??!?!
			} else if (bid.getQuantity() >= productCurrentPrice) {
				if (bid.getQuantity() > winningQuantity) {
					winningBid.setState(BidState.LOST);
					bid.getProduct().setCurrentPrice(quantity);
				} else {
					bid.setState(BidState.LOST);
					productCurrentPrice = bid.getQuantity()+(float)0.5;
				}
			} else {
				bid.setState(BidState.LOST);
			}
		}
		
		//SI NO HAY PUJAS - SE COMPRUEBA CON EL PRECIO MINIMO?

		//Update se hace solo.
	
		if (bid.getState()==BidState.WINNING || (bid.getState()==BidState.LOST && bid.getQuantity()>product.getCurrentPrice())) {
			bidDao.save(bid);
		}
		//Cambiar estado bids y buscar la bid que va ganando para poner que ha perdido 
		/*Comparamos con el precio inicial que sea mayor
		 * Un señor no puede pujar sobre si mismo
		 * Fecha expirada que ya la tenemos*/
		
		//tiempo que dura la puja pero cuando empieza/acaba?
		//version en prod
		//que puja gana? como era esto? lo tengo anotado estado den puja
		
		return null;
	}

	@Override
	public Bid findBid(Long id, Bid bid) {
		// TODO Auto-generated method stub
		return null;
	}

}

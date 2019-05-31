package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.BidDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductDao;
import es.udc.paproject.backend.model.entities.User;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private BidDao bidDao;
	
	@Override
	public Bid createBid(Long userId, Long productId, BigDecimal quantity) 
			throws ExpiratedProductDateException, InstanceNotFoundException,
			UnauthorizedBidException, InsufficientBidQuantityException,
			UnauthorizedWinningUserException {
		
		User user = permissionChecker.checkUser(userId);		
		Optional<Product> optProduct;
		Product product;
		Bid newBid, winningBid;
		String winningUserEmail;
		
		optProduct = productDao.findById(productId);
		try {
			product = optProduct.get();
		} catch (NoSuchElementException e) {
			throw new InstanceNotFoundException("Product not found with id: ", productId);
		}
		
		if (!product.isActive()) {
			throw new ExpiratedProductDateException(productId);
		}
		
		if(product.getUser() == user) {
			//Si eres el propietario del producto no puedes pujar sobre él
			//Tb sirve para que no puedas pujar sobre tu propia puja
			throw new UnauthorizedBidException(productId);
		}		

		newBid = new Bid(quantity, user, product);
		
		if (quantity.compareTo(product.getMinPrice()) == -1) {
			throw new InsufficientBidQuantityException(product.getMinPrice().setScale(2, RoundingMode.HALF_EVEN).doubleValue());
		}
		
		Bid optWinningBid = product.getWinningBid();
		String optWinningUserEmail = product.getWinningBid().getUser().getEmail();
		if (optWinningBid != null) {
			winningBid = optWinningBid;
			winningUserEmail = optWinningUserEmail;
			if (winningBid.getUser() == user) {
				throw new UnauthorizedWinningUserException(winningBid.getId());
			}
			
			BigDecimal winningQuantity = winningBid.getQuantity();
			BigDecimal newQuantity = newBid.getQuantity();

			
			if (newQuantity.compareTo(winningQuantity) == 1) { 
//					winningBid.setState(BidState.LOST);
				product.setWinningBid(newBid);
				if(newQuantity.compareTo(winningQuantity.add(new BigDecimal(0.5))) == 1) {
					product.setCurrentPrice(winningQuantity.add(new BigDecimal(0.5)));
				} else {
					product.setCurrentPrice(newQuantity);
				}
			} else {
//					newBid.setState(BidState.LOST);
				//BigDecimal newPrice = newBid.getQuantity().min(newBid.getQuantity().add(new BigDecimal(0.5)));
				BigDecimal newPrice = newBid.getQuantity().add(new BigDecimal(0.5));
				
				if(winningQuantity.compareTo(newPrice) == 1) {
					product.setCurrentPrice(newPrice);
				} else {
					product.setCurrentPrice(winningQuantity);
				}
			}
		} else {
			product.setWinningBid(newBid);
			product.setWinningUserEmail(optWinningUserEmail);
		}
		
		bidDao.save(newBid);

		return newBid;
	}

	@Override
	public Block<Bid> getUserBids(Long userId, int page, int size) throws InstanceNotFoundException {

		permissionChecker.checkUserExists(userId);
		
		Slice<Bid> slice = bidDao.findByUserIdOrderByDateDesc(userId, PageRequest.of(page, size));
		
		return new Block<>(slice.getContent(), slice.hasNext());
	}

}

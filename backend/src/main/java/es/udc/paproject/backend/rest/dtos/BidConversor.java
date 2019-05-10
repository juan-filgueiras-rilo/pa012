package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Product;

public class BidConversor {

	public final static BidProductDto toBidProductDto(Bid bid) {
		Product product = bid.getProduct();
		
		return new BidProductDto(product.getRemainingTime(), product.getCurrentPrice(),
				product.getMinPrice(), bid.getBidStatus());
	}
	
	public final static List<BidDto> toBidDtos(List<Bid> bids) {
		return bids.stream().map(p -> toBidDto(p)).collect(Collectors.toList());
	}
	
	private final static BidDto toBidDto(Bid bid) {
		
		return new BidDto(bid.getQuantity(), bid.getProduct().getName(), 
				bid.getProduct().getId(), bid.getBidStatus(), toMillis(bid.getDate()));
	}
	
	private final static Long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}
}

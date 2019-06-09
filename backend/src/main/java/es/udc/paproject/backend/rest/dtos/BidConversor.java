package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Bid;

public class BidConversor {

	public final static BidProductDto toBidProductDto(Bid bid) {		
		return new BidProductDto(bid.getProduct().getRemainingTime(), bid.getProduct().getCurrentPrice(),
				bid.getProduct().getMinPrice(), bid.getBidStatus());
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

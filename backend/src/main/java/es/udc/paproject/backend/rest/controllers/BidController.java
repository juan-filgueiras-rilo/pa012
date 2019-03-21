package es.udc.paproject.backend.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;
import es.udc.paproject.backend.model.services.InsufficientBidQuantityException;
import es.udc.paproject.backend.model.services.UnauthorizedBidException;
import es.udc.paproject.backend.model.services.UnauthorizedWinningUser;
import es.udc.paproject.backend.rest.dtos.BidDetailDto;
import es.udc.paproject.backend.rest.dtos.BidDto;
import es.udc.paproject.backend.rest.dtos.BidParamsDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;

import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidDtos;
import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidDetailDto;

@RestController
@RequestMapping("/bid")
public class BidController {

	@Autowired
	private BidService bidService;
	
	@PostMapping("/bids")
	public BidDetailDto createBid(@RequestAttribute Long userId, 
			@Validated @RequestBody BidParamsDto params) 
			throws ExpiratedProductDateException, InstanceNotFoundException,
			UnauthorizedBidException, InsufficientBidQuantityException,
			UnauthorizedWinningUser {
		
		return toBidDetailDto(bidService.createBid(userId, 
				params.getProductId(), params.getQuantity()));
	}
	
	@PostMapping("/bids")
	public BlockDto<BidDto> getUserBids(@RequestAttribute Long userId) 
			throws InstanceNotFoundException {
		
		Block<Bid> bidBlock = bidService.getUserBids(userId);
		
		return new BlockDto<>(toBidDtos(bidBlock.getItems()),bidBlock.getExistMoreItems());
	}
}

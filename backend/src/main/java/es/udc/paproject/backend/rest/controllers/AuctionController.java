package es.udc.paproject.backend.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.services.AuctionService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ExpiratedProductDateException;
import es.udc.paproject.backend.model.services.InsufficientBidQuantityException;
import es.udc.paproject.backend.model.services.UnauthorizedBidException;
import es.udc.paproject.backend.model.services.UnauthorizedWinningUserException;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.BidProductDto;
import es.udc.paproject.backend.rest.dtos.BidDto;
import es.udc.paproject.backend.rest.dtos.BidParamsDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;

import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidDtos;

import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidProductDto;

@RestController
@RequestMapping("/auction")
public class AuctionController {

	private final static String EXPIRATED_PRODUCT_DATE_EXCEPTION_CODE = "project.exceptions.ExpiratedProductDateException";
	private final static String UNAUTHORIZED_BID_EXCEPTION_CODE = "project.exceptions.UnauthorizedBidException";
	private final static String INSUFFICIENT_BID_QUANTITY_EXCEPTION_CODE = "project.exceptions.InsufficientBidQuantityException";
	private final static String UNAUTHORIZED_WINNING_USER_EXCEPTION_CODE = "project.exceptions.UnauthorizedWinningUserException";

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuctionService auctionService;
	
	@ExceptionHandler(ExpiratedProductDateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleExpiratedProductDateException(ExpiratedProductDateException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(EXPIRATED_PRODUCT_DATE_EXCEPTION_CODE,
			new Object[] {exception.getId()}, EXPIRATED_PRODUCT_DATE_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(UnauthorizedBidException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handleUnauthorizedBidException(UnauthorizedBidException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(UNAUTHORIZED_BID_EXCEPTION_CODE,
			new Object[] {exception.getId()}, UNAUTHORIZED_BID_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(InsufficientBidQuantityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleInsufficientBidQuantityException(InsufficientBidQuantityException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INSUFFICIENT_BID_QUANTITY_EXCEPTION_CODE,
			new Object[] {exception.getQuantity()}, INSUFFICIENT_BID_QUANTITY_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(UnauthorizedWinningUserException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handleUnauthorizedWinningUser(UnauthorizedWinningUserException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(UNAUTHORIZED_WINNING_USER_EXCEPTION_CODE,
			new Object[] {exception.getId()}, UNAUTHORIZED_WINNING_USER_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@PostMapping("/bids")
	public BidProductDto createBid(@RequestAttribute Long userId, 
			@Validated @RequestBody BidParamsDto params) 
			throws ExpiratedProductDateException, InstanceNotFoundException,
			UnauthorizedBidException, InsufficientBidQuantityException,
			UnauthorizedWinningUserException {
		
		return toBidProductDto(auctionService.createBid(userId, 
				params.getProductId(), params.getQuantity()));
	}
	
	@GetMapping("/bids")
	public BlockDto<BidDto> getUserBids(@RequestAttribute Long userId,
			@RequestParam(defaultValue="0") int page) 
			throws InstanceNotFoundException {
		
		Block<Bid> bidBlock = auctionService.getUserBids(userId, page, 10);
		
		return new BlockDto<>(toBidDtos(bidBlock.getItems()),bidBlock.getExistMoreItems());
	}
}

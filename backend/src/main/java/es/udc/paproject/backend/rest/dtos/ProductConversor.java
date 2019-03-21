package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Product;

public class ProductConversor {
	
	private ProductConversor() {}
	
	public final static UserProductDto toUserProductDto(Product product) {
		
		return new UserProductDto(product.getId(), product.getName(), 
			product.getRemainingTime(), product.getCurrentPrice(),
			product.getWinningBid().getUser().getEmail());
	}
	
	public final static List<ProductSummaryDto> toProductSummaryDtos(List<Product> products) {
		return products.stream().map(p -> toProductSummaryDto(p)).collect(Collectors.toList());
	}
	
	private final static ProductSummaryDto toProductSummaryDto(Product product) {
		
		return new ProductSummaryDto(product.getId(), product.getCategory().getId(), product.getName(), 
				product.getCurrentPrice(), Math.floorDiv(product.getRemainingTime(), (long)60000));
	}
	
	public final static ProductDetailDto toProductDetailDto(Product product) {
		
		return new ProductDetailDto(product.getId(), product.getCategory().getName(), 
				product.getName(), product.getDescriptionProduct(), 
				product.getUser().getUserName(), 
				toMillis(product.getCreationTime()), product.getRemainingTime(),
				product.getInitialPrice(), product.getCurrentPrice(), 
				product.getShipmentInfo());
	}
	
	private final static Long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}
}

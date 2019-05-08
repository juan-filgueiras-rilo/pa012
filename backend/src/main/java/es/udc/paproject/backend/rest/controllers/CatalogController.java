package es.udc.paproject.backend.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.dtos.AddProductParamsDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import es.udc.paproject.backend.rest.dtos.CategoryDto;
import es.udc.paproject.backend.rest.dtos.IdDto;
import es.udc.paproject.backend.rest.dtos.ProductDto;
import es.udc.paproject.backend.rest.dtos.ProductSummaryDto;
import es.udc.paproject.backend.rest.dtos.UserProductDto;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.toProductDto;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.toProductSummaryDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.toUserProductDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;
	
	@GetMapping("/categories")
	public List<CategoryDto> findAllCategories() {
		return toCategoryDtos(catalogService.findAllCategories());
	}
	
	@GetMapping("/products")
	public BlockDto<ProductSummaryDto> findProducts(@RequestParam(required=false) Long categoryId,
			@RequestParam(required=false) String keywords, 
			@RequestParam(defaultValue="0") int page) {
		
		Block<Product> productBlock = catalogService.findProducts(categoryId,
				keywords, page, 10);
		
		return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()),
				productBlock.getExistMoreItems());
	}

	@GetMapping("/products/{productId}")
	public ProductDto getProductDetail(@PathVariable Long productId) throws InstanceNotFoundException {
		
		return toProductDto(catalogService.getProductDetail(productId));
	}
	
	@GetMapping("/userProducts")
	public BlockDto<UserProductDto> getUserProducts(@RequestAttribute Long userId,
			@RequestParam(defaultValue="0") int page) 
			throws InstanceNotFoundException {
		
		Block<Product> productBlock = catalogService.getUserProducts(userId,page,10);
		
		return new BlockDto<>(toUserProductDtos(productBlock.getItems()),
				productBlock.getExistMoreItems());
	}
	
	@PostMapping("/products")
	public IdDto addProduct(@RequestAttribute Long userId, 
			@Validated @RequestBody AddProductParamsDto params) 
					throws InstanceNotFoundException {
		Long productId = catalogService.addProduct(userId, params.getName(),
				params.getDescription(), params.getDuration(), 
				params.getInitialPrice(), params.getShipmentInfo(), 
				params.getCategoryId());
		
		return new IdDto(productId);
	}
	
}

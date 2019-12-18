//package es.udc.paproject.backend.test.model.controller;
//
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.charset.Charset;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.MessageSource;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
//import es.udc.paproject.backend.model.entities.Category;
//import es.udc.paproject.backend.model.entities.Product;
//import es.udc.paproject.backend.model.entities.User;
//import es.udc.paproject.backend.model.entities.User.RoleType;
//import es.udc.paproject.backend.model.services.Block;
//import es.udc.paproject.backend.model.services.CatalogService;
//import es.udc.paproject.backend.rest.common.JwtGenerator;
//import es.udc.paproject.backend.rest.common.JwtInfo;
//import es.udc.paproject.backend.rest.controllers.CatalogController;
//import es.udc.paproject.backend.rest.dtos.AddProductParamsDto;
//
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CatalogController.class)
//public class CatalogControllerTest {
//
//	@MockBean
//	CatalogService catalogService;
//	@MockBean
//	MessageSource messageSourceMock;
//	@MockBean
//	JwtGenerator jwtGeneratorMock;
//
//	@MockBean
//	BCryptPasswordEncoder passwordEncoder;
//
//	@InjectMocks
//	private CatalogController catalogController;
//
//	@Autowired
//	private MockMvc mockMvc;
//	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
//	private static final String KEYWORD = "keywords";
//	private static final String PROD_NAME = "prod";
//	private static final String PROD_DESCR = "Descr";
//	private static final Long DURATION_OK = 10000L;
//	private static final Long DURATION_INVALID = -10000L;
//	private static final BigDecimal PROD_PRICE = new BigDecimal(10);
//	private static final BigDecimal PROD_PRICE_INVALID = new BigDecimal(-1);
//	private static final String PROD_SHIPP = "shipping";
//
//	private Category CATEGORY;
//	private static final int PAGE = 0;
//	private Block<Product> PROD_BLOCK_OK;
//	private List<Category> CAT_LIST_OK;
//	private Product PRODUCT;
//	private static final Long PRODUCTID_OK = 1L;
//	private static final Long PRODUCTID_INVALID = -1L;
//
//	private static final Long USERID_INVALID = -1L;
//	private static final Long USERID_OK = 1L;
//	private static final String TOKEN_OK = "ValidToken";
//	private static final String TOKEN_NOTFOUND = "InvalidTokenUserNotFound";
//	private JwtInfo JWT_OK;
//	private JwtInfo JWT_NOTFOUND;
//	
//	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        return mapper.writeValueAsBytes(object);
//    }
//	
//	@Before
//	public void startUp() throws InstanceNotFoundException
//			 {
//		String userName = "userName";
//		RoleType role = RoleType.USER;
//
//		JwtInfo jwt_ok = new JwtInfo(USERID_OK, userName, role.toString());
//		JWT_OK = jwt_ok;
//		JwtInfo jwt_notFound = new JwtInfo(USERID_INVALID, userName, role.toString());
//		JWT_NOTFOUND = jwt_notFound;
//
//		User user_ok = new User(userName, "pass", "firstName", "lastName", userName + "@user.com");
//		user_ok.setRole(role);
//		user_ok.setId(USERID_OK);
//
//		Category category = new Category("catName");
//		category.setId(1L);
//		CATEGORY = category;
//		Product product = new Product(PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE, PROD_SHIPP, category, user_ok);
//		product.setId(PRODUCTID_OK);
//		PRODUCT = product;
//
//		when(jwtGeneratorMock.generate(JWT_OK)).thenReturn(TOKEN_OK);
//		when(jwtGeneratorMock.getInfo(TOKEN_OK)).thenReturn(JWT_OK);
//		when(jwtGeneratorMock.getInfo(TOKEN_NOTFOUND)).thenReturn(JWT_NOTFOUND);
//
//		List<Product> pItems = new ArrayList<Product>();
//		PROD_BLOCK_OK = new Block<Product>(pItems, false);
//		List<Category> catList = new ArrayList<Category>();
//		catList.add(category);
//		CAT_LIST_OK = catList;
//
//		when(catalogService.findProducts(null, null, PAGE, 10)).thenReturn(PROD_BLOCK_OK);
//		when(catalogService.findProducts(CATEGORY.getId(), KEYWORD, PAGE, 10)).thenReturn(PROD_BLOCK_OK);
//		when(catalogService.findAllCategories()).thenReturn(CAT_LIST_OK);
//		when(catalogService.getProductDetail(PRODUCTID_OK)).thenReturn(PRODUCT);
//		when(catalogService.getProductDetail(PRODUCTID_INVALID))
//				.thenThrow(new InstanceNotFoundException(null, PRODUCTID_INVALID));
//
//		when(catalogService.addProduct(USERID_OK, PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE, PROD_SHIPP, CATEGORY.getId()))
//			.thenReturn(PRODUCT.getId());
//
//		when(catalogService.addProduct(USERID_OK, PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE, PROD_SHIPP, -1L))
//		.thenReturn(PRODUCT.getId());
//
//	}
//	
//	@Test
//	public void testFindProductByKeywords() throws Exception {
//		ResultActions result = mockMvc.perform(get("/products/")
//			.contentType(APPLICATION_JSON_UTF8)
//			.param("page", Integer.toString(PAGE)));
//						
//	}
//
//	@Test
//	public void testFindProductByKeywordsAllOptions() throws Exception {
//		ResultActions result = mockMvc.perform(get("/products/")
//			.contentType(APPLICATION_JSON_UTF8)
//			.param("page", Integer.toString(PAGE))
//			.param("keywords", KEYWORD)
//			.param("categoryId", Long.toString(CATEGORY.getId())));
//						
//		result.andExpect(content().contentType(APPLICATION_JSON_UTF8))
//			.andExpect(status().isOk());
//	}
//
////	/*******
////	 * TEST FIND PRODUCTS
////	 * 
////	 * @throws Exception
////	 ************************************/
////
////	@Test
////	public void testFindProducts() throws Exception {
////		// @formatter:off
////		ResultActions result = mockMvc.perform(get(UrlConfig.URL_CATALOG_FINDPROD_GET)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8)
////			.header("Authorization", "Bearer " + TOKEN_OK)
////			.param("page", Integer.toString(PAGE)));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isOk());
////		// @formatter:on	
////	}
////
////	/**************
////	 * TEST FIND ALL CATEGORIES
////	 * 
////	 * @throws Exception
////	 ***************************/
////
////	@Test
////	public void testFindAllCategories() throws Exception {
////		// @formatter:off
////		ResultActions result = mockMvc.perform(get(UrlConfig.URL_CATALOG_FINDALLCAT_GET)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isOk());
////		// @formatter:on
////	}
////
////	/************
////	 * TEST FIND PRODUCT BY ID
////	 * 
////	 * @throws Exception
////	 * @throws InstanceNotFoundException
////	 ********************/
////
////	@Test
////	public void testFindProductsById() throws Exception, InstanceNotFoundException {
////		// @formatter:off
////		ResultActions result = mockMvc.perform(get(UrlConfig.URL_CATALOG_FINDPRODBYID_GET, PRODUCTID_OK)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isOk());
////		// @formatter:on	
////	}
////
////	@Test
////	public void testFindProductsByIdInstanceNotFoundException() throws Exception, InstanceNotFoundException {
////		// @formatter:off
////		ResultActions result = mockMvc.perform(get(UrlConfig.URL_CATALOG_FINDPRODBYID_GET, PRODUCTID_INVALID)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isNotFound());
////		// @formatter:on	
////	}
////
////	/*********
////	 * TEST ANNOUNCE PRODUCTS
////	 * 
////	 *********************************/
////
////	@Test
////	public void testAnnounceProducts()
////			throws InvalidPriceException, InstanceNotFoundException, InvalidDurationException, Exception {
////
////		ProductInsertDto params = new ProductInsertDto(PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE, PROD_SHIPP,
////				CATEGORY.getId(), USERID_OK);
////
////		// @formatter:off
////		ResultActions result = mockMvc.perform(post(UrlConfig.URL_CATALOG_ANNOUNCE_POST)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8)
////			.header("Authorization", "Bearer " + TOKEN_OK)
////			.content(Utils.convertObjectToJsonBytes(params)));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isOk());
////		// @formatter:on	
////	}
////
////	@Test
////	public void testAnnounceProductsInvalidPriceException()
////			throws InvalidPriceException, InstanceNotFoundException, InvalidDurationException, Exception {
////
////		ProductInsertDto params = new ProductInsertDto(PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE_INVALID,
////				PROD_SHIPP, CATEGORY.getId(), USERID_OK);
////
////		// @formatter:off
////		ResultActions result = mockMvc.perform(post(UrlConfig.URL_CATALOG_ANNOUNCE_POST)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8)
////			.header("Authorization", "Bearer " + TOKEN_OK)
////			.content(Utils.convertObjectToJsonBytes(params)));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isBadRequest());
////		// @formatter:on	
////	}
////
////	@Test
////	public void testAnnounceProductsInstanceNotFoundException()
////			throws InvalidPriceException, InstanceNotFoundException, InvalidDurationException, Exception {
////
////		ProductInsertDto params = new ProductInsertDto(PROD_NAME, PROD_DESCR, DURATION_OK, PROD_PRICE, PROD_SHIPP,
////				CATEGORY.getId(), USERID_INVALID);
////
////		// @formatter:off
////		ResultActions result = mockMvc.perform(post(UrlConfig.URL_CATALOG_ANNOUNCE_POST)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8)
////			.header("Authorization", "Bearer " + TOKEN_NOTFOUND)
////			.content(Utils.convertObjectToJsonBytes(params)));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isNotFound());
////		// @formatter:on	
////	}
////
////	@Test
////	public void testAnnounceProductsInvalidDurationException()
////			throws InvalidPriceException, InstanceNotFoundException, InvalidDurationException, Exception {
////
////		ProductInsertDto params = new ProductInsertDto(PROD_NAME, PROD_DESCR, DURATION_INVALID, PROD_PRICE, PROD_SHIPP,
////				CATEGORY.getId(), USERID_OK);
////
////		// @formatter:off
////		ResultActions result = mockMvc.perform(post(UrlConfig.URL_CATALOG_ANNOUNCE_POST)
////			.contentType(TestUtils.APPLICATION_JSON_UTF8)
////			.header("Authorization", "Bearer " + TOKEN_OK)
////			.content(Utils.convertObjectToJsonBytes(params)));
////						
////		result.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
////			.andExpect(status().isBadRequest());
////		// @formatter:on	
////	}
//}
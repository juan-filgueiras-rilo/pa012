package es.udc.paproject.backend.test.model.quickcheck;

import java.math.BigDecimal;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

public class ProductGenerator extends Generator<Product>{

	public ProductGenerator() {
		super(Product.class);
	}

	@Override
	public Product generate(SourceOfRandomness random, GenerationStatus status) {
		
		String name = gen().type(String.class).generate(random, status);
		String description = gen().type(String.class).generate(random, status);
		Long duration = random.nextLong(10L,100000L);
		BigDecimal initialPrice = gen().type(BigDecimal.class).generate(random, status);
		String shipmentInfo = gen().type(String.class).generate(random, status);
		Category category = new Category("cat");
		category.setId(random.nextLong(0,1));
		User user = new User("user","password","user","user","user@user.com");
		user.setId(random.nextLong(0,1));
		Product product = new Product(name,description,duration,initialPrice,shipmentInfo,category,user);
		product.setId(random.nextLong(1,1000));
		return product;
	}

}

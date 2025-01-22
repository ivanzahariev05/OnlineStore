package softuni.bg.supplementsonlinestore.product.service;

import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.product.model.Product;
import softuni.bg.supplementsonlinestore.product.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    Random random = new Random();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getRandomProducts() {

        List<Product> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) {
            return List.of();
        }
        Collections.shuffle(allProducts, random);

        return allProducts.stream().toList();
    }
}

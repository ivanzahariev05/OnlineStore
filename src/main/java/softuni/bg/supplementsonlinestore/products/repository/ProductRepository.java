package softuni.bg.supplementsonlinestore.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bg.supplementsonlinestore.products.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

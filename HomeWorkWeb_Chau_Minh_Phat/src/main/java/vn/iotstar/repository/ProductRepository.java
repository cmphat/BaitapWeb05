package vn.iotstar.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    long countByCategory(Category category);

    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    List<Product> findTop10ByOrderByCreatedAtDesc();
}

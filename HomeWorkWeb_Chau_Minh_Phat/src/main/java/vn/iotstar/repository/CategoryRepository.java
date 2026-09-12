package vn.iotstar.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByCategorynameContainingIgnoreCase(String categoryname, Pageable pageable);

    Optional<Category> findByCategoryname(String categoryname);

    boolean existsByCategorynameIgnoreCase(String categoryname);

    boolean existsByCategorynameIgnoreCaseAndCategoryidNot(String categoryname, int categoryid);
}

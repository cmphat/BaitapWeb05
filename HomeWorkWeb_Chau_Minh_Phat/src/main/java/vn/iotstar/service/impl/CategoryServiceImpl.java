package vn.iotstar.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.ICategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void insert(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByCategoryname(String name) {
        return categoryRepository.findByCategoryname(name).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return categoryRepository.findAll();
        }
        return categoryRepository.findByCategorynameContainingIgnoreCase(keyword.trim(), Pageable.unpaged()).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> searchByName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.findByCategorynameContainingIgnoreCase(keyword.trim(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByCategorynameIgnoreCase(name != null ? name.trim() : "");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameExceptId(String name, int id) {
        return categoryRepository.existsByCategorynameIgnoreCaseAndCategoryidNot(name != null ? name.trim() : "", id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countProductsByCategory(int categoryId) {
        Category cat = findById(categoryId);
        if (cat == null) {
            return 0;
        }
        return productRepository.countByCategory(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {
        return (int) categoryRepository.count();
    }
}
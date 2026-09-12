package vn.iotstar.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void insert(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by("productId").descending());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findLatest(int limit) {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll(int page, int pageSize) {
        return productRepository.findAll(PageRequest.of(page, pageSize, Sort.by("productId").descending())).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return productRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return productRepository.findByProductNameContainingIgnoreCase(keyword.trim(), Pageable.unpaged()).getContent();
    }
}

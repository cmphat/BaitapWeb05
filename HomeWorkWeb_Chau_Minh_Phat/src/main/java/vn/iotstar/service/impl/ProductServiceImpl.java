package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.impl.ProductDao;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

public class ProductServiceImpl implements IProductService {

    private final IProductDao productDao = new ProductDao();

    @Override
    public void insert(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        productDao.insert(product);
    }

    @Override
    public void update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        productDao.update(product);
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findLatest(int limit) {
        return productDao.findLatest(limit);
    }

    @Override
    public List<Product> findAll(int page, int pageSize) {
        return productDao.findAll(page, pageSize);
    }

    @Override
    public long count() {
        return productDao.count();
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productDao.searchByName(keyword);
    }
}

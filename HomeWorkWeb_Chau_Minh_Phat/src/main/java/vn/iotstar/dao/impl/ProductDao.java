package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.entity.Product;

public class ProductDao implements IProductDao {

    @Override
    public void insert(Product product) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Product product) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Product product = enma.find(Product.class, id);
            if (product != null) {
                enma.remove(product);
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Product findById(int id) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(Product.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p ORDER BY p.createdAt DESC", Product.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findLatest(int limit) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p ORDER BY p.createdAt DESC", Product.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findAll(int page, int pageSize) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p ORDER BY p.createdAt DESC", Product.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public long count() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Long> query = enma.createQuery("SELECT COUNT(p) FROM Product p", Long.class);
            return query.getSingleResult();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> searchByName(String keyword) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(:keyword) ORDER BY p.createdAt DESC", Product.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }
}

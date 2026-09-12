package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

public class CategoryDao implements ICategoryDao {

    @Override
    public void insert(Category category) {

        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            em.persist(category);

            trans.commit();

        } catch (Exception e) {

            if (trans.isActive()) {
                trans.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {

        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            em.merge(category);

            trans.commit();

        } catch (Exception e) {

            if (trans.isActive()) {
                trans.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {

        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            Category category = em.find(Category.class, id);

            if (category != null) {
                em.remove(category);
            }

            trans.commit();

        } catch (Exception e) {

            if (trans.isActive()) {
                trans.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(int id) {

        EntityManager em = JpaConfig.getEntityManager();

        try {
            return em.find(Category.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public Category findByCategoryname(String name) {

        EntityManager em = JpaConfig.getEntityManager();

        try {

            String jpql =
                    "SELECT c FROM Category c "
                    + "WHERE c.categoryname = :name";

            TypedQuery<Category> query =
                    em.createQuery(jpql, Category.class);

            query.setParameter("name", name);

            List<Category> result =
                    query.getResultList();

            if (result.isEmpty()) {
                return null;
            }

            return result.get(0);

        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {

        EntityManager em = JpaConfig.getEntityManager();

        try {

            TypedQuery<Category> query =
                    em.createNamedQuery(
                            "Category.findAll",
                            Category.class
                    );

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> searchByName(String keyword) {

        EntityManager em = JpaConfig.getEntityManager();

        try {

            String jpql =
                    "SELECT c FROM Category c "
                    + "WHERE c.categoryname LIKE :keyword";

            TypedQuery<Category> query =
                    em.createQuery(jpql, Category.class);

            query.setParameter(
                    "keyword",
                    "%" + keyword + "%"
            );

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public int count() {

        EntityManager em = JpaConfig.getEntityManager();

        try {

            Query query =
                    em.createQuery(
                            "SELECT COUNT(c) FROM Category c"
                    );

            return ((Long) query.getSingleResult()).intValue();

        } finally {
            em.close();
        }
    }
}
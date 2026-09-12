package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
@NamedQuery(
        name = "Category.findAll",
        query = "SELECT c FROM Category c"
)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryid;

    @Column(name = "CategoryName",
            columnDefinition = "NVARCHAR(255)")
    private String categoryname;

    @Column(name = "Images",
            columnDefinition = "NVARCHAR(500)")
    private String images;

    @Column(name = "Status")
    private int status;

    @jakarta.persistence.OneToMany(mappedBy = "category")
    private java.util.List<Product> products;

    public Category() {
    }

    public Category(String categoryname, String images, int status) {
        this.categoryname = categoryname;
        this.images = images;
        this.status = status;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public java.util.List<Product> getProducts() {
        return products;
    }

    public void setProducts(java.util.List<Product> products) {
        this.products = products;
    }
}
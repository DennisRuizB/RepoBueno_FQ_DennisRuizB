package edu.upc.dsa.models;

public class Products {
    Integer id;
    String nameProduct;
    Integer price;

    public Products(int id, String nameProduct, int price) {
        this();
        this.setId(id);
        this.setNameProduct(nameProduct);
        this.setPrice(price);
    }

    public Products() {
    }

    public String getDatos(){
        return "Product [id="+id+", name=" + nameProduct + ", price=" + price + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

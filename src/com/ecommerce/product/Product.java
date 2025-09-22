package com.ecommerce.product;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public boolean isInStock(int quantity) { return stock >= quantity; }

    public void reduceStock(int quantity) {
        if(quantity <= stock) stock -= quantity;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return "Product[ID=" + productId + ", Name=" + name + ", Price=" + price + ", Stock=" + stock + "]";
    }
}

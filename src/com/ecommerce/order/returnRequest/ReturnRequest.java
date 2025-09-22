package com.ecommerce.order.returnRequest;

import com.ecommerce.product.Product;
import com.ecommerce.orders.Order;
import com.ecommerce.order.orderItems.OrderItem;

public class ReturnRequest {
    public enum Status {
        APPROVED,
        REJECTED
    }

    private int returnId;
    private Order order;
    private Product product;
    private int quantity;
    private Status status;

    public ReturnRequest(int returnId, Order order, Product product, int quantity) {
        this.returnId = returnId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.status = Status.REJECTED;
    }

    public void processReturn() {
        int purchasedQty = 0;
        for(OrderItem item : order.getItems()) {
            if(item.getProduct().getProductId() == product.getProductId()) {
                purchasedQty = item.getQuantity();
                break;
            }
        }
        if(quantity <= purchasedQty) {
            status = Status.APPROVED;
            product.increaseStock(quantity);
        }
    }

    public Status getStatus() { 
    	return status; 
    	}
}

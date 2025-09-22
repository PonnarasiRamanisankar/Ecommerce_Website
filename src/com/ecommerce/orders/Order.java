package com.ecommerce.orders;

import com.ecommerce.customer.Customer;
import com.ecommerce.order.orderItems.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum Status {
        PLACED,
        PAID,
        SHIPPED
    }

    private int orderId;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private Status status = Status.PLACED;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public int getOrderId() { 
    	return orderId; 
    	}
    public Customer getCustomer() {
    	return customer;
    	}
    public List<OrderItem> getItems() { 
    	return items;
    	}
    public Status getStatus() { 
    	return status;
    	}
    public void setStatus(Status status) {
    	this.status = status; 
    	}

    public void addItem(OrderItem item) throws Exception {
        if(!item.getProduct().isInStock(item.getQuantity())) {
            throw new Exception("Insufficient stock: " + item.getProduct().getName());
        }
        item.getProduct().reduceStock(item.getQuantity());
        items.add(item);
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void printInvoice() {
        System.out.println("\nOrder Invoice");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName());
        for(OrderItem item : items)
            System.out.println(item);
        System.out.println("Total Amount: " + getTotalAmount());
        System.out.println("Order Status: " + status);
    }
}

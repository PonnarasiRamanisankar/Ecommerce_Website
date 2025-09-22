package com.ecommerce.order.payment;

import com.ecommerce.orders.Order;

public class Payment {
    public enum Status {
        SUCCESSFUL, FAILED
    }

    private int paymentId;
    private Order order;
    private double amount;
    private Status status;

    public Payment(int paymentId, Order order, double amount) {
        this.paymentId = paymentId;
        this.order = order;
        this.amount = amount;
        this.status = Status.FAILED;
    }

    public void processPayment() {
        if(amount >= order.getTotalAmount()) {
            status = Status.SUCCESSFUL;
            order.setStatus(Order.Status.PAID);
        }
    }

    public Status getStatus() { 
    	return status; 
    	}
}

package com.ecommerce.order.shipment;

import com.ecommerce.orders.Order;

public class Shipment {
    public enum Status {
        PENDING, SHIPPED
    }

    private int shipmentId;
    private Order order;
    private Status status;

    public Shipment(int shipmentId, Order order) {
        this.shipmentId = shipmentId;
        this.order = order;
        this.status = Status.PENDING;
    }

    public void ship() throws Exception {
        if(order.getStatus() != Order.Status.PAID) {
            throw new Exception("Order not paid, cannot ship.");
        }
        order.setStatus(Order.Status.SHIPPED);
        status = Status.SHIPPED;
    }

    public Status getStatus() {
    	return status; 
    	}
}

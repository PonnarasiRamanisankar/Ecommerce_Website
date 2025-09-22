package com.ecommerce.main;

import com.ecommerce.product.Product;
import com.ecommerce.customer.Customer;
import com.ecommerce.orders.Order;
import com.ecommerce.order.orderItems.OrderItem;
import com.ecommerce.order.payment.Payment;
import com.ecommerce.order.returnRequest.ReturnRequest;
import com.ecommerce.order.shipment.Shipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ecommerce {
    private static Scanner sc = new Scanner(System.in);

    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    private static int productIdSeq = 1;
    private static int customerIdSeq = 1;
    private static int orderIdSeq = 1;
    private static int paymentIdSeq = 1;
    private static int shipmentIdSeq = 1;
    private static int returnIdSeq = 1;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Place Order");
            System.out.println("4. Make Payment");
            System.out.println("5. Ship Order");
            System.out.println("6. Request Return");
            System.out.println("7. View Products");
            System.out.println("8. Exit");

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> addCustomer();
                case 3 -> placeOrder();
                case 4 -> makePayment();
                case 5 -> shipOrder();
                case 6 -> requestReturn();
                case 7 -> products.forEach(System.out::println);
                case 8 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        System.out.println("Application terminated.");
    }

    private static void addProduct() {
        String name = readLine("Name: ");
        double price = readDouble("Price: ");
        int stock = readInt("Stock quantity: ");
        products.add(new Product(productIdSeq++, name, price, stock));
        System.out.println("Product added.");
    }

    private static void addCustomer() {
        String name = readLine("Name: ");
        String email = readLine("Email: ");
        customers.add(new Customer(customerIdSeq++, name, email));
        System.out.println("Customer added.");
    }

    private static void placeOrder() {
        if(customers.isEmpty()) {
            System.out.println("No customers found; add customers first.");
            return;
        }
        int custId = readInt("Customer ID: ");
        Customer customer = customers.stream().filter(c -> c.getId() == custId).findFirst().orElse(null);
        if(customer == null) {
            System.out.println("Invalid customer ID.");
            return;
        }

        Order order = new Order(orderIdSeq++, customer);
        while(true) {
            products.forEach(System.out::println);
            int prodId = readInt("Product ID to add to order (0 to finish): ");
            if(prodId == 0) break;
            Product product = products.stream().filter(p -> p.getProductId() == prodId).findFirst().orElse(null);
            if(product == null) {
                System.out.println("Invalid product ID.");
                continue;
            }
            int qty = readInt("Quantity: ");
            try {
                order.addItem(new OrderItem(product, qty));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        if(order.getItems().isEmpty()) {
            System.out.println("No items added to order.");
            return;
        }
        orders.add(order);
        System.out.println("Order placed:");
        order.printInvoice();
    }

    private static void makePayment() {
        int orderId = readInt("Order ID to pay: ");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findFirst().orElse(null);
        if(order == null) {
            System.out.println("Order not found.");
            return;
        }
        if(order.getStatus() != Order.Status.PLACED) {
            System.out.println("Order cannot be paid (already paid or shipped).");
            return;
        }
        double amount = readDouble("Enter payment amount: ");
        Payment payment = new Payment(paymentIdSeq++, order, amount);
        payment.processPayment();
        System.out.println("Payment status: " + payment.getStatus());
    }

    private static void shipOrder() {
        int orderId = readInt("Order ID to ship: ");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findFirst().orElse(null);
        if(order == null) {
            System.out.println("Order not found.");
            return;
        }
        Shipment shipment = new Shipment(shipmentIdSeq++, order);
        try {
            shipment.ship();
            System.out.println("Order shipped.");
        } catch (Exception e) {
            System.out.println("Cannot ship order: " + e.getMessage());
        }
    }

    private static void requestReturn() {
        int orderId = readInt("Order ID to return from: ");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findFirst().orElse(null);
        if(order == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println("Order Items:");
        for(var item : order.getItems()) {
            System.out.println(item.getProduct().getProductId() + ": " + item.getProduct().getName() + " Qty: " + item.getQuantity());
        }
        int prodId = readInt("Product ID to return: ");
        Product product = products.stream().filter(p -> p.getProductId() == prodId).findFirst().orElse(null);
        if(product == null) {
            System.out.println("Product not found.");
            return;
        }
        int qty = readInt("Return quantity: ");
        ReturnRequest returnRequest = new ReturnRequest(returnIdSeq++, order, product, qty);
        returnRequest.processReturn();
        System.out.println("Return request status: " + returnRequest.getStatus());
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static int readInt(String prompt) {
        while(true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch(NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while(true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch(NumberFormatException e) {
                System.out.println("Invalid decimal number. Try again.");
            }
        }
    }
}

package main;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RunnerController {
	orderControl orderControl = new orderControl();
	
    @PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;

    public void acceptOrder(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("preparing")) {
            order.setOrderStatus("accepted");
            entityManager.merge(order);
        }
    }

    public void cancelOrder(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("preparing")) {
            order.setOrderStatus("canceled");
            entityManager.merge(order);
        }
    }

    public void completeOrder(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("accepted")) {
            order.setOrderStatus("completed");
            entityManager.merge(order);
        }
    }

    public int getTripsN() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'completed'", Long.class)
                .getSingleResult()
                .intValue();
    }

    public void getTripsHistory() {
        List<Order> completedOrders = entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'completed'", Order.class)
                .getResultList();
        for (Order order : completedOrders) {
            System.out.println("Order ID: " + order.getId() + ", Restaurant Name: " + orderControl.getRestaurant(order.getResturantId()).getName());
            // Print more details or perform any other operations
        }
    }
}
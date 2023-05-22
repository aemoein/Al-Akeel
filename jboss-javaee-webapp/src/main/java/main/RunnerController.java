package main;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/runners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RunnerController {
	
    @PersistenceContext
    private EntityManager entityManager;

    @PUT
    @Path("/orders/{orderId}/accept")
    public Response acceptOrder(@PathParam("orderId") int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("preparing")) {
            order.setOrderStatus("accepted");
            entityManager.merge(order);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    
    @PUT
    @Path("/orders/{orderId}/cancel")
    public Response cancelOrder(@PathParam("orderId") int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("preparing")) {
            order.setOrderStatus("canceled");
            entityManager.merge(order);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{orderId}/complete")
    public Response completeOrder(@PathParam("orderId") int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderStatus().equals("accepted")) {
            order.setOrderStatus("completed");
            entityManager.merge(order);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/trips")
    public Response getTripsCount() {
        return Response.ok(" Number of trips: " + entityManager.createQuery("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'completed'", Long.class).getSingleResult().intValue()).build();
    }
    
    
    @GET
    @Path("/history")
    public Response getTripsHistory() {
    	List<Order> completedOrders = entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'completed'", Order.class).getResultList();
    	StringBuilder output = new StringBuilder();

    	for (Order order : completedOrders) {
    	    Restaurant restaurant = entityManager.find(Restaurant.class, order.getResturantId());
    	    String orderDetails = "Order ID: " + order.getId() + ", Restaurant Name: " + restaurant.getName();
    	    output.append(orderDetails).append("\n");
    	}

    	String outputString = output.toString();
    	return Response.ok(outputString).build();

    }   
}
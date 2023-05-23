package main;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerControl {
	
    @PersistenceContext
	EntityManager entityManager;

    @POST
    @Path("/create-order/{restaurantId}/{runnerId}/{mealId}")
    public Response createOrder(@PathParam("restaurantId") long restaurantId, @PathParam("runnerId") long runnerId, @PathParam("mealId") long mealid) {
        User orderUser = UserCredentials.currentUser;
        float totalPrice = 0;
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setfk_runnerId(runnerId);
        order.setUserId(orderUser.getId());
        
        Meal meal = entityManager.find(Meal.class, mealid);

      	order.addItems(meal);
        totalPrice += meal.getPrice();

        order.setTotalPrice(totalPrice);

        entityManager.persist(order);
        return Response.ok(order).build();
    }


    @PUT
    @Path("/edit-order/{choice}/{orderId}/{mealId}")
    public Response editOrder(@PathParam("choice") int choice , @PathParam("orderId") long orderId, @PathParam("mealId") long mealId) {
        while (choice != 3) {
            System.out.println("Choose the desired edit:");
            System.out.println("1. Remove item");
            System.out.println("2. Add item");
            System.out.println("3. Exit");
            
            Order order = entityManager.find(Order.class, orderId);
            Restaurant restaurant = entityManager.find(Restaurant.class, order.getResturantId());
            Meal meal = entityManager.find(Meal.class, mealId);

            if (choice == 1) {
                order.getItemsArray();
                System.out.println("Choose the meal you would like to remove:");
                
                // Retrieve the meal choice to remove from query parameters
                
                order.RemoveItems(meal);
            } else if (choice == 2) {
                restaurant.getMeals();
                System.out.println("Choose the meal you would like to add:");
                
                // Retrieve the meal choice to add from query parameters
                
                order.addItems(meal);
            } else {
                entityManager.persist(order);
            }
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/cancel-order/{orderId}")
    public Response cancelOrder(@PathParam("orderId") long orderId) {
    	Order order = entityManager.find(Order.class, orderId);
    	order.setOrderStatus("Cancelled");
    	entityManager.persist(order);
        return Response.ok().build();
    }

    @GET
    @Path("/list-restaurants")
    public Response listRestaurants() {
    	List<Restaurant> restaurants = entityManager.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();

    	StringBuilder restaurantList = new StringBuilder();
    	restaurantList.append("Available Restaurants:\n");

    	for (Restaurant restaurant : restaurants) {
    	    restaurantList.append(restaurant.getName()).append("\n");
    	}

    	String responseString = restaurantList.toString();
    	return Response.ok(responseString).build();
    }
}


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
    @Path("/create-order/{restaurantId}/{runnerId}")
    public Response createOrder(@PathParam("restaurantId") long restaurantId, @PathParam("runnerId") long runnerId, @Context UriInfo uriInfo) {
        User orderUser = UserCredentials.currentUser;
        float totalPrice = 0;
        Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setfk_runnerId(runnerId);
        order.setUserId(orderUser.getId());
        boolean addToOrder = true;

        while (addToOrder) {
            List<Meal> meals = restaurant.getMeals();
            System.out.println("Choose the meal you would like to add:");

            // Retrieve the meal choice from query parameters
            int addChoice = Integer.parseInt(uriInfo.getQueryParameters().getFirst("mealChoice"));

            order.addItems(meals.get(addChoice - 1));
            totalPrice += meals.get(addChoice - 1).getPrice();

            System.out.println("Would you like to add more meals? 1. Yes 2. No:");

            // Retrieve the continue choice from query parameters
            int cont = Integer.parseInt(uriInfo.getQueryParameters().getFirst("continueChoice"));

            if (cont == 2) {
                addToOrder = false;
            }
        }

        order.setTotalPrice(totalPrice);

        entityManager.persist(order);
        return Response.ok(order).build();
    }


    @PUT
    @Path("/edit-order/{orderId}")
    public Response editOrder(@PathParam("orderId") long orderId, @Context UriInfo uriInfo) {
        int choice = 0;

        while (choice != 3) {
            System.out.println("Choose the desired edit:");
            System.out.println("1. Remove item");
            System.out.println("2. Add item");
            System.out.println("3. Exit");
            
            // Retrieve the choice from query parameters
            choice = Integer.parseInt(uriInfo.getQueryParameters().getFirst("editChoice"));
            
            Order order = entityManager.find(Order.class, orderId);
            Restaurant restaurant = entityManager.find(Restaurant.class, order.getResturantId());

            if (choice == 1) {
                order.getItemsArray();
                System.out.println("Choose the meal you would like to remove:");
                
                // Retrieve the meal choice to remove from query parameters
                int removeChoice = Integer.parseInt(uriInfo.getQueryParameters().getFirst("removeChoice"));
                
                order.getItemsArray().remove(removeChoice - 1);
            } else if (choice == 2) {
                restaurant.getMeals();
                System.out.println("Choose the meal you would like to add:");
                
                // Retrieve the meal choice to add from query parameters
                int addChoice = Integer.parseInt(uriInfo.getQueryParameters().getFirst("addChoice"));
                
                order.addItems(restaurant.getMeals().get(addChoice));
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


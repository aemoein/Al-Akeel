package main;

import java.util.List;
import java.util.Scanner;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerControl {
	
    @PersistenceContext
	EntityManager entityManager;

    @POST
    @Path("/create-order/{restaurantId}/{runnerId}")
    public Response createOrder(@PathParam("restaurantId") long restaurantId, @PathParam("runnerId") long runnerId) {
    	Scanner scanner = new Scanner(System.in);
    	User OrderUser = UserCredentials.currentUser;
    	float totalprice = 0;
    	Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
    	
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setfk_runnerId(runnerId);
        order.setUserId(OrderUser.getId());
        boolean addtoOrder = true;
        
        while(addtoOrder) {
        	restaurant.getMeals();
            System.out.println("choose the meal you would like to add");
            int AddChoice = scanner.nextInt();
            order.addItems(restaurant.getMeals().get(AddChoice-1));
            totalprice =+ restaurant.getMeals().get(AddChoice-1).getPrice();
            
            System.out.println("Would you like to add more Meals? 1. Yes 2. No : ");
            int Cont = scanner.nextInt();
            
            if (Cont == 2)
            {
            	addtoOrder = false;
            }      
        }
        
        order.setTotalPrice(totalprice);
        
        entityManager.persist(order);
        return Response.ok(order).build();
    }

    @PUT
    @Path("/edit-order/{orderId}")
    public Response editOrder(@PathParam("orderId") long orderId) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(choice != 3)
        {
            System.out.println("Choose the desired edit");
            System.out.println("1. Remove item");
            System.out.println("2. Add item");
            System.out.println("3. Exit");
            choice = scanner.nextInt();
            Order order = entityManager.find(Order.class, orderId);
            Restaurant restaurant = entityManager.find(Restaurant.class, order.getResturantId());
    	
            if (choice == 1) {
            	order.getItemsArray();
                System.out.println("choose the meal you would like to remove");
                int removeChoice = scanner.nextInt();
                order.getItemsArray().remove(removeChoice-1);
            }

            else if (choice == 2) {
            	restaurant.getMeals();
                System.out.println("choose the meal you would like to add");
                int AddChoice = scanner.nextInt();
                order.addItems(restaurant.getMeals().get(AddChoice));
            }

            else {
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
    	List<Restaurant> query = entityManager.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();
        return Response.ok().entity(query).build();
    }
}


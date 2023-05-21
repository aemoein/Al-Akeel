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
@RolesAllowed("Customer")
@PersistenceContext(unitName = "databaseConnection")
public class CustomerControl {
	
	EntityManager entityManager;
	orderControl orderControl = new orderControl();

    @POST
    @Path("/create-order/{restaurantId}/{runnerId}/")
    public Response createOrder(@PathParam("restaurantId") long restaurantId, @PathParam("runnerId") long runnerId) {
    	Scanner scanner = new Scanner(System.in);
    	User OrderUser = UserCredentials.currentUser;
    	float totalprice = 0;
    	
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setfk_runnerId(runnerId);
        order.setUserId(OrderUser.getId());
        boolean addtoOrder = true;
        
        while(addtoOrder) {
        	orderControl.getRestaurant(order.getResturantId()).getMeals();
            System.out.println("choose the meal you would like to add");
            int AddChoice = scanner.nextInt();
            order.addItems(orderControl.getRestaurant(order.getResturantId()).getMeals().get(AddChoice-1));
            totalprice =+ orderControl.getRestaurant(order.getResturantId()).getMeals().get(AddChoice-1).getPrice();
            
            System.out.println("Would you like to add more Meals? 1. Yes 2. No : ");
            int Cont = scanner.nextInt();
            
            if (Cont == 2)
            {
            	addtoOrder = false;
            }      
        }
        
        order.setTotalPrice(totalprice);
        
        entityManager.persist(order);
        return Response.ok().build();
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
    	
            if (choice == 1) {
                orderControl.getOrder(orderId).getItemsArray();
                System.out.println("choose the meal you would like to remove");
                int removeChoice = scanner.nextInt();
                orderControl.getOrder(orderId).getItemsArray().remove(removeChoice-1);
            }

            else if (choice == 2) {
                orderControl.getRestaurant(orderControl.getOrder(orderId).getResturantId()).getMeals();
                System.out.println("choose the meal you would like to add");
                int AddChoice = scanner.nextInt();
                orderControl.getOrder(orderId).addItems(orderControl.getRestaurant(orderControl.getOrder(orderId).getResturantId()).getMeals().get(AddChoice));
            }

            else {
                    entityManager.persist(orderControl.getOrder(orderId));
            }
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/cancel-order/{orderId}")
    public Response cancelOrder(@PathParam("orderId") long orderId) {
    	orderControl.getOrder(orderId).setOrderStatus("Cancelled");
    	entityManager.persist(orderControl.getOrder(orderId));
        return Response.ok().build();
    }

    @GET
    @Path("/list-restaurants")
    public Response listRestaurants() {
    	List<Restaurant> query = entityManager.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();
        return Response.ok().entity(query).build();
    }
}


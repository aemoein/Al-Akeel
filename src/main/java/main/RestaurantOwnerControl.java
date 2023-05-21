package main;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@Path("/RestaurantControl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("Owner")
@PersistenceContext(unitName = "databaseConnection")
public class RestaurantOwnerControl {
	RestaurantControl restaurantControl = new RestaurantControl();
	
	@PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;
	
	@POST
    @Path("/createMenu/{mealNum}/{mealName}/{mealPrice}/{restaurantId}")
	public Response CreateMenu(@PathParam("mealNum") int mealNum, @PathParam("mealName") String mealName, @PathParam("mealPrice") float mealPrice, @PathParam("restaurantId") long restaurantId)
	{
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		for (int i=0; i<mealNum; i++)
		{
			restaurantControl.addMeal(mealName,mealPrice,restaurantId);
		}
		return Response.ok(restaurant.getMeals()).build();
	}
	
	@GET
    @Path("/getRestaurantDetails/{restaurantId}")
	public Response GetRestaurantDetails(@PathParam("restaurantId") Long restaurantId)
	{
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		List<Meal> mealsMenu = restaurant.getMeals();
		
		StringBuilder restaurantDetails = new StringBuilder();
		restaurantDetails.append("Here is your restaurant details:\n");
		restaurantDetails.append("Restaurant ID: ").append(restaurant.getId()).append("\n");
		restaurantDetails.append("Restaurant Name: ").append(restaurant.getName()).append("\n");
		restaurantDetails.append("List of meals in the restaurant:\n");

		for (Meal meals : mealsMenu) {
		    restaurantDetails.append(meals.getName()).append("\n");
		}

		String responseString = restaurantDetails.toString();
		return Response.ok(responseString).build();
	}
	
	@PUT
    @Path("/editRestaurant/{choice}/{mealId}/{Name}/{price}/{restaurantId}")
	public Response EditRestaurant(@PathParam("choice") int choice, @PathParam("mealId") Long mealId, @PathParam("Name") String Name, @PathParam("price") float price, @PathParam("restaurantId") Long restaurantId)
	{
		RestaurantControl restaurantControl = new RestaurantControl();
		
		if (choice == 1)
		{
			return Response.ok(restaurantControl.addMeal(Name, price, restaurantId)).build();
		}
		
		if (choice == 2)
		{
			return Response.ok(restaurantControl.removeMeal(mealId, restaurantId)).build();
		}
		else {
			return Response.ok("Error! Unexpected Choice the Avaliable choices are 1-2").build();
		}	
	}
	
	@GET
    @Path("/creatRestaurantReport/{restaurantId}")
	public Response CreateRestaurantReport(@PathParam("restaurantId") Long restaurantId)
	{
		StringBuilder output = new StringBuilder();

		// get total amount by restaurant
		TypedQuery<Float> totalAmountQuery = entityManager.createQuery("SELECT SUM(e.totalPrice) FROM Order e WHERE e.Restaurant.Id = :restaurantId AND e.status = 'Completed'", float.class);
		totalAmountQuery.setParameter("restaurantId", restaurantId);
		float totalAmount = totalAmountQuery.getSingleResult();
		output.append("The total amount is: ").append(totalAmount).append("\n");

		// number of completed orders
		TypedQuery<Integer> completedOrdersQuery = entityManager.createQuery("SELECT COUNT(c) FROM Order c WHERE c.Restaurant.Id = :restaurantId AND c.status = 'Completed'", int.class);
		completedOrdersQuery.setParameter("restaurantId", restaurantId);
		int completedOrders = completedOrdersQuery.getSingleResult();
		output.append("The number of completed orders is: ").append(completedOrders).append("\n");

		// number of canceled orders
		TypedQuery<Integer> canceledOrdersQuery = entityManager.createQuery("SELECT COUNT(c) FROM Order c WHERE c.Restaurant.Id = :restaurantId AND c.status = 'Canceled'", int.class);
		canceledOrdersQuery.setParameter("restaurantId", restaurantId);
		int canceledOrders = canceledOrdersQuery.getSingleResult();
		output.append("The number of canceled orders is: ").append(canceledOrders).append("\n");

		entityManager.close();

		String outputString = output.toString();
		return Response.ok(outputString).build();
	}	
}

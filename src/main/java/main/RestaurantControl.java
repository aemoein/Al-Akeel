package main;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/RestaurantControl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("Owner")
@PersistenceContext(unitName = "databaseConnection")
public class RestaurantControl 
{
	EntityManager entityManager;
	
	@POST
    @Path("/addMeal/{name}/{price}/{restaurantId}/")
	public Response addMeal(@PathParam("name") String name,@PathParam("price") float price,@PathParam("restaurantId") long restaurantId) {
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		String responseString;
		Meal meal = new Meal(name,price,restaurantId);
        restaurant.getMeals().add(meal);
        meal.setRestaurantId(restaurantId);
        entityManager.persist(meal);
        responseString = "The New Meal " + meal.getName() + " with the price of " + meal.getPrice() + " has been added to " + restaurant.getName();
        return Response.ok(responseString).build();
    }

	@DELETE
    @Path("/removeMeal/{mealId}/{restaurantId}/")
    public Response removeMeal(@PathParam("mealId") long mealId,@PathParam("restaurantId") long restaurantId) {
    	Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
    	Meal meal = entityManager.find(Meal.class, mealId);
    	String responseString;
    	
    	if (meal.getRestaurantId() == restaurantId)
    	{
    		restaurant.getMeals().remove(meal);
            meal.setRestaurantId(null);
            entityManager.remove(meal);
            responseString = "The" + meal.getName() + "Meal Requested have been removed";
    	}
    	
    	else {
			responseString = "The Meal Requested Doesnt belong to the Restaurant";
		}
        
        return Response.ok(responseString).build();
    }
}

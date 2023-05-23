package main;

import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless
@Path("/RestaurantOwnerControl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantOwnerControl {
	
    @PersistenceContext
    private EntityManager entityManager;
	
	@PUT
	@Path("/createMenu/{mealNum}")
	public Response createMenu(@PathParam("mealNum") int mealNum, @Context UriInfo uriInfo) {
	    List<Meal> meals = new ArrayList<>();

	    for (int i = 0; i < mealNum; i++) {
	        String mealName = uriInfo.getQueryParameters().getFirst("mealName" + i);
	        float mealPrice = Float.parseFloat(uriInfo.getQueryParameters().getFirst("mealPrice" + i));
	        long restaurantId = Long.parseLong(uriInfo.getQueryParameters().getFirst("restaurantId" + i));

	        Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);

	        Meal meal = new Meal(mealName, mealPrice, restaurantId);
	        restaurant.getMeals().add(meal);
	        entityManager.persist(meal);
	        entityManager.persist(restaurant);

	        meals.add(meal);
	    }

	    return Response.ok(meals).build();
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
		    restaurantDetails.append(meals.getName()).append(" ").append(meals.getPrice()).append("\n");
		}

		String responseString = restaurantDetails.toString();
		return Response.ok(responseString).build();
	}
	
	@POST
    @Path("/AddRestaurant/{Name}")
	public Response AddRestaurant(@PathParam("Name") String Name )
	{
		User user = UserCredentials.currentUser;
		Restaurant restaurant = new Restaurant(user.getId(), Name);
		entityManager.persist(restaurant);
		return Response.ok("The Restaurant Name is :"+restaurant.getName() + " And has an id of: " + restaurant.getId()).build();
	}
	
	@PUT
    @Path("/editRestaurant/{choice}/{mealId}/{Name}/{price}/{restaurantId}")
	public Response EditRestaurant(@PathParam("choice") int choice, @PathParam("mealId") Long mealId, @PathParam("Name") String Name, @PathParam("price") float price, @PathParam("restaurantId") Long restaurantId)
	{
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		if (choice == 1)
		{
			Meal meal = new Meal(Name, price,restaurantId);
	        restaurant.getMeals().add(meal);
	        entityManager.persist(meal);
	        entityManager.persist(restaurant);
			return Response.ok(restaurant.getMeals()).build();
		}
		
		if (choice == 2)
		{
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

	@GET
    @Path("/my-restaurants")
    public Response getMyResturants() {
        long ownerId = UserCredentials.currentUser.getId();
        TypedQuery<Restaurant> query = entityManager.createQuery("SELECT r FROM Restaurant r WHERE r.ownerId = :ownerId", Restaurant.class);
        query.setParameter("ownerId", ownerId);
        List<Restaurant> restaurants = query.getResultList();

        if (restaurants.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No restaurants found for the current owner.").build();
        } else {
            return Response.ok(restaurants).build();
        }
    }

}

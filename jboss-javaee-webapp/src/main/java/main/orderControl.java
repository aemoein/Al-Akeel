package main;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/orderControl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class orderControl {
	
    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("/getRestaurant/{restaurantId}")
    public Response getRestaurant(@PathParam("restaurantId") long restaurantId) {
        return Response.ok(entityManager.find(Restaurant.class, restaurantId)).build();
    }

    @GET
    @Path("/getMeal/{mealId}")
    public Response getMeal(@PathParam("mealId") long mealId) {
        return Response.ok(entityManager.find(Meal.class, mealId)).build();
    }

    @GET
    @Path("/getMealsList")
    public Response getMeals() {
        return Response.ok(entityManager.createQuery("SELECT m FROM Meal m", Meal.class).getResultList()).build();
    }
    
    @GET
    @Path("/getOrder/{orderId}")
    public Response getOrder(@PathParam("orderId") long orderId) { 
        return Response.ok(entityManager.find(Order.class, orderId)).build();
    }
}
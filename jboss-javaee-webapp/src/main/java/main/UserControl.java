package main;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserControl {
	
    @PersistenceContext
	private EntityManager entityManager;

    @POST
    @Path("/login/{email}/{password}")
    public Response login(@PathParam("email") String email, @PathParam("password") String password) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        
        try {
            User user = query.getSingleResult();
            
            if (user.getPassword().equals(password)) {
                // Email and password match, return 200 OK
            	UserCredentials.currentUser = user;
                return Response.ok(user.getRole()).build();
            } else {
                // Password is incorrect, return an error response
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            // User with the provided email not found, return an error response
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

	
	@POST
	@Path("/signup/{name}/{email}/{password}/{role}")
	public Response signUp(@PathParam("name") String name, @PathParam("email") String email, @PathParam("password") String password, @PathParam("role") String role) {
	    if (role.equalsIgnoreCase("runner")) {
	        // Create a new Runner object and set the properties
	        Runner runner = new Runner();
	        runner.setName(name);
	        runner.setEmail(email);
	        runner.setPassword(password);
	        runner.setRole(role);
	        runner.setStatus(false); // Assuming initial status is false

	        // Persist the Runner object into the "Runner" table
	        entityManager.persist(runner);
	    } else {
	        // Create a new User object and set the properties
	        User user = new User();
	        user.setName(name);
	        user.setEmail(email);
	        user.setPassword(password);
	        user.setRole(role);
	        

	        // Persist the User object into the "User" table
	        entityManager.persist(user);
	    }

	    return Response.ok().build(); // SignUp successful
	}
	

	@POST
	@Path("/DeliveryFees/{fees}/{email}")
	public Response promptForDeliveryFees(@PathParam("fees") float fees, @PathParam("email") String email) {
		Runner runner = entityManager.find(Runner.class, email);
		runner.setFee(fees);
	    entityManager.persist(runner);
	    return Response.ok().build();
	}
	
	@GET
	@Path("/getAllUsers")
	public Response getAllUsers() {
	    TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
	    List<User> users = query.getResultList();

	    StringBuilder responseBuilder = new StringBuilder();
	    for (User user : users) {
	        responseBuilder.append("Username: ").append(user.getName())
	            .append(", Email: ").append(user.getEmail())
	            .append(", Role: ").append(user.getRole())
	            .append("\n");
	    }

	    String response = responseBuilder.toString();

	    return Response.ok(response).build();
	}
	
	@GET
	@Path("/getCurrentUser")
	public Response getCurrentUser() {
	    User user = UserCredentials.currentUser;

	    StringBuilder responseBuilder = new StringBuilder();
	    
        responseBuilder.append("Username: ").append(user.getName())
            .append(", Email: ").append(user.getEmail())
            .append(", Role: ").append(user.getRole())
            .append("\n");

	    String response = responseBuilder.toString();

	    return Response.ok(response).build();
	}

	
	void openApp(String role) {
		try {
		    if (role.equals("Customer")) {
		        // Logic for opening the app for a customer
		    	CustomerInterface customerInterface = new CustomerInterface();
		    	customerInterface.display();
		    	
		    } 
		    
		    else if (role.equals("Owner")) {
		        // Logic for opening the app for an owner
		    	OwnerInterFace ownerInterFace = new OwnerInterFace();
		    	ownerInterFace.display();
		    } 
		    
		    else if (role.equals("Runner")) {
		        // Logic for opening the app for a runner
		    	RunnerInterface runnerInterface = new RunnerInterface();
		    	runnerInterface.display();
		    } 
		    
		    else {
		        throw new RuntimeException("Invalid role: " + role);
		    }
		} catch (IllegalArgumentException e) {
	        // Handle the exception here
	        System.out.println("Error: " + e.getMessage());
	        // Perform any necessary error handling or logging
	    }
		
	}

	
}

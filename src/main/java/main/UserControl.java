package main;

import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.Scanner;


@Stateless
@Path("/user")
public class UserControl {
	
	@PersistenceContext
	private EntityManager entityManager;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserCredentials credentials) {
	    String email = credentials.getEmail();
	    String password = credentials.getPassword();
	    
	    // Create a query to check if a user with the given email and password exists
	    Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
	    
	    query.setParameter("email", email);
	    query.setParameter("password", password);

	    try {
	        // Execute the query and get the result
	        User user = (User) query.getSingleResult();
	        
	        // If the query returns a user, it means the login is successful
	        openApp(user.getRole());
	        return Response.ok().build();
	    } catch (NoResultException e) {
	        // If no user is found, return an unauthorized response
	        return Response.status(Response.Status.UNAUTHORIZED).build();
	    }
	}

	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signUp(User userData) {
	    String name = userData.getName();
	    String email = userData.getEmail();
	    String password = userData.getPassword();
	    String role = userData.getRole();

	    if (role.equalsIgnoreCase("runner")) {
	        // Prompt the user for the delivery fees
	        float deliveryFees = promptForDeliveryFees();

	        // Create a new Runner object and set the properties
	        Runner runner = new Runner();
	        runner.setName(name);
	        runner.setEmail(email);
	        runner.setPassword(password);
	        runner.setRole(role);
	        runner.setStatus(false); // Assuming initial status is false
	        runner.setFee(deliveryFees);

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
	

	private float promptForDeliveryFees() {
		Scanner scanner = new Scanner(System.in);
	    
	    System.out.print("Please set the minimum fees for the next trip: ");
	    float fees = scanner.nextFloat();
	    
	    scanner.nextLine(); // Consume the newline character
	    
	    return fees;
	}

	
	void openApp(String role) {
		try {
		    if (role.equals("Customer")) {
		        // Logic for opening the app for a customer
		    	
		    	
		    } 
		    
		    else if (role.equals("Owner")) {
		        // Logic for opening the app for an owner
		    } 
		    
		    else if (role.equals("Runner")) {
		        // Logic for opening the app for a runner
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

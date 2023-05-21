package main;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Scanner;

public class UserControl {
	
	@PersistenceContext
	private EntityManager entityManager;

	public boolean login(String email, String password) {
	    // Create a query to check if a user with the given name and password exists
	    Query query = entityManager.createQuery("SELECT u.role FROM User u WHERE u.email = email AND u.password = password");
	    
	    query.setParameter("name", email);
	    query.setParameter("password", password);

	    // Execute the query and get the result
	    User user = (User) query.getSingleResult();
	    
	    // If the query returns a user, it means the login is successful
	    // Otherwise, an exception would be thrown, and we can catch it to handle the failed login
	    if (user != null) {
	    	openApp(user.getRole());
	        return true;
	    } else {
	        return false;
	    }

	}

	
	boolean SignUp(String name,String email, String password, String role) {
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

	    return true; // SignUp successful
	}
	

	private float promptForDeliveryFees() {
		Scanner scanner = new Scanner(System.in);
	    
	    System.out.print("Please set the minimum fees for the next trip: ");
	    float fees = scanner.nextFloat();
	    
	    scanner.nextLine(); // Consume the newline character
	    
	    return fees;
	}

	

	void openApp(String Role) {
		//open interface aka boundary class depending on the role
		//Please update the class diagram
	}
}

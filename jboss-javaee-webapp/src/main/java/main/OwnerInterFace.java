package main;

import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.Response;

public class OwnerInterFace implements Display {
	
	Scanner scanner = new Scanner(System.in);
	Response response ;
	Object entity;
	
	@Override
	public void display() {
	    int choice;
	    boolean cond = true;
	    RestaurantOwnerControl restaurantOwnerControl = new RestaurantOwnerControl();
	    while (cond == true)
	    {
	    	System.out.println("Choose a function to use:");
	        System.out.println("1. Create Menu");
	        System.out.println("2. Get Restaurant Details");
	        System.out.println("3. Add Restaurant");
	        System.out.println("4. Edit Restaurant");
	        System.out.println("5. Create Restaurant Report");
	        System.out.println("6. exit");
	        System.out.print("Enter your choice: ");
	        
	        choice = scanner.nextInt();
	        
	        switch (choice) 
	        {
			case 1:
			{
				System.out.println("Creating a menu");
				Restaurant restaurant = new Restaurant();
				List<Meal> meals = restaurant.getMeals();
				for(int i = 0; i<meals.size() ;i++) {
					System.out.println(meals.get(i));
				}
				break;
			}
			case 2:
			{
				System.out.println("Get Restaurant Details");
				System.out.println("Enter the restuarant id");
				long resId = scanner.nextLong();
				response =	restaurantOwnerControl.GetRestaurantDetails(resId);
				entity = response.getEntity();
				System.out.println(entity);
				break;
			}
			case 3:
			{
				System.out.println("Add Restaurant");
				System.out.println("Enter the Owner id");
				long ownerId = scanner.nextLong();
				System.out.println("Enter the restuarant Name");
				String resName = scanner.next();
				response = restaurantOwnerControl.AddRestaurant(ownerId,resName);
				entity = response.getEntity();
				System.out.println(entity);
				break;
			}
			case 4:
			{
				System.out.println("Edit Restaurant");
				System.out.println("1. add meal" 
						+ "add meal");
				int ch = scanner.nextInt();
				System.out.println("Enter meal id: ");
				long mealId = scanner.nextLong();
				System.out.println("Enter mael name: ");
				String name = scanner.next();
				System.out.println("Enter mael price: ");
				float price = scanner.nextFloat();
				System.out.println("Enter restaurant id: ");
				long resId = scanner.nextLong();
				response = restaurantOwnerControl.EditRestaurant(ch,mealId,name,price,resId);
				entity = response.getEntity();
				System.out.println(entity);
				break;
				
			}
			case 5:
			{
				 System.out.println("Create Restaurant Report");
				 System.out.println("Enter restaurant id: ");
				 long resId = scanner.nextLong();
				 response = restaurantOwnerControl.CreateRestaurantReport(resId);
				 entity = response.getEntity();
				 System.out.println(entity);
				 break;
			}
			
			case 6:
			{
				// Exit case
				cond = false;
				break;
			}

			default:
				break;
			}
	    }
	}	
}

package main;

import java.util.List;
import java.util.Scanner;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class RestaurantOwnerControl {
	RestaurantControl restaurantControl = new RestaurantControl();
	
	@PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;
	
	Scanner sc=new Scanner(System.in);
	
	public List<Meal> CreateMenu(long restaurantId)
	{
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		System.out.print("eneter the number of meals you want add to menu: ");
		int numMeals = sc.nextInt();
		
		for (int i=0; i<numMeals; i++)
		{
			System.out.println("enetr the name of the meal: ");
			String mealName = sc.next();
			
			System.out.println("enetr the price of the meal: ");
			Float mealPrice = sc.nextFloat();
			
			restaurantControl.addMeal(mealName,mealPrice,restaurantId);
		}
		
		return restaurant.getMeals();	
	}
	
	public void GetRestaurantDetails(Long restaurantId)
	{
		
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		List<Meal> mealsMenu = restaurant.getMeals();
		
		System.out.println("Here is your restuarant details: ");
		System.out.println("Restuarnt ID: " + restaurant.getId());
		System.out.println("Restuarnt Name: " + restaurant.getName());
		System.out.println("list of meals in the restaurant: ");
		
		for (Meal meals : mealsMenu)
		{
			System.out.println(meals.getName());
		}
	}
	
	public void EditRestaurant(Long restaurantId, Long mealId)
	{
		
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		
		System.out.println("What Would you like to Change?");
		System.out.println("1. Restaurant Name");
		System.out.println("2. Menu");
		System.out.println("3. Exit");
		int choice = sc.nextInt();
		
		switch (choice) {
		case 1:
			System.out.println("The Restaurnt's name is:" + restaurant.getName());
			System.out.println("Enter the new name: ");
			String NewName = sc.nextLine();
			restaurant.setName(NewName);
			System.out.println("The New Restaurnt name is:" + restaurant.getName());
			break;
			
		case 2:
			
			break;
			
		case 3:
			
			break;

		default:
			break;
		}
	}
	
	public void CreateRestaurantReport(Long restaurantId)
	{
		// get total amount by restaurant 
		TypedQuery<Float> totalAmountQuery = entityManager.createQuery("SELECT SUM(e.totalPrice) FROM Order e WHERE e.Restaurant.Id = :restaurantId AND e.status = 'Completed'", float.class);
		totalAmountQuery.setParameter("restaurantId", restaurantId);
		float totalAmount = totalAmountQuery.getSingleResult();
		System.out.println("the total amount are: " + totalAmount);
		
		// number of completed orders 
		TypedQuery<Integer> completedOrdersQuery = entityManager.createQuery("SELECT COUNT(c) FROM Order c WHERE c.Restaurant.Id = :restaurantId AND c.status = 'Completed'", int.class);
		completedOrdersQuery.setParameter("restaurantId", restaurantId);
	    int completedOrders = completedOrdersQuery.getSingleResult();
	    System.out.println("the number of completed orders are: " + completedOrders);
	    
	    //// number of canceled orders 
	    TypedQuery<Integer> canceledOrdersQuery = entityManager.createQuery("SELECT COUNT(c) FROM Order c WHERE c.Restaurant.Id = :restaurantId AND c.status = 'Canceled'", int.class);
		canceledOrdersQuery.setParameter("restaurantId", restaurantId);
	    int canceledOrders = completedOrdersQuery.getSingleResult();
	    System.out.println("the number of completed orders are: " + canceledOrders);
	    entityManager.close();
	}
	
	
}

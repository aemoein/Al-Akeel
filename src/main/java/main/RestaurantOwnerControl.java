package main;

import java.util.List;
import java.util.Scanner;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class RestaurantOwnerControl {
	
	@PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;
	
	Scanner sc=new Scanner(System.in);
	
	public void CreateMenu()
	{
		System.out.print("enter the id of the restaurant you want create menu for: ");
		long restaurantId = sc.nextInt();
		Restaurant restaurant = entityManager.find(Restaurant.class, restaurantId);
		System.out.print("enetr the number of meals you want add to menu: ");
		int numMeals = sc.nextInt();
		while (numMeals !=0)
		{
			System.out.println("enetr the name of the meal: ");
			String mealName = sc.next();
			System.out.println("enetr the price of the meal: ");
			Float mealPrice = sc.nextFloat();
			Meal newMeal = new Meal(mealName,mealPrice,restaurantId);
			restaurant.addMeal(newMeal);
			numMeals --;
		}
	}
	
	public void GetRestaurantDetails(Long restaurantId)
	{
		List<Meal> restaurant;
		restaurant = entityManager.find(Restaurant.class, restaurantId).getMeals();
		System.out.println("Here is your restuarant details: ");
		System.out.println("Restuarnt ID: " + entityManager.find(Restaurant.class, restaurantId).getId());
		System.out.println("Restuarnt Name: " + entityManager.find(Restaurant.class, restaurantId).getName());
		System.out.println("list of meals in the restaurant");
		for (Meal meals : restaurant)
		{
			System.out.println(meals.getName());
		}
	}
	
	public void EditRestaurant(Long restaurantId, Long mealId)
	{
		
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

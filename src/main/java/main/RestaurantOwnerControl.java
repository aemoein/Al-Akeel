package main;

import java.awt.MenuItem;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RestaurantOwnerControl {
	
	@PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;
	private List<Meal> menuItems;

	
	public void CreateMenu()
	{
		menuItems = new ArrayList<>();
		
	}
	
	public void GetRestaurantDetails(Long restaurantId)
	{
		Restaurant restaurant;
		
			restaurant = entityManager.find(Restaurant.class, restaurantId);
			if (restaurant != null)
			{
				System.out.println("Here is your restuarant details: ");
				System.out.println("Restuarnt ID: " + restaurant.getId());
				System.out.println("Restuarnt Name: " + restaurant.getName());
				System.out.println("list of meals in the restaurant");
				for (Meal meals : restaurant.getMeals())
				{
					System.out.println(meals.getName());
				}
			}
			else 
			{
				System.out.println("Restuarant not found");
			}
	}
	
	public void EditRestaurant(int restaurantId)
	{
		
	}
	
	public void CreateRestaurantReport(int restaurantId)
	{
		
	}
	
	
}

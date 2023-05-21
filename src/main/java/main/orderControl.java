package main;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class orderControl {
    @PersistenceContext(unitName = "databaseConnection")
    private EntityManager entityManager;

    public Restaurant getRestaurant(long restaurantId) {
        return entityManager.find(Restaurant.class, restaurantId);
    }

    public Meal getMeal(int mealId) {
        return entityManager.find(Meal.class, mealId);
    }

    public List<Meal> getMeals() {
        return entityManager.createQuery("SELECT m FROM Meal m", Meal.class)
                .getResultList();
    }
}


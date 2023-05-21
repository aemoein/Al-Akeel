package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
	
	@Column(name = "ownerId")
    private Long ownerId;
	
	@Column(name = "name")
	private String Name;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Meal> meals;
	
	// default constructor 
	public Restaurant() {
        meals = new ArrayList<>();
    }
	
	public Restaurant(Long Id , Long ownerId, String Name)
	{
		this.Name = Name;
        this.ownerId = ownerId;
        meals = new ArrayList<>();
	}
	
	public void setName(String name)
	{
		this.Name = name;
	}
	public String getName()
	{
		return this.Name;
	}
	
	
	public void setId(long id)
	{
		this.Id=id;
	}
	public long getId()
	{
		return this.Id;
	}
	
	public void setOwnerId(long ownerId)
	{
		this.ownerId = ownerId;
	}
	public long getOwnerId()
	{
		return this.ownerId;
	}
    
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}

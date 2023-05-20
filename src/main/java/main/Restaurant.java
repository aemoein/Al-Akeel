package main;

import java.sql.RowId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

@Entity
public class Restaurant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
	@Column(name = "ownerId")
    private Long ownerId;
	@Column(name = "name")
	private String Name;
	
	private Meal meal = new Meal();
	
	// default constructor 
	public Restaurant() {}
	
	public Restaurant(Long Id , Long OwnerId, String resName)
	{
		this.Id = Id;
		this.ownerId = OwnerId;
		this.Name = resName;
	}
	
	public void setName(String name)
	{
		this.Name = name;
	}
	public String getNmString()
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
	public long getownerId()
	{
		return this.ownerId;
	}
	
	public void addMeal(Meal meal)
	{
		// add meal to the menu 
		
		
		// add the menu to the DB
		// creating entity manger 
		EntityManagerFactory entityManger = Persistence.createEntityManagerFactory("");
	}
	
	public void removeMeal(Meal meal)
	{
		
		// remove meal to the menu 
		
		
		// remove the menu to the DB
		// creating entity manger 
		EntityManagerFactory entityManger = Persistence.createEntityManagerFactory("");
	}
}

package main;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
 
@Entity
@Table(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "items",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> items = new ArrayList<>();

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "fk_userId")
    private Long userId;

    @Column(name = "fk_runnerId")
    private Long runnerId;

    @Column(name = "fk_restaurantId")
    private Long restaurantId;

    @Column(name = "order_status")
    private String orderStatus;

    public Order() {
    }

    // Constructor
    public Order(float totalPrice, Long userId, Long runnerId, Long restaurantId, String orderStatus) {
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.runnerId = runnerId;
        this.restaurantId = restaurantId;
        this.orderStatus = orderStatus;
    }
    

    public void setId(long id) {
		this.id = id;
	}

	public void setItemsArray(List<Meal> item) {
		this.items=item;
	} 

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setfk_runnerId(long runnerId) {
		this.runnerId = runnerId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public void setOrder_status(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getId() {return this.id;}

	public List<Meal> getItemsArray() {return this.items;} 

	public float getTotalPrice() {return this.totalPrice;}

	public long getRunnerId() {return this.runnerId;}

	public long getResturantId() {return this.restaurantId;}

	public String getOrder_status() {return this.orderStatus;}

	public void addItems(Meal meal) {
		this.items.add(meal);
	}

	public void RemoveItems(Meal meal) {
		this.items.remove(meal);
	}
    
}
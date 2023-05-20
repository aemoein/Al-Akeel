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
import javax.persistence.JoinColumn;
 
@Entity
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
    
}
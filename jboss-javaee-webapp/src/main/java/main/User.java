package main;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public User() {
    }

    // Constructor
    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getEmail() {return this.email;}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
}
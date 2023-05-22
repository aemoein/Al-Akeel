package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Runner")
public class Runner extends User {
	@Column(name = "status")
	boolean Status;
	@Column(name = "delivery_fees")
	float delivery_fees;
	
	public Runner(){
	}
	
	public Runner(float delivery_fees, boolean Status) {
		this.delivery_fees = delivery_fees;
		this.Status = Status;
	}
	
	public void setStatus(boolean Status) {
		this.Status = Status;
	}
	
	public void setFee(float delivery_fees) {
		this.delivery_fees = delivery_fees;
	}
	
	public float getFees() {return this.delivery_fees;}
	public boolean getStatus() {return this.Status;}
}
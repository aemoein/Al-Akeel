package main;

import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.print.CancelablePrintJob;
import javax.validation.constraints.Null;
import javax.ws.rs.core.Response;

public class BankMisrnterface {
	Scanner scanner = new Scanner(System.in);
	BankMisrPayment bankMisrPayment;
	public Response response;
	public Object entity ;
	int cont;
	
	public void Pay() {
		System.out.println("Welcom to the Bank Misr online payment");
		System.out.println("to continue press 1, to cancel press 2.");
		cont = scanner.nextInt();
		if(cont == 2) {
			System.out.print("Canceling payment");
			return;
		}
		
		do {
		System.out.println("Please enter the credit card number:");
		String cridetNumber = scanner.nextLine();
		response = bankMisrPayment.validateCreditCard(cridetNumber);
		entity = response.getEntity();
		System.out.println(entity);} while(!entity.equals("Credit card number is invalid."));
		
		do {
		System.out.println("please enter the CVV:");
		String cvv = scanner.nextLine();
		response = bankMisrPayment.validateCVV(cvv);
		entity = response.getEntity();
		System.out.println(entity);}while(entity.equals("CVV is invalid."));
		
		do {
		System.out.println("please enter the month of expiry:");
		int month = scanner.nextInt();
		System.out.println("please enter the year of expiry:");
		int year = scanner.nextInt();
		response = bankMisrPayment.validateYear(month, year);
		entity = response.getEntity();
		System.out.println(entity);}while(entity.equals("Year or month are invalid."));
		
		System.out.println("to continue press 1, to cancel press 2.");
		cont = scanner.nextInt();
		do {
		switch(cont) {
		case 1:
			System.out.println("Complreting Payment");
			
			break;
		case 2:
			System.out.println("Canceling payment");
			return;
		default:
			System.out.println("invalid input");	
		}}while(cont != 1 || cont !=2);
	}
}

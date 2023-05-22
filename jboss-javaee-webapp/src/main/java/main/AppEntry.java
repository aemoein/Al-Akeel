package main;

import java.util.Scanner;

public class AppEntry {
	UserControl userControl = new UserControl();
	Scanner myScanner = new Scanner(System.in);
	boolean run = true;

	public AppEntry(){
		
	};
	
	public void Display() {
		String name,password,pass2,role,Email;
		System.out.println("Welcome to El-Akeel online orders, where flavor meets convenience!");
		System.out.println("you need to sign in or sign up to continue");
		System.out.println("Press (1) to log-in, (2) to sign-up.");
		int choice = myScanner.nextInt();
		while(run) {
			switch (choice) {
			case 1:
				System.out.print("Please enter your Email or user-name: ");
				name = myScanner.nextLine();
				System.out.print("Please enter your Password: ");
				password = myScanner.nextLine();
				userControl.login(name, password);
				break;
			case 2:
				
				System.out.print("Please enter your Email: ");
				Email = myScanner.nextLine();
				System.out.print("Please enter your name: ");
				name = myScanner.nextLine();
				System.out.print("Please enter your Password: ");
				password = myScanner.nextLine();
				
				do {
				    System.out.print("Please re-enter your Password: ");
				    pass2 = myScanner.nextLine();

				    if (!pass2.equals(password)) {
				        System.out.println("Passwords do not match. Please try again.");
				    }
				} while (!pass2.equals(password));
				
				do {
				    System.out.print("Choose your role (runner, owner, customer): ");
				    role = myScanner.nextLine();
				} while (!role.equals("runner") && !role.equals("owner") && !role.equals("customer"));
	
				
				userControl.signUp(name, Email, password, role);
				
				break;
	
			default:
				System.out.println("Please enter a valid input.");
				break;
			}
		}
	}
}

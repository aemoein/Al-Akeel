package main;


public class UserCredentials {
	private String email;
    private String password;
    public static User currentUser;

    // Getters and setters

    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void setCurrentUser(User currentUser) {
        UserCredentials.currentUser = currentUser;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (currentUser != null) {
            currentUser.setEmail(email);
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        if (currentUser != null) {
            currentUser.setPassword(password);
        }
    }
}

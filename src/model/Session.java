package model;

public class Session {
    private static Session instance;
    private User currentUser;
    
    // Private constructor for Singleton
    private Session() {
        this.currentUser = null;
    }
    
    // Get Singleton instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    
    // Login user
    public void login(User user) {
        this.currentUser = user;
    }
    
    // Logout user
    public void logout() {
        this.currentUser = null;
    }
    
    // Get current logged-in user
    public User getCurrentUser() {
        return currentUser;
    }
    
    // Check if user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
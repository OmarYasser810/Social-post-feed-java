package controller;

import model.DataStore;
import model.Session;
import model.User;

public class AuthController {
    private DataStore dataStore;
    private Session session;
    
    public AuthController() {
        this.dataStore = DataStore.getInstance();
        this.session = Session.getInstance();
    }
    
    // Login method
    public boolean login(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        
        // Authenticate user
        User user = dataStore.authenticateUser(username.trim(), password);
        
        if (user != null) {
            session.login(user);
            return true;
        }
        
        return false;
    }
    
    // Register method
    public String register(String username, String password, String confirmPassword, String name, String bio) {
        // Validate username
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty";
        }
        if (username.length() < 3) {
            return "Username must be at least 3 characters";
        }
        if (username.contains(" ")) {
            return "Username cannot contain spaces";
        }
        
        // Validate password
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }
        
        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        }
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty";
        }
        
        // Bio is optional, but set default if empty
        String userBio = (bio == null || bio.trim().isEmpty()) ? "No bio yet" : bio.trim();
        
        // Try to register
        boolean success = dataStore.registerUser(username.trim(), password, name.trim(), userBio);
        
        if (success) {
            return "SUCCESS";
        } else {
            return "Username already exists";
        }
    }
    
    // Logout method
    public void logout() {
        session.logout();
    }
    
    // Check if user is logged in
    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }
    
    // Get current user
    public User getCurrentUser() {
        return session.getCurrentUser();
    }
}
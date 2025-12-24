package controller;

import java.util.List;

import model.DataStore;
import model.Session;
import model.User;

public class UserController {
    private DataStore dataStore;
    private Session session;
    
    public UserController() {
        this.dataStore = DataStore.getInstance();
        this.session = Session.getInstance();
    }
    
    // Get current logged-in user
    public User getCurrentUser() {
        return session.getCurrentUser();
    }
    
    // Get user by ID
    public User getUserById(String userId) {
        return dataStore.getUserById(userId);
    }
    
    // Get user by username
    public User getUserByUsername(String username) {
        return dataStore.getUserByUsername(username);
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return dataStore.getAllUsers();
    }
    
    // Search users by query (username or name)
    public List<User> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllUsers();
        }
        return dataStore.searchUsers(query.trim());
    }
    
    // Update user profile
    public boolean updateProfile(String name, String bio) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        // Update user data
        currentUser.setName(name.trim());
        currentUser.setBio(bio != null ? bio.trim() : "");
        
        return true;
    }
    
    // Update profile photo
    public boolean updateProfilePhoto(javax.swing.ImageIcon photo) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        currentUser.setProfilePhoto(photo);
        return true;
    }
    
    // Follow a user
    public boolean followUser(User userToFollow) {
        User currentUser = getCurrentUser();
        if (currentUser == null || userToFollow == null) {
            return false;
        }
        
        // Can't follow yourself
        if (currentUser.equals(userToFollow)) {
            return false;
        }
        
        // Check if already following
        if (currentUser.isFollowing(userToFollow)) {
            return false;
        }
        
        dataStore.followUser(currentUser, userToFollow);
        return true;
    }
    
    // Unfollow a user
    public boolean unfollowUser(User userToUnfollow) {
        User currentUser = getCurrentUser();
        if (currentUser == null || userToUnfollow == null) {
            return false;
        }
        
        // Check if actually following
        if (!currentUser.isFollowing(userToUnfollow)) {
            return false;
        }
        
        dataStore.unfollowUser(currentUser, userToUnfollow);
        return true;
    }
    
    // Toggle follow/unfollow
    public boolean toggleFollow(User user) {
        User currentUser = getCurrentUser();
        if (currentUser == null || user == null) {
            return false;
        }
        
        if (currentUser.isFollowing(user)) {
            return unfollowUser(user);
        } else {
            return followUser(user);
        }
    }
    
    // Check if current user is following another user
    public boolean isFollowing(User user) {
        User currentUser = getCurrentUser();
        if (currentUser == null || user == null) {
            return false;
        }
        return currentUser.isFollowing(user);
    }
    
    // Get followers of a user
    public List<User> getFollowers(User user) {
        if (user == null) {
            return null;
        }
        return user.getFollowers();
    }
    
    // Get following list of a user
    public List<User> getFollowing(User user) {
        if (user == null) {
            return null;
        }
        return user.getFollowing();
    }
    
    // Get user statistics
    public UserStats getUserStats(User user) {
        if (user == null) {
            return null;
        }
        return new UserStats(
            user.getPostsCount(),
            user.getFollowersCount(),
            user.getFollowingCount()
        );
    }
    
    // Inner class for user statistics
    public static class UserStats {
        private int postsCount;
        private int followersCount;
        private int followingCount;
        
        public UserStats(int postsCount, int followersCount, int followingCount) {
            this.postsCount = postsCount;
            this.followersCount = followersCount;
            this.followingCount = followingCount;
        }
        
        public int getPostsCount() {
            return postsCount;
        }
        
        public int getFollowersCount() {
            return followersCount;
        }
        
        public int getFollowingCount() {
            return followingCount;
        }
    }
}
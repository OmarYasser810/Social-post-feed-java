package model;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {
    // Singleton instance
    private static DataStore instance;
    
    // Data collections
    private List<User> users;
    private List<Post> posts;
    private int userIdCounter;
    private int postIdCounter;
    private int commentIdCounter;
    
    // Private constructor for Singleton
    private DataStore() {
        users = new ArrayList<>();
        posts = new ArrayList<>();
        userIdCounter = 1;
        postIdCounter = 1;
        commentIdCounter = 1;
        initializeHardcodedData();
    }
    
    // Get Singleton instance
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }
    
    // Initialize hardcoded data
    private void initializeHardcodedData() {
        // Create hardcoded users
        User user1 = new User("1", "john_doe", "password123", "John Doe", "Coffee lover ☕ | Travel enthusiast");
        User user2 = new User("2", "jane_smith", "password123", "Jane Smith", "Tech enthusiast 💻 | Developer");
        User user3 = new User("3", "bob_wilson", "password123", "Bob Wilson", "Photographer 📷 | Nature lover");
        User user4 = new User("4", "alice_johnson", "password123", "Alice Johnson", "Fitness coach 💪 | Healthy living");
        User user5 = new User("5", "mike_brown", "password123", "Mike Brown", "Foodie 🍕 | Chef in training");
        
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        
        userIdCounter = 6;
        
        // Set up follow relationships
        // John follows Jane and Bob
        user1.addFollowing(user2);
        user2.addFollower(user1);
        user1.addFollowing(user3);
        user3.addFollower(user1);
        
        // Jane follows John, Bob, and Alice
        user2.addFollowing(user1);
        user1.addFollower(user2);
        user2.addFollowing(user3);
        user3.addFollower(user2);
        user2.addFollowing(user4);
        user4.addFollower(user2);
        
        // Bob follows everyone
        user3.addFollowing(user1);
        user1.addFollower(user3);
        user3.addFollowing(user2);
        user2.addFollower(user3);
        user3.addFollowing(user4);
        user4.addFollower(user3);
        user3.addFollowing(user5);
        user5.addFollower(user3);
        
        // Alice follows Jane and Mike
        user4.addFollowing(user2);
        user2.addFollower(user4);
        user4.addFollowing(user5);
        user5.addFollower(user4);
        
        // Mike follows Alice and Bob
        user5.addFollowing(user4);
        user4.addFollower(user5);
        user5.addFollowing(user3);
        user3.addFollower(user5);
        
        // Create hardcoded posts
        Post post1 = new Post("1", user1, "Just had the most amazing cup of coffee at the new café downtown! ☕ Highly recommend the vanilla latte!");
        Post post2 = new Post("2", user2, "Finally finished my Java Swing project! 💻 It's amazing how much you can build with desktop applications.");
        Post post3 = new Post("3", user3, "Captured this beautiful sunset today 🌅 Nature never ceases to amaze me!");
        Post post4 = new Post("4", user4, "Morning workout complete! 💪 Remember, consistency is key to achieving your fitness goals.");
        Post post5 = new Post("5", user5, "Experimenting with a new pasta recipe today 🍝 Can't wait to share the results!");
        Post post6 = new Post("6", user1, "Planning my next travel destination ✈️ Thinking about visiting Japan. Any recommendations?");
        Post post7 = new Post("7", user2, "Learning about design patterns in software development. The Observer pattern is fascinating!");
        Post post8 = new Post("8", user3, "Just got a new camera lens! Time to test it out in the wild 📸");
        Post post9 = new Post("9", user4, "Healthy eating tip: Meal prep on Sundays saves so much time during the week! 🥗");
        Post post10 = new Post("10", user5, "Made homemade pizza from scratch! The dough turned out perfect 🍕");
        
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post9);
        posts.add(post10);
        
        // Add posts to users
        user1.addPost(post1);
        user1.addPost(post6);
        user2.addPost(post2);
        user2.addPost(post7);
        user3.addPost(post3);
        user3.addPost(post8);
        user4.addPost(post4);
        user4.addPost(post9);
        user5.addPost(post5);
        user5.addPost(post10);
        
        postIdCounter = 11;
        
        // Add some likes
        post1.addLike(user2);
        post1.addLike(user3);
        post1.addLike(user4);
        
        post2.addLike(user1);
        post2.addLike(user3);
        
        post3.addLike(user1);
        post3.addLike(user2);
        post3.addLike(user4);
        post3.addLike(user5);
        
        post4.addLike(user2);
        post4.addLike(user5);
        
        post5.addLike(user3);
        post5.addLike(user4);
        
        // Add some comments
        Comment comment1 = new Comment("1", user2, "Looks delicious! What café is this?");
        Comment comment2 = new Comment("2", user3, "I need to check this place out!");
        post1.addComment(comment1);
        post1.addComment(comment2);
        
        Comment comment3 = new Comment("3", user1, "Great work! I'd love to see the project.");
        post2.addComment(comment3);
        
        Comment comment4 = new Comment("4", user2, "Absolutely stunning! What camera did you use?");
        Comment comment5 = new Comment("5", user5, "Beautiful shot! 📷");
        post3.addComment(comment4);
        post3.addComment(comment5);
        
        Comment comment6 = new Comment("6", user5, "You're an inspiration! 💪");
        post4.addComment(comment6);
        
        Comment comment7 = new Comment("7", user4, "Recipe please! 🍝");
        post5.addComment(comment7);
        
        commentIdCounter = 8;
    }
    
    // User Management Methods
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public User getUserById(String userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
    
    public User authenticateUser(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    
    public boolean registerUser(String username, String password, String name, String bio) {
        // Check if username already exists
        if (getUserByUsername(username) != null) {
            return false;
        }
        
        String userId = String.valueOf(userIdCounter++);
        User newUser = new User(userId, username, password, name, bio);
        users.add(newUser);
        return true;
    }
    
    // Post Management Methods
    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }
    
    public Post getPostById(String postId) {
        return posts.stream()
                .filter(p -> p.getPostId().equals(postId))
                .findFirst()
                .orElse(null);
    }
    
    public List<Post> getPostsByUser(User user) {
        return posts.stream()
                .filter(p -> p.getAuthor().equals(user))
                .collect(Collectors.toList());
    }
    
    public List<Post> getFeedForUser(User user, boolean followingOnly) {
        if (followingOnly) {
            // Return posts from users that the current user follows
            return posts.stream()
                    .filter(p -> user.isFollowing(p.getAuthor()) || p.getAuthor().equals(user))
                    .collect(Collectors.toList());
        } else {
            // Return all posts (public feed)
            return new ArrayList<>(posts);
        }
    }
    
    public Post createPost(User author, String content, ImageIcon image) {
        String postId = String.valueOf(postIdCounter++);
        Post newPost = new Post(postId, author, content, image);
        posts.add(0, newPost); // Add to beginning for newest first
        author.addPost(newPost);
        return newPost;
    }
    
    public boolean deletePost(Post post) {
        post.getAuthor().removePost(post);
        return posts.remove(post);
    }
    
    // Comment Management Methods
    public Comment createComment(User author, Post post, String content) {
        String commentId = String.valueOf(commentIdCounter++);
        Comment newComment = new Comment(commentId, author, content);
        post.addComment(newComment);
        return newComment;
    }
    
    // Like Management Methods
    public void toggleLike(User user, Post post) {
        if (post.isLikedBy(user)) {
            post.removeLike(user);
        } else {
            post.addLike(user);
        }
    }
    
    // Follow Management Methods
    public void followUser(User follower, User userToFollow) {
        if (!follower.equals(userToFollow) && !follower.isFollowing(userToFollow)) {
            follower.addFollowing(userToFollow);
            userToFollow.addFollower(follower);
        }
    }
    
    public void unfollowUser(User follower, User userToUnfollow) {
        if (follower.isFollowing(userToUnfollow)) {
            follower.removeFollowing(userToUnfollow);
            userToUnfollow.removeFollower(follower);
        }
    }
    
    // Search Methods
    public List<User> searchUsers(String query) {
        String lowerQuery = query.toLowerCase();
        return users.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(lowerQuery) ||
                           u.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}
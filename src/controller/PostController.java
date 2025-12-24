package controller;

import javax.swing.ImageIcon;

import model.Comment;
import model.DataStore;
import model.Post;
import model.Session;
import model.User;

import java.util.List;

public class PostController {
    private DataStore dataStore;
    private Session session;
    
    public PostController() {
        this.dataStore = DataStore.getInstance();
        this.session = Session.getInstance();
    }
    
    // Get all posts (public feed)
    public List<Post> getAllPosts() {
        return dataStore.getAllPosts();
    }
    
    // Get feed for current user
    public List<Post> getFeed(boolean followingOnly) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null) {
            return dataStore.getAllPosts();
        }
        return dataStore.getFeedForUser(currentUser, followingOnly);
    }
    
    // Get posts by specific user
    public List<Post> getPostsByUser(User user) {
        if (user == null) {
            return null;
        }
        return dataStore.getPostsByUser(user);
    }
    
    // Get post by ID
    public Post getPostById(String postId) {
        return dataStore.getPostById(postId);
    }
    
    // Create a new post
    public Post createPost(String content, ImageIcon image) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        
        // Validate content
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        
        // Create post
        Post newPost = dataStore.createPost(currentUser, content.trim(), image);
        return newPost;
    }
    
    // Delete a post
    public boolean deletePost(Post post) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null || post == null) {
            return false;
        }
        
        // Only author can delete their post
        if (!post.getAuthor().equals(currentUser)) {
            return false;
        }
        
        return dataStore.deletePost(post);
    }
    
    // Like or unlike a post
    public boolean toggleLike(Post post) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null || post == null) {
            return false;
        }
        
        dataStore.toggleLike(currentUser, post);
        return true;
    }
    
    // Check if current user liked a post
    public boolean isLikedByCurrentUser(Post post) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null || post == null) {
            return false;
        }
        return post.isLikedBy(currentUser);
    }
    
    // Add a comment to a post
    public Comment addComment(Post post, String content) {
        User currentUser = session.getCurrentUser();
        if (currentUser == null || post == null) {
            return null;
        }
        
        // Validate content
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        
        Comment newComment = dataStore.createComment(currentUser, post, content.trim());
        return newComment;
    }
    
    // Get comments for a post
    public List<Comment> getComments(Post post) {
        if (post == null) {
            return null;
        }
        return post.getComments();
    }
    
    // Get like count for a post
    public int getLikeCount(Post post) {
        if (post == null) {
            return 0;
        }
        return post.getLikesCount();
    }
    
    // Get comment count for a post
    public int getCommentCount(Post post) {
        if (post == null) {
            return 0;
        }
        return post.getCommentsCount();
    }
    
    // Get users who liked a post
    public List<User> getUsersWhoLiked(Post post) {
        if (post == null) {
            return null;
        }
        return post.getLikes();
    }
}

package model;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String userId;
    private String username;
    private String password;
    private String name;
    private ImageIcon profilePhoto;
    private String bio;
    private List<User> followers;
    private List<User> following;
    private List<Post> posts;

    // Constructor
    public User(String userId, String username, String password, String name, ImageIcon profilePhoto, String bio) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    // Overloaded constructor without profile photo
    public User(String userId, String username, String password, String name, String bio) {
        this(userId, username, password, name, null, bio);
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(ImageIcon profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Utility Methods
    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public void removeFollower(User user) {
        followers.remove(user);
    }

    public void addFollowing(User user) {
        if (!following.contains(user)) {
            following.add(user);
        }
    }

    public void removeFollowing(User user) {
        following.remove(user);
    }

    public void addPost(Post post) {
        posts.add(0, post); // Add to beginning for newest first
    }

    public void removePost(Post post) {
        posts.remove(post);
    }

    public boolean isFollowing(User user) {
        return following.contains(user);
    }

    public int getFollowersCount() {
        return followers.size();
    }

    public int getFollowingCount() {
        return following.size();
    }

    public int getPostsCount() {
        return posts.size();
    }

    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", followersCount=" + getFollowersCount() +
                ", followingCount=" + getFollowingCount() +
                ", postsCount=" + getPostsCount() +
                '}';
    }
}

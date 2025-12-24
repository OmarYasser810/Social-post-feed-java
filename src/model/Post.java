package model;

import javax.swing.ImageIcon;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Post {
    private String postId;
    private User author;
    private String content;
    private ImageIcon image;
    private LocalDateTime timestamp;
    private List<User> likes;
    private List<Comment> comments;

    // Constructor
    public Post(String postId, User author, String content, ImageIcon image) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.image = image;
        this.timestamp = LocalDateTime.now();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // Overloaded constructor without image
    public Post(String postId, User author, String content) {
        this(postId, author, content, null);
    }

    // Getters and Setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Utility Methods
    public void addLike(User user) {
        if (!likes.contains(user)) {
            likes.add(user);
        }
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public boolean isLikedBy(User user) {
        return likes.contains(user);
    }

    public int getLikesCount() {
        return likes.size();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public int getCommentsCount() {
        return comments.size();
    }

    public String getFormattedTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(timestamp, now).toMinutes();
        long hours = java.time.Duration.between(timestamp, now).toHours();
        long days = java.time.Duration.between(timestamp, now).toDays();

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + "m ago";
        } else if (hours < 24) {
            return hours + "h ago";
        } else if (days < 7) {
            return days + "d ago";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            return timestamp.format(formatter);
        }
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", author=" + author.getUsername() +
                ", content='" + content + '\'' +
                ", likesCount=" + getLikesCount() +
                ", commentsCount=" + getCommentsCount() +
                ", timestamp=" + getFormattedTimestamp() +
                '}';
    }
}
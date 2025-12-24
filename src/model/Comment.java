package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Comment {
    private String commentId;
    private User author;
    private String content;
    private LocalDateTime timestamp;

    // Constructor
    public Comment(String commentId, User author, String content) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Utility Methods
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
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", author=" + author.getUsername() +
                ", content='" + content + '\'' +
                ", timestamp=" + getFormattedTimestamp() +
                '}';
    }
}
package view;

import model.*;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFeedFrame extends JFrame {
    private Session session;
    private PostController postController;
    private UserController userController;
    private DataStore dataStore;
    
    private JPanel feedPanel;
    private JScrollPane scrollPane;
    private JTextArea postTextArea;
    private JCheckBox followingOnlyCheckBox;
    
    public MainFeedFrame(Session session) {
        this.session = session;
        this.postController = new PostController();
        this.userController = new UserController();
        this.dataStore = DataStore.getInstance();
        
        initializeUI();
        loadFeed();
    }
    
    private void initializeUI() {
        setTitle("Social Feed - Welcome " + session.getCurrentUser().getName());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Top panel with user info and logout
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with feed
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel for creating posts
        JPanel createPostPanel = createPostPanel();
        add(createPostPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Left side - Welcome and user info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + session.getCurrentUser().getName());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel usernameLabel = new JLabel("@" + session.getCurrentUser().getUsername());
        usernameLabel.setForeground(new Color(200, 200, 200));
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        leftPanel.add(welcomeLabel);
        leftPanel.add(usernameLabel);
        
        // Right side - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        followingOnlyCheckBox = new JCheckBox("Following Only");
        followingOnlyCheckBox.setForeground(Color.WHITE);
        followingOnlyCheckBox.setOpaque(false);
        followingOnlyCheckBox.setFocusPainted(false);
        followingOnlyCheckBox.addActionListener(e -> loadFeed());
        
        JButton profileButton = new JButton("Profile");
        profileButton.setBackground(new Color(91, 120, 180));
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.addActionListener(e -> showProfile());
        
        JButton searchButton = new JButton("Search Users");
        searchButton.setBackground(new Color(91, 120, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> showSearchDialog());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(66, 183, 42));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadFeed());
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 78, 65));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> logout());
        
        buttonPanel.add(followingOnlyCheckBox);
        buttonPanel.add(searchButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        return topPanel;
    }
    
    private JPanel createPostPanel() {
        JPanel createPostPanel = new JPanel(new BorderLayout(5, 5));
        createPostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createPostPanel.setBackground(new Color(240, 242, 245));
        
        JLabel label = new JLabel("What's on your mind, " + session.getCurrentUser().getName().split(" ")[0] + "?");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
        postTextArea = new JTextArea(3, 40);
        postTextArea.setLineWrap(true);
        postTextArea.setWrapStyleWord(true);
        postTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        postTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane textScrollPane = new JScrollPane(postTextArea);
        
        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(59, 89, 152));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setFont(new Font("Arial", Font.BOLD, 13));
        postButton.addActionListener(e -> createPost());
        
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(label, BorderLayout.NORTH);
        topSection.add(textScrollPane, BorderLayout.CENTER);
        
        createPostPanel.add(topSection, BorderLayout.CENTER);
        createPostPanel.add(postButton, BorderLayout.EAST);
        
        return createPostPanel;
    }
    
    private void loadFeed() {
        feedPanel.removeAll();
        
        boolean followingOnly = followingOnlyCheckBox.isSelected();
        List<Post> posts = dataStore.getFeedForUser(session.getCurrentUser(), followingOnly);
        
        if (posts.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.WHITE);
            JLabel noPostsLabel = new JLabel(followingOnly ? 
                "No posts from people you follow. Try following more users!" : 
                "No posts yet. Be the first to post!");
            noPostsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noPostsLabel.setForeground(Color.GRAY);
            emptyPanel.add(noPostsLabel);
            feedPanel.add(emptyPanel);
        } else {
            for (Post post : posts) {
                JPanel postPanel = createPostCard(post);
                feedPanel.add(postPanel);
                feedPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        feedPanel.revalidate();
        feedPanel.repaint();
    }
    
    private JPanel createPostCard(Post post) {
        JPanel postCard = new JPanel();
        postCard.setLayout(new BoxLayout(postCard, BoxLayout.Y_AXIS));
        postCard.setBackground(Color.WHITE);
        postCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        postCard.setMaximumSize(new Dimension(850, Integer.MAX_VALUE));
        
        // Header with username, name and timestamp
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        authorPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(post.getAuthor().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel usernameLabel = new JLabel("@" + post.getAuthor().getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.GRAY);
        
        authorPanel.add(nameLabel);
        authorPanel.add(usernameLabel);
        
        JLabel timestampLabel = new JLabel(post.getTimestamp().toString());
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        timestampLabel.setForeground(Color.GRAY);
        
        headerPanel.add(authorPanel, BorderLayout.WEST);
        headerPanel.add(timestampLabel, BorderLayout.EAST);
        
        // Content
        JTextArea contentArea = new JTextArea(post.getContent());
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setOpaque(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 13));
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Image if exists
        JLabel imageLabel = null;
        if (post.getImage() != null) {
            imageLabel = new JLabel(post.getImage());
            imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        }
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionPanel.setOpaque(false);
        
        boolean isLiked = post.isLikedBy(session.getCurrentUser());
        JButton likeButton = new JButton((isLiked ? "❤️ " : "🤍 ") + post.getLikes().size() + " Like" + (post.getLikes().size() != 1 ? "s" : ""));
        likeButton.setFocusPainted(false);
        if (isLiked) {
            likeButton.setForeground(new Color(220, 78, 65));
        }
        likeButton.addActionListener(e -> {
            dataStore.toggleLike(session.getCurrentUser(), post);
            loadFeed();
        });
        
        JButton commentButton = new JButton("💬 " + post.getComments().size() + " Comment" + (post.getComments().size() != 1 ? "s" : ""));
        commentButton.setFocusPainted(false);
        commentButton.addActionListener(e -> showComments(post));
        
        actionPanel.add(likeButton);
        actionPanel.add(commentButton);
        
        // Delete button (only for post author)
        if (post.getAuthor().getUserId().equals(session.getCurrentUser().getUserId())) {
            JButton deleteButton = new JButton("🗑️ Delete");
            deleteButton.setForeground(Color.RED);
            deleteButton.setFocusPainted(false);
            deleteButton.addActionListener(e -> deletePost(post));
            actionPanel.add(deleteButton);
        }
        
        postCard.add(headerPanel);
        postCard.add(contentArea);
        if (imageLabel != null) {
            postCard.add(imageLabel);
        }
        postCard.add(actionPanel);
        
        return postCard;
    }
    
    private void createPost() {
        String content = postTextArea.getText().trim();
        
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Post content cannot be empty!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        dataStore.createPost(session.getCurrentUser(), content, null);
        postTextArea.setText("");
        loadFeed();
    }
    
    private void deletePost(Post post) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this post?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            dataStore.deletePost(post);
            loadFeed();
        }
    }
    
    private void showComments(Post post) {
        JDialog commentDialog = new JDialog(this, "Comments on " + post.getAuthor().getName() + "'s post", true);
        commentDialog.setSize(550, 450);
        commentDialog.setLocationRelativeTo(this);
        commentDialog.setLayout(new BorderLayout(10, 10));
        
        // Comments panel
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(Color.WHITE);
        
        if (post.getComments().isEmpty()) {
            JLabel noCommentsLabel = new JLabel("No comments yet. Be the first to comment!");
            noCommentsLabel.setFont(new Font("Arial", Font.ITALIC, 13));
            noCommentsLabel.setForeground(Color.GRAY);
            noCommentsLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            commentsPanel.add(noCommentsLabel);
        } else {
            for (Comment comment : post.getComments()) {
                JPanel commentCard = new JPanel();
                commentCard.setLayout(new BoxLayout(commentCard, BoxLayout.Y_AXIS));
                commentCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                commentCard.setBackground(Color.WHITE);
                commentCard.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
                
                JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                authorPanel.setOpaque(false);
                
                JLabel nameLabel = new JLabel(comment.getAuthor().getName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
                
                JLabel usernameLabel = new JLabel("@" + comment.getAuthor().getUsername());
                usernameLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                usernameLabel.setForeground(Color.GRAY);
                
                authorPanel.add(nameLabel);
                authorPanel.add(usernameLabel);
                
                JTextArea commentText = new JTextArea(comment.getContent());
                commentText.setEditable(false);
                commentText.setLineWrap(true);
                commentText.setWrapStyleWord(true);
                commentText.setOpaque(false);
                commentText.setFont(new Font("Arial", Font.PLAIN, 12));
                commentText.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
                
                commentCard.add(authorPanel);
                commentCard.add(commentText);
                
                commentsPanel.add(commentCard);
            }
        }
        
        JScrollPane commentsScroll = new JScrollPane(commentsPanel);
        commentsScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        // Add comment panel
        JPanel addCommentPanel = new JPanel(new BorderLayout(5, 5));
        addCommentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addCommentPanel.setBackground(new Color(240, 242, 245));
        
        JTextArea commentTextArea = new JTextArea(2, 30);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        commentTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton addCommentButton = new JButton("Comment");
        addCommentButton.setBackground(new Color(59, 89, 152));
        addCommentButton.setForeground(Color.WHITE);
        addCommentButton.setFocusPainted(false);
        addCommentButton.addActionListener(e -> {
            String commentContent = commentTextArea.getText().trim();
            if (!commentContent.isEmpty()) {
                dataStore.createComment(session.getCurrentUser(), post, commentContent);
                commentDialog.dispose();
                loadFeed();
            } else {
                JOptionPane.showMessageDialog(commentDialog, 
                    "Comment cannot be empty!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        addCommentPanel.add(new JLabel("Add a comment:"), BorderLayout.NORTH);
        addCommentPanel.add(new JScrollPane(commentTextArea), BorderLayout.CENTER);
        addCommentPanel.add(addCommentButton, BorderLayout.EAST);
        
        commentDialog.add(commentsScroll, BorderLayout.CENTER);
        commentDialog.add(addCommentPanel, BorderLayout.SOUTH);
        
        commentDialog.setVisible(true);
    }
    
    private void showProfile() {
        User currentUser = session.getCurrentUser();
        
        JDialog profileDialog = new JDialog(this, "Profile - " + currentUser.getName(), true);
        profileDialog.setSize(500, 400);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.setLayout(new BorderLayout(10, 10));
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        profilePanel.setBackground(Color.WHITE);
        
        // Name
        JLabel nameLabel = new JLabel(currentUser.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username
        JLabel usernameLabel = new JLabel("@" + currentUser.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Bio
        JTextArea bioArea = new JTextArea(currentUser.getBio());
        bioArea.setEditable(false);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setOpaque(false);
        bioArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bioArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bioArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Stats
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setMaximumSize(new Dimension(400, 60));
        statsPanel.setOpaque(false);
        
        JPanel postsPanel = createStatPanel("Posts", String.valueOf(currentUser.getPosts().size()));
        JPanel followersPanel = createStatPanel("Followers", String.valueOf(currentUser.getFollowers().size()));
        JPanel followingPanel = createStatPanel("Following", String.valueOf(currentUser.getFollowing().size()));
        
        statsPanel.add(postsPanel);
        statsPanel.add(followersPanel);
        statsPanel.add(followingPanel);
        
        profilePanel.add(nameLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        profilePanel.add(usernameLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(bioArea);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(statsPanel);
        
        profileDialog.add(profilePanel, BorderLayout.CENTER);
        profileDialog.setVisible(true);
    }
    
    private JPanel createStatPanel(String label, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.PLAIN, 12));
        labelText.setForeground(Color.GRAY);
        labelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(valueLabel);
        panel.add(labelText);
        
        return panel;
    }
    
    private void showSearchDialog() {
        JDialog searchDialog = new JDialog(this, "Search Users", true);
        searchDialog.setSize(500, 400);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setLayout(new BorderLayout(10, 10));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(59, 89, 152));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        
        JScrollPane resultsScroll = new JScrollPane(resultsPanel);
        
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            resultsPanel.removeAll();
            
            if (!query.isEmpty()) {
                List<User> results = dataStore.searchUsers(query);
                
                if (results.isEmpty()) {
                    JLabel noResults = new JLabel("No users found");
                    noResults.setFont(new Font("Arial", Font.ITALIC, 13));
                    noResults.setForeground(Color.GRAY);
                    noResults.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    resultsPanel.add(noResults);
                } else {
                    for (User user : results) {
                        if (!user.getUserId().equals(session.getCurrentUser().getUserId())) {
                            JPanel userCard = createUserCard(user);
                            resultsPanel.add(userCard);
                            resultsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        }
                    }
                }
            }
            
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });
        
        searchDialog.add(searchPanel, BorderLayout.NORTH);
        searchDialog.add(resultsScroll, BorderLayout.CENTER);
        
        searchDialog.setVisible(true);
    }
    
    private JPanel createUserCard(User user) {
        JPanel userCard = new JPanel(new BorderLayout(10, 5));
        userCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        userCard.setBackground(Color.WHITE);
        userCard.setMaximumSize(new Dimension(450, 80));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel usernameLabel = new JLabel("@" + user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.GRAY);
        
        infoPanel.add(nameLabel);
        infoPanel.add(usernameLabel);
        
        boolean isFollowing = session.getCurrentUser().isFollowing(user);
        JButton followButton = new JButton(isFollowing ? "Unfollow" : "Follow");
        followButton.setBackground(isFollowing ? new Color(220, 220, 220) : new Color(59, 89, 152));
        followButton.setForeground(isFollowing ? Color.BLACK : Color.WHITE);
        followButton.setFocusPainted(false);
        followButton.addActionListener(e -> {
            if (isFollowing) {
                dataStore.unfollowUser(session.getCurrentUser(), user);
            } else {
                dataStore.followUser(session.getCurrentUser(), user);
            }
            loadFeed();
        });
        
        userCard.add(infoPanel, BorderLayout.CENTER);
        userCard.add(followButton, BorderLayout.EAST);
        
        return userCard;
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            session.logout();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
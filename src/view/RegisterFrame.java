package view;

import javax.swing.*;

import controller.AuthController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextArea bioArea;
    private JButton registerButton;
    private JButton backButton;
    private JLabel messageLabel;
    private AuthController authController;
    private LoginFrame loginFrame;
    
    public RegisterFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.authController = new AuthController();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Social Feed - Register");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(29, 161, 242));
        
        JLabel subtitleLabel = new JLabel("Join Social Feed today");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.GRAY);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username *");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Name field
        JLabel nameLabel = new JLabel("Full Name *");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(nameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password *");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password *");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(confirmPasswordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(confirmPasswordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Bio field
        JLabel bioLabel = new JLabel("Bio (Optional)");
        bioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        bioArea = new JTextArea(3, 20);
        bioArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JScrollPane bioScrollPane = new JScrollPane(bioArea);
        bioScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        bioScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(bioLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(bioScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Message label for errors
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Register button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setBackground(new Color(29, 161, 242));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.add(registerButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Back button
        backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(29, 161, 242));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.add(backButton);
        
        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToLogin();
            }
        });
        
        // Wrap in scroll pane for smaller screens
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane);
    }
    
    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String name = nameField.getText();
        String bio = bioArea.getText();
        
        String result = authController.register(username, password, confirmPassword, name, bio);
        
        if (result.equals("SUCCESS")) {
            messageLabel.setForeground(new Color(0, 150, 0));
            messageLabel.setText("Registration successful!");
            
            JOptionPane.showMessageDialog(this,
                "Account created successfully! You can now login.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            goBackToLogin();
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText(result);
        }
    }
    
    private void goBackToLogin() {
        loginFrame.setVisible(true);
        this.dispose();
    }
}

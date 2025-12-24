package view;
import javax.swing.*;

import controller.AuthController;
import model.Session;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel messageLabel;
    private AuthController authController;
    
    public LoginFrame() {
        authController = new AuthController();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Social Feed - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Social Feed");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(29, 161, 242));
        
        JLabel subtitleLabel = new JLabel("Connect with friends");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.GRAY);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Message label for errors
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(29, 161, 242));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Register info
        JLabel registerLabel = new JLabel("Don't have an account?");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(registerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Register button
        registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(29, 161, 242));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(29, 161, 242), 2));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.add(registerButton);
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterFrame();
            }
        });
        
        // Allow Enter key to login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        add(mainPanel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (authController.login(username, password)) {
            messageLabel.setText(" ");
            JOptionPane.showMessageDialog(this, 
                "Login successful! Welcome " + authController.getCurrentUser().getName(),
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            Session session = Session.getInstance();
            MainFeedFrame mainFeed = new MainFeedFrame(session);
            mainFeed.setVisible(true);
            this.dispose();
            
            // For now, just show success
            System.out.println("Login successful for: " + username);
        } else {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
        }
    }
    
    private void openRegisterFrame() {
        RegisterFrame registerFrame = new RegisterFrame(this);
        registerFrame.setVisible(true);
        this.setVisible(false);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}

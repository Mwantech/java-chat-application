package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ChatApplication app;

    public LoginPanel(ChatApplication app) {
        this.app = app;
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton loginButton = createButton("Login", new Color(76, 175, 80), Color.WHITE);
        JButton cancelButton = createButton("Clear", new Color(255, 87, 34), Color.WHITE);
        JButton registerButton = createButton("Register", new Color(33, 150, 243), Color.WHITE);

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    app.currentUser = getUser(username);
                    app.cardLayout.show(app.mainPanel, "ChatPanel");
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.cardLayout.show(app.mainPanel, "RegistrationPanel");
            }
        });
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setPreferredSize(new Dimension(100, 35)); // Increased size
        return button;
    }

    private boolean authenticate(String username, String password) {
        for (User user : app.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private User getUser(String username) {
        for (User user : app.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}

package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel {
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private ChatApplication app;

    public RegistrationPanel(ChatApplication app) {
        this.app = app;
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30)); // Increased size
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton registerButton = createButton("Register", new Color(0, 123, 255), Color.WHITE);
        JButton cancelButton = createButton("Cancel", new Color(220, 53, 69), Color.WHITE);
        JButton clearButton = createButton("Clear", new Color(255, 193, 7), Color.BLACK);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (register(name, username, email, password)) {
                    JOptionPane.showMessageDialog(RegistrationPanel.this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    app.cardLayout.show(app.mainPanel, "LoginPanel");
                } else {
                    JOptionPane.showMessageDialog(RegistrationPanel.this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.cardLayout.show(app.mainPanel, "LoginPanel");
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
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

    private boolean register(String name, String username, String email, String password) {
        for (User user : app.users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        app.users.add(new User(name, username, email, password));
        return true;
    }
}

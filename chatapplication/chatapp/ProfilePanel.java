package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProfilePanel extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JLabel profilePictureLabel;
    private JButton changePictureButton;
    private JButton saveButton;
    private JButton backButton;
    private ChatApplication app;

    public ProfilePanel(ChatApplication app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        profilePictureLabel = new JLabel("Profile Picture");
        formPanel.add(profilePictureLabel);

        changePictureButton = new JButton("Change Picture");
        changePictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ProfilePanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        byte[] pictureData = Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()));
                        app.currentUser.setProfilePicture(pictureData);
                        profilePictureLabel.setText("Profile Picture Updated");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ProfilePanel.this, "Failed to update profile picture", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        formPanel.add(changePictureButton);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.currentUser.setName(nameField.getText());
                app.currentUser.setEmail(emailField.getText());
                JOptionPane.showMessageDialog(ProfilePanel.this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.cardLayout.show(app.mainPanel, "ChatPanel");
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag && app.currentUser != null) {
            nameField.setText(app.currentUser.getName());
            emailField.setText(app.currentUser.getEmail());
            if (app.currentUser.getProfilePicture() != null) {
                profilePictureLabel.setText("Profile Picture");
            }
        }
    }
}

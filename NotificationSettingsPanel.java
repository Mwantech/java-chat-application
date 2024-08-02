package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationSettingsPanel extends JPanel {
    private JCheckBox emailNotificationsCheckBox;
    private JCheckBox smsNotificationsCheckBox;
    private JButton saveButton;
    private JButton backButton;
    private ChatApplication app;

    public NotificationSettingsPanel(ChatApplication app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Notification Settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        emailNotificationsCheckBox = new JCheckBox("Email Notifications");
        formPanel.add(emailNotificationsCheckBox);

        smsNotificationsCheckBox = new JCheckBox("SMS Notifications");
        formPanel.add(smsNotificationsCheckBox);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.currentUser.setEmailNotificationsEnabled(emailNotificationsCheckBox.isSelected());
                app.currentUser.setSmsNotificationsEnabled(smsNotificationsCheckBox.isSelected());
                JOptionPane.showMessageDialog(NotificationSettingsPanel.this, "Notification settings updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            emailNotificationsCheckBox.setSelected(app.currentUser.isEmailNotificationsEnabled());
            smsNotificationsCheckBox.setSelected(app.currentUser.isSmsNotificationsEnabled());
        }
    }
}

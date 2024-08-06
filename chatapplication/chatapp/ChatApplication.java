package chatapp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatApplication extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;
    LoginPanel loginPanel;
    RegistrationPanel registrationPanel;
    ProfilePanel profilePanel;
    ChatPanel chatPanel;
    NotificationSettingsPanel notificationSettingsPanel;
    ArrayList<User> users = new ArrayList<>();
    User currentUser;

    public ChatApplication() {
        setTitle("Chat Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        registrationPanel = new RegistrationPanel(this);
        profilePanel = new ProfilePanel(this);
        chatPanel = new ChatPanel(this);
        notificationSettingsPanel = new NotificationSettingsPanel(this);

        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(registrationPanel, "RegistrationPanel");
        mainPanel.add(profilePanel, "ProfilePanel");
        mainPanel.add(chatPanel, "ChatPanel");
        mainPanel.add(notificationSettingsPanel, "NotificationSettingsPanel");

        add(mainPanel);
        cardLayout.show(mainPanel, "LoginPanel");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatApplication().setVisible(true));
    }
}

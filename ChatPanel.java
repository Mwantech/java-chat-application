package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChatPanel extends JPanel {
    private JTextPane chatArea;
    private JTextField messageField;
    private ChatApplication app;
    private JComboBox<String> groupComboBox;
    private DefaultComboBoxModel<String> groupModel;
    private Map<String, JTextPane> groupChatPanels;

    public ChatPanel(ChatApplication app) {
        this.app = app;
        this.groupChatPanels = new HashMap<>();

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Chat", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        chatArea = new JTextPane();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messageField = new JTextField();
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(Color.GREEN);
        sendButton.setForeground(Color.BLACK);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton fileButton = new JButton("File");
        fileButton.setBackground(Color.YELLOW);
        fileButton.setForeground(Color.BLACK);
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ChatPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    appendToChatArea(app.currentUser.getUsername() + " shared a file: " + selectedFile.getName() + "\n", false);
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(ChatPanel.this, "File selection canceled", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ChatPanel.this, "File selection error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);
        messagePanel.add(fileButton, BorderLayout.WEST);
        add(messagePanel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton createGroupButton = new JButton("Create Group");
        createGroupButton.setBackground(Color.BLUE);
        createGroupButton.setForeground(Color.WHITE);
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = JOptionPane.showInputDialog(ChatPanel.this, "Enter group name:");
                if (groupName != null && !groupName.trim().isEmpty()) {
                    groupModel.addElement(groupName);
                    createGroupChatPanel(groupName);
                }
            }
        });

        groupModel = new DefaultComboBoxModel<>();
        initializeInbuiltGroups();
        groupComboBox = new JComboBox<>(groupModel);
        groupComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup != null) {
                    chatArea.setStyledDocument(groupChatPanels.get(selectedGroup).getStyledDocument());
                }
            }
        });

        JButton profileButton = new JButton("Profile");
        profileButton.setBackground(Color.GRAY);
        profileButton.setForeground(Color.WHITE);
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.cardLayout.show(app.mainPanel, "ProfilePanel");
            }
        });

        JButton notificationButton = new JButton("Notifications");
        notificationButton.setBackground(Color.ORANGE);
        notificationButton.setForeground(Color.BLACK);
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.cardLayout.show(app.mainPanel, "NotificationSettingsPanel");
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        topPanel.add(createGroupButton);
        topPanel.add(groupComboBox);
        topPanel.add(profileButton);
        topPanel.add(notificationButton);
        topPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH);
    }

    private void initializeInbuiltGroups() {
        // Add inbuilt groups
        String[] inbuiltGroups = {"General", "Tech", "Discussions"};
        for (String group : inbuiltGroups) {
            groupModel.addElement(group);
            createGroupChatPanel(group);
        }
    }

    private void createGroupChatPanel(String groupName) {
        JTextPane groupChatArea = new JTextPane();
        groupChatArea.setEditable(false);
        groupChatPanels.put(groupName, groupChatArea);
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String selectedGroup = (String) groupComboBox.getSelectedItem();
            if (selectedGroup != null) {
                JTextPane selectedChatArea = groupChatPanels.get(selectedGroup);
                appendToChatArea(app.currentUser.getUsername() + ": " + message + "\n", true);
                selectedChatArea.setStyledDocument(chatArea.getStyledDocument());
                messageField.setText("");
            }
        }
    }

    private void appendToChatArea(String message, boolean isCurrentUser) {
        StyledDocument doc = chatArea.getStyledDocument();
        SimpleAttributeSet rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, isCurrentUser ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
        StyleConstants.setBackground(rightAlign, isCurrentUser ? Color.GREEN : Color.WHITE);

        try {
            doc.insertString(doc.getLength(), message, rightAlign);
            doc.setParagraphAttributes(doc.getLength() - message.length(), message.length(), rightAlign, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}

package fc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
    private static final ChatPanel instance = new ChatPanel();

    // UI 组件
    private JTextArea chatArea;  // 用于显示聊天内容的文本区域
    private JTextField chatInputField;  // 用于输入消息的文本框
    private JButton sendButton;  // 发送按钮
    
    private ChatPanel() {
        // 设置面板的布局为 BorderLayout
        setLayout(new BorderLayout());
        
        // 创建聊天区域（不可编辑）
        chatArea = new JTextArea(25, 25);  // 设置聊天区域的大小
        chatArea.setEditable(false);  // 不允许用户编辑聊天记录
        chatArea.setLineWrap(true);  // 自动换行
        chatArea.setWrapStyleWord(true);  // 换行时按单词来换行
        chatArea.setFont(new Font("楷体", Font.PLAIN, 20));  // 设置聊天框中的字体，大小为 14
        JScrollPane scrollPane = new JScrollPane(chatArea);  // 添加滚动条
        chatArea.setOpaque(false);
        // 创建输入框和发送按钮
        chatInputField = new JTextField(10);
        sendButton = new JButton("发送");

        // 设置输入框的字体大小和样式
        chatInputField.setFont(new Font("楷体", Font.PLAIN, 15));  // 设置字体，大小为15
        // 设置输入框的高度为30（通过调整PreferredSize）
        chatInputField.setPreferredSize(new Dimension(chatInputField.getPreferredSize().width, 30));
        
        // 创建一个面板用于输入区域（文本框 + 发送按钮）
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(chatInputField, BorderLayout.CENTER);  // 将输入框添加到面板的中间
        inputPanel.add(sendButton, BorderLayout.EAST);  // 将发送按钮添加到面板的右侧
        chatInputField.setOpaque(false);
        inputPanel.setOpaque(false);
        // 将组件添加到 ChatPanel
        add(scrollPane, BorderLayout.CENTER);  // 聊天区域放置在中部
        add(inputPanel, BorderLayout.SOUTH);  // 输入框和发送按钮放置在底部

        // 设置发送按钮的点击事件
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatInputField.getText();  // 获取输入框中的文本
                if (!message.isEmpty()) {
                    Net.getInstance().sendChat(message);  // 发送聊天消息到网络
                    chatArea.append("我: " + message + "\n");  // 将发送的消息显示在聊天框中
                    chatInputField.setText("");  // 清空输入框
                }
            }
        });
    }

    public static ChatPanel getInstance() {
        return instance;
    }

    // 用于接收并显示聊天消息
    public void receiveChatMessage(String message) {
        chatArea.append("对方: " + message + "\n");  // 在聊天区域显示接收到的消息
    }
}

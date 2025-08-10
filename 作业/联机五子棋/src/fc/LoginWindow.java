package fc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    private JFrame frame;
    private JPanel panel;
    private JButton btnPlayerVsPlayer;
    private JButton btnPlayerVsAI;
    private JButton btnExit;
    
    public LoginWindow() {
        // 创建窗口
        frame = new JFrame("游戏登录");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); // 居中显示
        
        // 创建面板
        //panel = new JPanel();
        panel = new BackgroundPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); // 设置为垂直布局，间隔10px
        // 设置面板的上下边距为 100px，左右边距为 0px
        panel.setBorder(new EmptyBorder(200, 100, 80, 100));  // 上下边距为100px，左右为0px
        // 创建按钮
        btnPlayerVsPlayer = new JButton("人人对战");
        btnPlayerVsAI = new JButton("人机对战");
        btnExit = new JButton("退出游戏");
        btnPlayerVsPlayer.setFont(new Font("楷体",Font.BOLD,20));
        btnExit.setFont(new Font("楷体",Font.BOLD,20));
        btnPlayerVsAI.setFont(new Font("楷体",Font.BOLD,20));
        // 添加按钮到面板
        panel.add(btnPlayerVsPlayer);
        panel.add(btnPlayerVsAI);
        panel.add(btnExit);
        
        // 添加面板到窗口
        frame.add(panel);
        
        // 设置按钮点击事件
        btnPlayerVsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 如果选择人人对战，打开双人对战窗口
                openPlayerVsPlayerWindow();
            }
        });

        btnPlayerVsAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 如果选择人机对战，打开人机对战窗口
                openPlayerVsAIWindow();
            }
        });
        
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 退出游戏
                System.exit(0);
            }
        });
        
        // 显示窗口
        frame.setVisible(true);
    }

    // 打开“人人对战”窗口，即原来的棋盘窗口
    private void openPlayerVsPlayerWindow() {
        // 关闭当前登录窗口
        frame.dispose();
        
        // 跳转到双人对战的窗口
        Main mainWindow = new Main();
    }

    // 打开“人机对战”窗口
    private void openPlayerVsAIWindow() {
        // 关闭当前登录窗口
        frame.dispose();
        
        // 跳转到人机对战窗口（这里可以扩展一个人机对战窗口的实现）
        AIWindow aiWindow = new AIWindow();
    }

    
 // 自定义面板类，用于绘制背景图片
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            // 加载背景图片
            ImageIcon icon = new ImageIcon("src/images/fivechess.jpg");  // 替换成你的图片路径
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("图片加载失败！");
            } else {
                System.out.println("图片加载成功！");
            }
            backgroundImage = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("paintComponent 被调用");
            // 绘制背景图片，确保图片覆盖整个面板
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    
    
    public static void main(String[] args) {
        // 创建并显示登录窗口
        new LoginWindow();
    }
}

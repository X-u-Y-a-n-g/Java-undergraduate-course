package fc;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    private JFrame frame;
    private BackgroundPanel backgroundPanel;
    public Main() {
        frame = new JFrame("联机五子棋");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // 创建背景面板并设置背景图
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());

        // 添加其他模块
        backgroundPanel.add(ChessPanel.getInstance(), BorderLayout.CENTER);
        ChessPanel.getInstance().setOpaque(false);
        backgroundPanel.add(ChatPanel.getInstance(), BorderLayout.EAST);
        ChatPanel.getInstance().setOpaque(false);
        backgroundPanel.add(NorthPanel.getInstance(), BorderLayout.NORTH);
        NorthPanel.getInstance().setOpaque(false);
        backgroundPanel.add(WestPanel.getInstance(), BorderLayout.WEST);
        WestPanel.getInstance().setOpaque(false);

        // 添加菜单栏
        createMenuBar();
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    //创建菜单栏
    private void createMenuBar() {
       JMenuBar menuBar = new JMenuBar();

       // 菜单：游戏
       JMenu menuGame = new JMenu("游戏");
       JMenuItem switchBackground = new JMenuItem("切换背景");
       switchBackground.addActionListener(e -> changeBackgroundImage());
       menuGame.add(switchBackground);

       // 菜单：帮助
       JMenu menuHelp = new JMenu("帮助");
       JMenuItem gameRules = new JMenuItem("游戏规则");
       gameRules.addActionListener(e -> showGameRules());
       menuHelp.add(gameRules);

       JMenuItem aboutUs = new JMenuItem("关于我们");
       aboutUs.addActionListener(e -> showAboutUs());
       menuHelp.add(aboutUs);
       //打赏
       JMenu menuMoney=new JMenu("打赏");
    // 使用 JLayeredPane 将图片放到最上层
       JLayeredPane layeredPane = frame.getLayeredPane();
       JLabel rewardLabel = new JLabel();
       rewardLabel.setVisible(false); // 初始不可见
       rewardLabel.setBounds(350, 200, 300, 300); // 位置和大小

       try {
           // 加载打赏图片
           ImageIcon icon = new ImageIcon("src/images/money.jpg");
           rewardLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                   rewardLabel.getWidth(), rewardLabel.getHeight(), Image.SCALE_SMOOTH)));
       } catch (Exception e) {
           e.printStackTrace();
       }

       // 将打赏图片添加到最上层
       layeredPane.add(rewardLabel, JLayeredPane.POPUP_LAYER);

       // 鼠标事件监听：显示和隐藏打赏图片
       menuMoney.addMouseListener(new java.awt.event.MouseAdapter() {
           @Override
           public void mouseEntered(java.awt.event.MouseEvent evt) {
               rewardLabel.setVisible(true); // 鼠标悬停显示图片
           }

           @Override
           public void mouseExited(java.awt.event.MouseEvent evt) {
               rewardLabel.setVisible(false); // 鼠标离开隐藏图片
           }
       });

       // 将菜单添加到菜单栏
       menuBar.add(menuGame);
       menuBar.add(menuHelp);
       menuBar.add(menuMoney);
       // 设置菜单栏
       frame.setJMenuBar(menuBar);
   }
    
    //切换背景图片功能
    
    private void changeBackgroundImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择新的背景图片");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            backgroundPanel.setBackgroundImage(selectedFile.getPath());
            backgroundPanel.repaint();
        }
    }

    //显示游戏规则
    private void showGameRules() {
        JOptionPane.showMessageDialog(frame,
                "游戏规则：\n1. 玩家轮流落子。\n2. 先连成五子一线的玩家获胜。\n3. 一线可以是横、竖或斜线。",
                "游戏规则", JOptionPane.INFORMATION_MESSAGE);
    }

    //显示关于我们
    private void showAboutUs() {
        JOptionPane.showMessageDialog(frame,
                "关于我们：\n五子棋小游戏 \n开发者：许洋 \n感谢您的支持！",
                "关于我们", JOptionPane.INFORMATION_MESSAGE);
    }

    
    // 自定义背景面板类
    static class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                // 加载背景图片
                backgroundImage = ImageIO.read(new File("src/images/b1.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void setBackgroundImage(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "无法加载背景图片！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // 重写paintComponent方法，绘制背景图片
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // 绘制背景图片，填充整个面板
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        
    }

    public static void main(String[] args) {
        // 启动主界面
        new Main();
    }
} 
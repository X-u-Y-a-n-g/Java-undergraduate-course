package fc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fc.Control.Move;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Random;

public class AIWindow {
    private JFrame frame;
    private BackgroundPanel backgroundPanel;
    private ChessPanel chessPanel;
    private JButton undoButton, surrenderButton, drawButton;
    private boolean isPlayerTurn = true;  // 判断是否轮到玩家
    private Stack<Move> moveHistory = new Stack<>();  // 存储棋步历史
    private int[][] board = new int[19][19];  // 棋盘，0为空，1为玩家，2为AI
    
    public AIWindow() {
        // 创建人机对战窗口
        frame = new JFrame("人机对战");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 800);
        frame.setLocationRelativeTo(null); // 居中显示
        
     // 创建背景面板并设置背景图
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        
        // 创建棋盘面板
        chessPanel = new ChessPanel();
        chessPanel.setPreferredSize(new Dimension(450, 450));
        backgroundPanel.add(chessPanel, BorderLayout.CENTER);
        chessPanel.setOpaque(false);
        // 创建控制面板（悔棋、认输、求和按钮）
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1, 100, 120));
        // 设置面板的上下边距为 100px，左右边距为 0px
        controlPanel.setBorder(new EmptyBorder(200, 0, 200, 20));  // 上下边距为100px，左右为0px

        undoButton = new JButton("悔棋");
        surrenderButton = new JButton("认输");
        drawButton = new JButton("求和");

        // 设置按钮字体
        undoButton.setFont(new Font("楷体", Font.PLAIN, 20));  // 设置字体为楷体，大小为20px
        surrenderButton.setFont(new Font("楷体", Font.PLAIN, 20));
        drawButton.setFont(new Font("楷体", Font.PLAIN, 20));
        
        controlPanel.add(undoButton);
        controlPanel.add(surrenderButton);
        controlPanel.add(drawButton);
        
        backgroundPanel.add(controlPanel, BorderLayout.EAST);
        controlPanel.setOpaque(false);
        // 设置按钮事件
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoMove();
            }
        });

        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surrender();
            }
        });

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offerDraw();
            }
        });

        // 添加菜单栏
        createMenuBar();
        
        // 添加背景面板
        frame.add(backgroundPanel);
        
        // 显示窗口
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
                "关于我们：\n五子棋\n开发者：许洋\n感谢您的支持！",
                "关于我们", JOptionPane.INFORMATION_MESSAGE);
    }

    
    // 自定义背景面板类
    static class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                // 加载默认背景图片
                backgroundImage = ImageIO.read(new File("src/images/b2.jpg"));
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // 绘制背景图片，填充整个面板
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    
    // 画棋盘和棋子
    private class ChessPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // 绘制棋盘网格
            g.setColor(Color.BLACK);
            for (int i = 0; i < 19; i++) {
                g.drawLine(38 + i * 38, 38, 38 + i * 38, 38 + 18 * 38);  // 竖线
                g.drawLine(38, 38 + i * 38, 38 + 18 * 38, 38 + i * 38);  // 横线
            }

            // 绘制棋子
            for (int row = 0; row < 19; row++) {
                for (int col = 0; col < 19; col++) {
                    if (board[row][col] == 1) {  // 玩家黑棋
                        g.setColor(Color.BLACK);
                        g.fillOval(38 + col * 38 - 10, 38 + row * 38 - 10, 20, 20);
                    } else if (board[row][col] == 2) {  // AI 白棋
                        g.setColor(Color.WHITE);
                        g.fillOval(38 + col * 38 - 10, 38 + row * 38 - 10, 20, 20);
                    }
                }
            }
        }
        
        // 鼠标点击事件处理
        public ChessPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int col = (e.getX() - 38) / 38;
                    int row = (e.getY() - 38) / 38;

                    if (row >= 0 && row < 19 && col >= 0 && col < 19) {
                        if (board[row][col] == 0 && isPlayerTurn) {  // 如果该位置为空且是玩家回合
                            playerMove(row, col);
                        }
                    }
                }
            });
        }
    }

    // 玩家下棋
    private void playerMove(int row, int col) {
        if (board[row][col] == 0) {  // 如果该位置没有棋子
            board[row][col] = 1;  // 玩家黑棋
            moveHistory.push(new Move(row, col, 1));  // 保存棋步
            isPlayerTurn = false;  // 轮到AI下棋
            checkWinner();
            chessPanel.repaint();
            if (!isPlayerTurn) {
                aiMove();  // 轮到AI下棋
            }
        }
    }

    // AI 下棋（简化版，随机选择一个空的位置）
    private void aiMove() {
        if (isPlayerTurn) return;

        // 简化AI逻辑，随机选择一个空的格子
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(19);
            col = random.nextInt(19);
        } while (board[row][col] != 0);

        board[row][col] = 2;  // AI 白棋
        moveHistory.push(new Move(row, col, 2));  // 保存棋步
        checkWinner();
        chessPanel.repaint();
        isPlayerTurn = true;  // 轮到玩家下棋
    }

    // 检查是否有人胜利
    private void checkWinner() {
        int winner = checkFiveInRow();
        if (winner != 0) {
            String winnerMessage = (winner == 1) ? "玩家胜利" : "AI胜利";
            showGameOverDialog(winnerMessage);
        }
    }

    // 判断是否五子连珠
    private int checkFiveInRow() {
        // 检查横、纵、斜对角是否有五子连珠
        for (int row = 0; row < 19; row++) {
            for (int col = 0; col < 19; col++) {
                if (board[row][col] != 0) {
                    if (checkDirection(row, col, 1, 0) ||  // 横向
                        checkDirection(row, col, 0, 1) ||  // 纵向
                        checkDirection(row, col, 1, 1) ||  // 主对角线
                        checkDirection(row, col, 1, -1)) { // 副对角线
                        return board[row][col];
                    }
                }
            }
        }
        return 0;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol) {
        int color = board[row][col];
        int count = 1;
        
        // 向一个方向检查
        for (int i = 1; i < 5; i++) {
            int r = row + dRow * i;
            int c = col + dCol * i;
            if (r < 0 || r >= 19 || c < 0 || c >= 19 || board[r][c] != color) {
                break;
            }
            count++;
        }

        // 向反方向检查
        for (int i = 1; i < 5; i++) {
            int r = row - dRow * i;
            int c = col - dCol * i;
            if (r < 0 || r >= 19 || c < 0 || c >= 19 || board[r][c] != color) {
                break;
            }
            count++;
        }

        return count >= 5;
    }

    // 显示游戏结束对话框
    private void showGameOverDialog(String message) {
        int option = JOptionPane.showOptionDialog(frame,
                message + "\n是否重新开始游戏？", "游戏结束",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"重新开始", "退出游戏"}, "重新开始");

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0); // 退出游戏
        }
    }

    // 重置游戏
    private void resetGame() {
        // 清空棋盘
        board = new int[19][19];
        moveHistory.clear();
        isPlayerTurn = true;
        chessPanel.repaint();
    }

    // 悔棋
    private void undoMove() {
        if (moveHistory.size() >= 2) {
            Move lastMove = moveHistory.pop();  // 撤回AI的棋
            board[lastMove.row][lastMove.col] = 0;

            lastMove = moveHistory.pop();  // 撤回玩家的棋
            board[lastMove.row][lastMove.col] = 0;

            // 切换回合
            
            chessPanel.repaint();
        }
    }

    // 认输
    private void surrender() {
        String winnerMessage = (isPlayerTurn) ? "AI胜利" : "玩家胜利";
        showGameOverDialog(winnerMessage);
        resetGame();
    }

    // 求和
    private void offerDraw() {
        int acceptDraw = JOptionPane.showOptionDialog(frame,
                "是否接受平局？", "平局请求",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"接受", "拒绝"}, "拒绝");

        if (acceptDraw == JOptionPane.YES_OPTION) {
            showGameOverDialog("平局");
            resetGame();
        }
    }

    public static void main(String[] args) {
        new AIWindow();  // 启动人机对战窗口
    }
}

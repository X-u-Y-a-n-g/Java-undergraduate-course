package fc;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class WestPanel extends JPanel {
    private static final WestPanel instance = new WestPanel();
    private boolean canUndo = false; // 用于控制每回合只允许悔棋一次

    private WestPanel() {
    	
    	// 使用 GridLayout 设置 3 行 1 列，按钮之间的水平和垂直间隔为 10px
        setLayout(new GridLayout(3, 1,10,100));  // 3行1列，按钮之间间隔为10px
        // 设置面板的上下边距为 100px，左右边距为 0px
        setBorder(new EmptyBorder(200, 20, 200, 0));  // 上下边距为100px，左右为0px

        // 创建按钮
        JButton undoBtn = new JButton("悔棋");
        JButton surrenderBtn = new JButton("认输");
        JButton drawBtn = new JButton("求和");

        // 设置按钮字体
        undoBtn.setFont(new Font("楷体", Font.PLAIN, 20));  // 设置字体为楷体，大小为20px
        surrenderBtn.setFont(new Font("楷体", Font.PLAIN, 20));
        drawBtn.setFont(new Font("楷体", Font.PLAIN, 20));

        // 将按钮添加到面板
        add(undoBtn);
        add(surrenderBtn);
        add(drawBtn);
        
    	// 设置按钮点击事件
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canUndo) {
                    Control.getInstance().undoMove();
                    canUndo = false; 
                }
            }
        });

        surrenderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.getInstance().surrender();
            }
        });

        drawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.getInstance().offerDraw();
            }
        });

        // 添加按钮到面板
        add(undoBtn);
        add(surrenderBtn);
        add(drawBtn);
    }

    public static WestPanel getInstance() {
        return instance;
    }

    public void setCanUndo(boolean canUndo) {
        this.canUndo = canUndo; // 控制悔棋按钮的状态
    }
}

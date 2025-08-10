package fc;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NorthPanel extends JPanel{
	private static final  NorthPanel instance = new NorthPanel();
	
	private JLabel turnLabel = new JLabel("轮到我方下棋");  // 显示当前轮到谁下棋的标签
    private JLabel ipLabel = new JLabel("IP");  // IP 标签
    private JLabel portLabel = new JLabel("PORT");  // port 标签
    
    // 定义文本框和按钮
    private JTextField ipTF = new JTextField(10);
    private JTextField portTF = new JTextField(5);
    private JButton connectBtn = new JButton("连接");
    private JButton listenBtn = new JButton("监听");
    
    private NorthPanel() {
        // 设置默认的文本框内容
        ipTF.setText("localhost");
        portTF.setText("2345");

        // 设置面板布局
        setLayout(new FlowLayout());

        // 设置组件大小
        ipTF.setPreferredSize(new Dimension(40, 40));  // 设置宽度为40，高度为40
        portTF.setPreferredSize(new Dimension(40, 40));  // 设置宽度为40，高度为40
        connectBtn.setPreferredSize(new Dimension(130, 40));  // 设置宽度为40，高度为40
        listenBtn.setPreferredSize(new Dimension(130, 40));   // 设置宽度为40，高度为40
        
        // 设置字体样式和大小
        Font font = new Font("Arial", Font.PLAIN, 20);  // 设置字体为 Arial，样式为普通，大小为14
        ipTF.setFont(font);  // 设置 IP 文本框的字体
        portTF.setFont(font);  // 设置端口文本框的字体
        connectBtn.setFont(new Font("楷体", Font.PLAIN, 20));  // 设置连接按钮的字体
        listenBtn.setFont(new Font("楷体", Font.PLAIN, 20));   // 设置监听按钮的字体
        turnLabel.setFont(new Font("楷体", Font.BOLD,20));  // 设置轮到我方下棋的标签字体，使用粗体

        // 设置 IP 和 port 标签的字体
        ipLabel.setFont(new Font("Arial", Font.PLAIN, 20));  // 设置 IP 标签字体
        portLabel.setFont(new Font("Arial", Font.PLAIN, 20));  // 设置 port 标签字体

        // 添加组件到面板
        add(ipLabel);  // 添加 IP 标签
        add(ipTF);     // 添加 IP 文本框
        add(portLabel);  // 添加 port 标签
        add(portTF);     // 添加 port 文本框
        add(listenBtn);  // 添加监听按钮
        add(connectBtn); // 添加连接按钮
        add(turnLabel);  // 添加轮到谁下棋的标签
		listenBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Net.getInstance().beginListen();
				listenBtn.setEnabled(false);
				Control.getInstance().setLocalColor(Model.BLACK);
				Control.getInstance().setAllow(true);
				updateTurnLabel(true);  // 启动监听后，显示“轮到我方下棋”
			}
		});
		connectBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipTF.getText();
				String p = portTF.getText();
				Net.getInstance().connect(ip, Integer.parseInt(p));
				Control.getInstance().setLocalColor(Model.WHITE);
				Control.getInstance().setAllow(false);
				updateTurnLabel(false);  // 连接成功后，显示“轮到对方下棋”
			}
		});
		
	}
	public static NorthPanel getInstance() {
		return instance;
	}
	
	// 更新轮到谁下棋的提示
    public void updateTurnLabel(boolean isPlayerTurn) {
        if (isPlayerTurn) {
            turnLabel.setText("轮到我方下棋");  // 如果轮到玩家下棋
        } else {
            turnLabel.setText("轮到对方下棋");  // 如果轮到对方下棋
        }
    }
    
    // 提供更新轮到谁下棋的接口供外部调用
    public void updateTurnForUndo(boolean isPlayerTurn) {
        updateTurnLabel(isPlayerTurn);  // 同样更新轮到谁下棋
    }
}
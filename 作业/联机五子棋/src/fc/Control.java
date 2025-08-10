package fc;

import java.util.Stack;

import javax.swing.JOptionPane;

public class Control {
	private boolean isAllow = false;
	private int localColor = Model.BLACK;
	private int otherColor = Model.WHITE;
	
	private Stack<Move> moveHistory = new Stack<>(); // 保存棋步历史记录
    
    // 用于记录每一步棋的信息
    static class Move {
        int row, col;
        int color; // 记录是本方还是对方的棋子
        
        public Move(int row, int col, int color) {
            this.row = row;
            this.col = col;
            this.color = color;
        }
    }

	
	public void otherPutChess(int row, int col) {
        boolean success = Model.getInstance().putChess(row, col, otherColor);
        if (success) {
        	moveHistory.push(new Move(row, col, otherColor)); // 保存对方的棋步
            isAllow = true;
            NorthPanel.getInstance().updateTurnLabel(true);  // 轮到我方下棋
            ChessPanel.getInstance().repaint();
            checkWinner();
        }
    }

    public void localPutChess(int row, int col) {
        if (!isAllow) {
            return;
        }
        boolean success = Model.getInstance().putChess(row, col, localColor);
        if (success) {
        	moveHistory.push(new Move(row, col, localColor)); // 保存本方的棋步
            isAllow = false;
            Net.getInstance().sendChess(row, col);
            NorthPanel.getInstance().updateTurnLabel(false);  // 轮到对方下棋
            ChessPanel.getInstance().repaint();
            checkWinner();
            // 启用悔棋按钮
            WestPanel.getInstance().setCanUndo(true);
        }
    }
	
    private void checkWinner() {
        int winner = Model.getInstance().whoWin();
        if (winner == Model.BLACK) {
            showGameOverDialog("黑棋胜利");
        } else if (winner == Model.WHITE) {
            showGameOverDialog("白棋胜利");
        }
    }

    private void showGameOverDialog(String message) {
        // 弹出对话框，询问是否重新开始或退出游戏
        int option = JOptionPane.showOptionDialog(null,
                message + "\n是否重新开始游戏？", "游戏结束",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"重新开始", "退出游戏"}, "重新开始");

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0); // 退出游戏
        }
    }

    	
	
	
	private static final Control instance = new Control();
	private Control(){}
	public static Control getInstance() {
		return instance;
	}
	public void setLocalColor(int color) {
		localColor = color;
		otherColor = -color;
	}
	public void setAllow(boolean b) {
		isAllow = b;
	}
	
	public void sendChatMessage(String message) {
		Net.getInstance().sendChat(message);
	}
	
	// 悔棋功能
	public void undoMove() {
        if (!moveHistory.isEmpty()) {
            // 取出最近一步棋（对方和本方的棋子）
            Move lastMove = moveHistory.pop();

            // 撤销本地棋盘的棋子
            Model.getInstance().removeChess(lastMove.row, lastMove.col);
            ChessPanel.getInstance().repaint();

            // 向对方发送撤回的棋步
            Net.getInstance().sendUndo(lastMove.row, lastMove.col);

            // 还原回合
            isAllow = (lastMove.color == localColor); // 如果是本方悔棋，则切换回合

            // 更新悔棋按钮的状态
            if (moveHistory.isEmpty()) {
                WestPanel.getInstance().setCanUndo(false);
            }
            
         // 更新轮到谁下棋的提示
            NorthPanel.getInstance().updateTurnLabel(isAllow); // 根据是否轮到本方更新提示
        }
    }

	// 接收并撤回对方的棋子
	public void undoMove(int row, int col) {
	    Model.getInstance().removeChess(row, col); // 从棋盘上移除
	    ChessPanel.getInstance().repaint(); // 刷新棋盘
	    isAllow = (localColor == Model.BLACK); // 切换回合
	    WestPanel.getInstance().setCanUndo(true); // 允许本方悔棋
	 // 更新轮到谁下棋的提示
        NorthPanel.getInstance().updateTurnLabel(isAllow); // 根据是否轮到本方更新提示
	}



    
    // 认输
    public void surrender() {
        String winnerMessage = (localColor == Model.BLACK) ? "白棋胜利" : "黑棋胜利";
        showGameOverDialog(winnerMessage);
        resetGame();
        
     // 向对方发送重置游戏请求
        Net.getInstance().sendResetGame();
    }

 // 求和
    public void offerDraw() {
        // 询问用户是否接受平局
        int acceptDraw = JOptionPane.showOptionDialog(null,
                "是否接受平局？", "平局请求",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"接受", "拒绝"}, "拒绝");

        if (acceptDraw == JOptionPane.YES_OPTION) {
            // 如果用户接受平局，弹出重新开始或退出的对话框
            int option = JOptionPane.showOptionDialog(null,
                    "游戏平局\n是否重新开始游戏？", "游戏结束",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"重新开始", "退出游戏"}, "重新开始");

            if (option == JOptionPane.YES_OPTION) {
                resetGame();  // 重新开始游戏
                Net.getInstance().sendResetGame();  // 发送重新开始的消息给对方
            } else {
                System.exit(0); // 退出游戏
            }
        }
        // 如果用户拒绝平局，什么也不做（平局请求被拒绝）
    }
 // 处理游戏重置
    public void resetGame() {
        Model.getInstance().resetBoard();  // 重置棋盘
        ChessPanel.getInstance().repaint();  // 刷新棋盘显示
        moveHistory.clear();  // 清空棋步历史
        WestPanel.getInstance().setCanUndo(false);  // 取消悔棋按钮的状态
        isAllow = true;  // 重新开始后，允许本地先下棋
    }

    
}
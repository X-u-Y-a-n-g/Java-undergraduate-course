package fc;

public class Model {
	private static final Model instance = new Model(); 
	private Model() {
//		data[3][4]=BLACK;
//		data[4][5]=WHITE;
	}
	public static Model getInstance() {
		return instance;
	}
	
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int SPACE = 0;
	public static final int WIDTH = 19;
	
	private int[][] data = new int[WIDTH][WIDTH];
	private int lastRow,lastCol;
	public boolean putChess(int row,int col,int color){
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH&&data[row][col]==SPACE){
			data[row][col]=color;
			lastRow = row;
			lastCol = col;
			return true;
		}
		return false;
	}
	
	public void removeChess(int row, int col) {
        if (row >= 0 && row < WIDTH && col >= 0 && col < WIDTH) {
            data[row][col] = SPACE;
        }
    }
	
	public int getChess(int row,int col){
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH){
			return data[row][col];
		}
		
		return SPACE;
	}
	
	// 重置棋盘
    public void resetBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                data[i][j] = SPACE;
            }
        }
    }
	
	public int whoWin() {
	    // 检查是否有五子连珠
	    if (checkDirection(lastRow, lastCol, 1, 0) || // 水平方向
	        checkDirection(lastRow, lastCol, 0, 1) || // 垂直方向
	        checkDirection(lastRow, lastCol, 1, 1) || // 主对角线
	        checkDirection(lastRow, lastCol, 1, -1)) { // 副对角线
	        return data[lastRow][lastCol]; // 返回获胜方的颜色
	    }

	    // 如果没有人获胜，返回 SPACE
	    return SPACE;
	}

	private boolean checkDirection(int row, int col, int rowDelta, int colDelta) {
	    int color = data[row][col];
	    if (color == SPACE) return false;

	    int count = 1; // 包括当前位置

	    // 检查正向方向
	    count += countConsecutive(row, col, rowDelta, colDelta, color);

	    // 检查反向方向
	    count += countConsecutive(row, col, -rowDelta, -colDelta, color);

	    // 如果连珠数量达到或超过 5，返回 true
	    return count >= 5;
	}

	private int countConsecutive(int row, int col, int rowDelta, int colDelta, int color) {
	    int count = 0;
	    int currentRow = row + rowDelta;
	    int currentCol = col + colDelta;

	    while (currentRow >= 0 && currentRow < WIDTH &&
	           currentCol >= 0 && currentCol < WIDTH &&
	           data[currentRow][currentCol] == color) {
	        count++;
	        currentRow += rowDelta;
	        currentCol += colDelta;
	    }

	    return count;
	}

}
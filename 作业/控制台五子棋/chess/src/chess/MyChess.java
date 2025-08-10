package chess;

import java.util.Scanner;

public class MyChess {
	//棋盘
	private String[][] chessboard;
	//棋盘大小
	private final int SIZE=10;
	private int row;
	private int col;
	private boolean time=true;
	
	
	public static void main(String[] args) {
		MyChess chess=new MyChess();
		chess.init();
		chess.print();
		
		//下棋
		Scanner scannerrow=new Scanner(System.in);
		Scanner scannercol=new Scanner(System.in);
		while(true) {
			chess.player(scannerrow, scannercol);
			chess.print();
			if(chess.win(chess.row, chess.col)){
				if(!chess.time) {
					System.out.println("●赢了");
				}else {
					System.out.println("○赢了");
				}
				break;
			}
		}
		
	}
	
	
	
	
	//初始化棋盘
	private void init() {
		chessboard=new String[SIZE][SIZE];
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				chessboard[i][j]="+";
			}
		}
	}
	
	
	
	//打印棋盘
	private void print() {
		System.out.print("  ");
		for(int i=0;i<SIZE;i++) {
			System.out.print((String.format("%2s",i)));
		}
		System.out.println();
		for(int i=0;i<SIZE;i++) {
			System.out.print((String.format("%3s",i+" ")));
			for(int j=0;j<SIZE;j++) {
				System.out.print(chessboard[i][j]+" ");
			}
		System.out.println();
		}
	}
	//下棋
	public void player(Scanner scannerrow,Scanner scannercol) {
		System.out.print("请输入行: ");
		row=scannerrow.nextInt();
		System.out.print("请输入列: ");
		col=scannercol.nextInt();
		//是否确定下棋
		System.out.println("是否确定下在此处，确定请输入Y或y，重下请选择N或n");
		Scanner OkOrNot=new Scanner(System.in);
		String juge;
		juge=OkOrNot.next();
		switch(juge) {
		case "Y":
		case "y":
			//判断是否可行
			if(row>9||col>9||row<0||col<0) {
				System.out.println("数据超出范围，请重新输入");
				player(scannerrow, scannercol);
			}
			else if(chessboard[row][col]!="+"){
				System.out.println("已有棋子，请重新输入");
				player(scannerrow, scannercol);
			}else {
				if(time) {
					chessboard[row][col]="●";
					time=false;
				}else {
					chessboard[row][col]="○";
					time=true;
				}
			}
			break;
		case "N":
		case "n":
			//重新输入
			player(scannerrow, scannercol);
		}
	}
	
	//判断
	public boolean win(int row,int col){
		if(colup(row, col)+coldown(row, col)+1>=5) {
			return true;
			
		}else if(rowleft(row, col)+rowright(row, col)+1>=5) {
			return true;
			
		}
		else if(leftup(row, col)+rightdown(row, col)+1>=5) {
			return true;
			
		}else if(leftdown(row, col)+rightup(row, col)+1>=5) {
			return true;
			
		}else {
			return false;
			
		}
	}
	
	//数棋子
	//向上
	public int colup(int row,int col) {
		int count=0;
		if(col==0) {
			return count;
		}
		int coll=col-1;
		while(coll<=9&&coll>=0){
			if(chessboard[row][coll]==chessboard[row][col]) {
				count++;
				coll--;
			}
			else {
				break;
			}
		}
		return count;
	}
	//向下
	public int coldown(int row,int col) {
		int count=0;
		if(col==9) {
			return count;
		}
		int coll=col+1;
		while(coll<=9&&coll>=0){
			if(chessboard[row][coll]==chessboard[row][col]) {
				count++;
				coll++;
			}
			else {
				break;
			}
		}
		return count;
	}
	//向左
	public int rowleft(int row,int col) {
		int count=0;
		if(row==0) {
			return count;
		}
		int roww=row-1;
		while(roww<=9&&roww>=0){
			if(chessboard[roww][col]==chessboard[row][col]) {
				count++;
				roww--;
			}
			else {
				break;
			}
		}
		return count;
	}
	//向右
	public int rowright(int row,int col) {
		int count=0;
		if(row==9) {
			return count;
		}
		int roww=row+1;
		while(roww<=9&&roww>=0){
			if(chessboard[roww][col]==chessboard[row][col]) {
				count++;
				roww++;
			}
			else {
				break;
			}
		}
		return count;
	}
	//左上
	public int leftup(int row,int col) {
		int count=0;
		if(row==0||col==0)
		{
			return count;
		}
		int coll=col-1,roww=row-1;
		while(coll<=9&&coll>=0&&roww<=9&&roww>=0){
			if(chessboard[roww][coll]==chessboard[row][col]) {
				count++;
				coll--;roww--;
			}
			else {
				break;
			}
		}
		return count;
	}
	//右下
	public int rightdown(int row,int col) {
		int count=0;
		if(col==9||row==9) {
			return count;
		}
		int coll=col+1,roww=row+1;
		while(coll<=9&&coll>=0&&roww<=9&&roww>=0){
			if(chessboard[roww][coll]==chessboard[row][col]) {
				count++;
				coll++;roww++;
			}
			else {
				break;
			}
		}
		return count;
	}
	//左下
	public int leftdown(int row,int col) {
		int count=0;
		if(col==0||row==9) {
			return count;
		}
		int coll=col-1,roww=row+1;
		while(coll<=9&&coll>=0&&roww<=9&&roww>=0){
			if(chessboard[roww][coll]==chessboard[row][col]) {
				count++;
				coll--;roww++;
			}
			else {
				break;
			}
		}
		return count;
	}
	//右上
	public int rightup(int row,int col) {
		int count=0;
		if(row==0||col==9) {
			return count;
		}
		int coll=col+1,roww=row-1;
		while(coll<=9&&coll>=0&&roww<=9&&roww>=0){
			if(chessboard[row][coll]==chessboard[row][col]) {
				count++;
				coll++;roww--;
			}
			else {
				break;
			}
		}
		return count;
	}
}

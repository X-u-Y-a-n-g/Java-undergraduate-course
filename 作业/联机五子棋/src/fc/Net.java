package fc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Net {
	private ServerSocket ss ;
	private Socket s;
	private BufferedReader in;
	private PrintStream out;
	public static final int PORT = 2345;
	public void beginListen(){
		new Thread(){
			public void run(){
				try {
					System.out.println("begin listening");
					ss = new ServerSocket(PORT);
					s = ss.accept();
					in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					out = new PrintStream(s.getOutputStream(),true);
					startReadThread();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
	}
	public void connect(String ip,int port){
		try {
			s = new Socket(ip,port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream(),true);
			startReadThread();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	protected void startReadThread() {
		new Thread(){
			public void run(){
				String line;
				try {
					while((line=in.readLine())!=null){
						parseMessage(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	protected void parseMessage(String line) {
	    if (line.startsWith("undo:")) {
	        String[] parts = line.substring(5).split(",");
	        int row = Integer.parseInt(parts[0]);
	        int col = Integer.parseInt(parts[1]);
	        // 当接收到对方的撤回操作时，执行撤回操作
	        Control.getInstance().undoMove(row, col);
	    } else if (line.startsWith("chess:")) {
	        parseChess(line.substring(6));
	    } else if (line.startsWith("chat:")) {
	        parseChat(line.substring(5));
	    } else if(line.equals("reset_game")) {
	    	 // 收到对方的重新开始消息，重置游戏
            Control.getInstance().resetGame();
	    }
	}
	private void parseChess(String msg) {
		// 3,15
		String[] a = msg.split(",");
		int row = Integer.parseInt(a[0]);
		int col = Integer.parseInt(a[1]);
		Control.getInstance().otherPutChess(row,col);
	}
	
	private void parseChat(String msg) {
		ChatPanel.getInstance().receiveChatMessage(msg);
	}
	private static final Net instance = new Net();
	private Net(){}
	public static Net getInstance() {
		return instance;
	}
	public void sendChess(int row, int col) {
		out.println("chess:"+row+","+col);
	}
	public void sendChat(String line){
		out.println("chat:"+line);
	}
	public void sendUndo(int row, int col) {
	    out.println("undo:" + row + "," + col); // 发送撤回操作的坐标
	}
	public void sendResetGame() {
	    out.println("reset_game");  // 发送重新开始游戏的指令
	}
	
}
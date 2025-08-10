package fc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ChessPanel extends JPanel {
	private int unit = 30;
	private int sx,sy;
	
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = this.getHeight();
		int width = getWidth();
		sy = (height - unit*(Model.WIDTH-1))/2;
		sx = (width-unit*(Model.WIDTH-1))/2;
		g.setColor(Color.black);
		for(int i=0;i<Model.WIDTH;i++){
			g.drawLine(sx, sy+unit*i, sx+unit*(Model.WIDTH-1), sy+unit*i);
			g.drawLine(sx+unit*i, sy, sx+unit*i, sy+unit*(Model.WIDTH-1));
		}
		for(int row=0;row<Model.WIDTH;row++){
			for (int col = 0; col < Model.WIDTH; col++) {
				int color = Model.getInstance().getChess(row, col);
				if(color==Model.BLACK){
					g.setColor(Color.black);
				}else if(color==Model.WHITE){
					g.setColor(Color.white);
				}else{
					continue;
				}
				g.fillOval(sx+unit*col-unit/2, sy+unit*row-unit/2, unit, unit);
				
				
			}
		}
		
		
	}
	
	private static final ChessPanel instance =  new ChessPanel();
	private ChessPanel(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = (e.getY()-sy)/unit;
				int col = (e.getX()-sx)/unit;
				if((e.getY()-sy)%unit > unit/2){
					row++;
				}
				if((e.getX()-sx) %unit > unit/2){
					col++;
				}
				Control.getInstance().localPutChess(row, col);
			}
		});
		
		
	}
	public static ChessPanel getInstance() {
		return instance;
	}
}
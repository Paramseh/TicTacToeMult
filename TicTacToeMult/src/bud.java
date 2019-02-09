import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class bud extends JFrame {
	boolean p1t = true;
	TIles[] tiles = new TIles[9];
	JPanel p = new JPanel();
	JPanel pnl = new JPanel();
boolean gameOver = false;

	ClientStarter c;
	ServerStarter s;
	boolean thic;
	public bud(ClientStarter c) {
		this.c = c;
		thic = true;
		CreateWindow();
		setupTiles();
		
	}
	
	private void setupTiles() {
		tiles[0] = new TIles(0,0,false);
		tiles[1] = new TIles(150,0,false);
		tiles[2] = new TIles(300,0,false);
		tiles[3] = new TIles(0,150,false);
		tiles[4] = new TIles(150,150,false);
		tiles[5] = new TIles(300,150,false);
		tiles[6] = new TIles(0,300,false);
		tiles[7] = new TIles(150,300,false);
		tiles[8] = new TIles(300,300,false);
		
	}
	public void paintline(Graphics g) {
			g.drawLine(150, 50, 150, 400);
			g.drawLine(300, 50, 300, 400);
			g.drawLine(50, 150, 400, 150);
			g.drawLine(50, 300, 400, 300); 

	}

	public bud(ServerStarter s) {
		this.s= s;
		thic = false;
		CreateServerPlayerWindow();
		setupTiles();

	}
	public void cat(Graphics g, int x, int y, int pn) {
		int pt = 0;
		if(p1t) pt =1;
		else pt =2;
		System.out.printf("Cat Request (%d,%d) from %d Current Players turn: %d \n",x,y,pn,pt);
		int a = findTileCord(x);
		int b = findTileCord(y);
		ArrayList<TIles> openones = new ArrayList();
		if(gameOver) {
			RestartGame();
		}
		for(int i = 0; i <tiles.length; i++) {
			if(tiles[i].owner=="p0") {
				openones.add(tiles[i]);
			}
		}
		if(openones.size()==9) {
			g.drawLine(150, 0, 150, 450);
			g.drawLine(275, 0, 270, 450);
			g.drawLine(0, 150, 450, 150);
			g.drawLine(0, 275, 450, 275); 
		}
		for(int i = 0; i <tiles.length; i++) {
			
			if(openones.isEmpty()) {
				//start a new game
				System.out.println("Game over");
				RestartGame();
				
			}
			if(tiles[i].tx == a && tiles[i].ty == b){
			if(tiles[i].owner!="p0") {
			return;
			}else {
				if(pn ==0 && pt==1) {
					g.drawLine(x+35, y+25, x+65, y+100);
					g.drawLine(x+65, y+25, x+35, y+100);
					tiles[i].owner = "p1";
					p1t = false;
				} else if(pn ==1 && pt==2) {
					g.drawOval(x+25, y+25, 65, 75);
					tiles[i].owner = "p2";
					p1t = true;
				}else {

				}
			
			break;
			}
			}
			}
		checkforwinner(pn);
		
	}
	private void checkforwinner(int pn) {
		for(int i = 0; i<3; i++) {
			if(tiles[i*3].owner == tiles[i*3+1].owner && tiles[i*3].owner == tiles[i*3+2].owner && tiles[i*3].hasOwner()) {
				int b = tiles[i*3].y;
				System.out.println("Some one won horizonatlly");
				gameOver=true;
				if(thic) {
					highlight_winnerh(pnl.getGraphics(),b);
				}else {
					highlight_winnerh(p.getGraphics(),b);
				}
			}
			if(tiles[i].owner==tiles[i+3].owner && tiles[i].owner==tiles[i+6].owner && tiles[i].hasOwner()){
				int a = tiles[i].x;
				
				System.out.println("Some one won virtically at "+ a);
				gameOver=true;
				if(thic) {
					highlight_winnerv(pnl.getGraphics(),a);
				}else {
					highlight_winnerv(p.getGraphics(),a);
				}
			}
			if(tiles[0].owner==tiles[4].owner && tiles[0].owner==tiles[8].owner && tiles[0].hasOwner()){				
				System.out.println("Some one won from left diagonal");
				gameOver=true;
				if(thic) {
					highlight_winnerld(pnl.getGraphics());
				}else {
					highlight_winnerld(p.getGraphics());
				}
			}
			if(tiles[2].owner==tiles[4].owner && tiles[2].owner==tiles[6].owner && tiles[2].hasOwner()){				
				System.out.println("Some one won from right diagonal");
				gameOver=true;
				if(thic) {
					highlight_winnerrd(pnl.getGraphics());
				}else {
					highlight_winnerrd(p.getGraphics());
				}
			}
			// 048, 246,
		}	
	}
	private void highlight_winnerh(Graphics g, int b) {
		g.drawRect(0, b+25, 450, 30);
	}
	private void highlight_winnerv(Graphics g, int a) {
		g.drawRect(a+25, 0, 30, 450);
	}
	private void highlight_winnerld(Graphics g) {
		g.drawLine(25,0,450,425);
		g.drawLine(0,25,425,450);
		g.drawLine(0, 25, 25, 0);
		g.drawLine(450,425,425,450);
	}
	private void highlight_winnerrd(Graphics g) {
		g.drawLine(425,0,450,25);
		g.drawLine(0,425,25,450);
		g.drawLine(450, 25, 25, 450);
		g.drawLine(0,425,425,0);
	}
	void RestartGame() {
		p.repaint();
		pnl.repaint();
		setupTiles();
		for(int i =0; i<tiles.length; i++) {
			tiles[i].resetOwner();
		}
		p1t =true;
		gameOver = false;
	}
	public int findTileCord(int z) {
		if(z<150) {
			return 0;
		}else if(150<=z && z<300) {
			return 1;
		}else if(300<z && z<=450) {
			return 2;
		}else {
			return 3;
		}
	}
	
	private void CreateServerPlayerWindow() {
		setTitle("ServerClient");	
		setLayout(null);
		setResizable(false);
		setSize(450,450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//paintline(getGraphics());
		setContentPane(p);
		p.setBorder(new EmptyBorder(0,0,3,3));
		p.setBackground(Color.GREEN);
		p.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int x;
				int y;
				String clickinfo=  "/c/" + e.getX() + "/s/" + e.getY() + "/e/";
				s.sendStuff(clickinfo);
				x = s.getNewTileCords(e.getX());
				y = s.getNewTileCords(e.getY());
				cat(p.getGraphics(),x,y,0);
			}
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		setVisible(true);
	}
	private void CreateWindow() {
	setTitle("Client");	
	setLayout(null);
	setResizable(false);
	setSize(450,450);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setContentPane(pnl);
	pnl.setBorder(new EmptyBorder(0,0,3,3));
	pnl.setBackground(Color.PINK);
	pnl.addMouseListener(new MouseListener() {
	
	
		
		public void mouseClicked(MouseEvent e) {
		int x;
		int y;
		String clickinfo=  "/c/" + e.getX() + "/s/" + e.getY() + "/e/";
		c.sendStuff(clickinfo);
		x = c.getNewTileCords(e.getX());
		y = c.getNewTileCords(e.getY());
		cat(pnl.getGraphics(),x,y,1);
		}
		public void mousePressed(MouseEvent e) {			
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		
	});
	
	setVisible(true);
}
	
	
}

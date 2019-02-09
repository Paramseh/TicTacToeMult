import java.awt.Graphics;
import java.io.*;
import java.net.*;

public class ServerStarter implements Runnable{
boolean p1t = true;
TIles[] tiles = new TIles[9];
ServerSocket socket;
OutputStream out;
InputStream in;
static bud bu;
public static void main(String[] args) {
	ServerStarter s = new ServerStarter();
	
}
public ServerStarter() {
	try {
		socket = new ServerSocket(8889);
		Socket so = socket.accept();
		out = so.getOutputStream();
		in = so.getInputStream();
		bu= new bud(this);
		listen();
	} catch (IOException e) {
	}
}

public void listen() {
Thread listen = new Thread() {
	public void run() {
		while(true) {
		byte[] b = new byte[1024];
		try {
			in.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s = new String(b);
		process(s);
		}
	}
};
listen.start();
}

private void checkIfPlayerWon(int pn) {
	
	for(int i = 0; i<3; i++) {
		if(bu.tiles[i*3].owner == bu.tiles[i*3+1].owner && bu.tiles[i*3].owner == bu.tiles[i*3+2].owner && bu.tiles[i*3].hasOwner()) {
			int a = bu.tiles[i*3].x;
			int b = bu.tiles[i*3].y;
			
			highlight_winner(bu.p.getGraphics(),pn, a , b);
			bu.RestartGame();
		}
	}
}

private void highlight_winner(Graphics g,int pn, int a, int b) {
	if(pn == 0) {
		g.drawRect(a, b, 450, 30);
	}else {
	}
}
public void process(String s) {
int x;
int y;
if(s.startsWith("/c/")) {
	int index = s.indexOf("/s/")+3;
	x = Integer.parseInt(s.substring(3, s.indexOf("/s/")));
	y = Integer.parseInt(s.substring(index, s.indexOf("/e/")));
	x = getNewTileCords(x);
	y = getNewTileCords(y);
	if(x ==1 || y ==1) {
		System.out.println("Error in number conversion");
	}
	bu.cat(bu.p.getGraphics(), x, y, 1);
	//checkIfPlayerWon(1);
	//System.out.println("Client clicked at "+ x + ", " + y + "\n");
	
}

}
public int getNewTileCords(int z) {
	if(z<150) {
		return 0;
	}else if(150<=z && z<300) {
		return 150;
	}else if(300<z && z<=450) {
		return 300;
	}else {
		return 1;
	}
}
public void sendStuff(String s) {
	byte[] b = s.getBytes();
	try {
		out.write(b);
	} catch (IOException e) {
	}
}

public void run() {
	new ServerStarter();
}
}

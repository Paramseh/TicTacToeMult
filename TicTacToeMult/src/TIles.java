
public class TIles {
boolean selected;
int x,y;
int tx,ty;
String owner = "p0";
public TIles(int x, int y, boolean selected) {
	this.x = x;
	this.y = y;
	this.selected = selected;
	tx = findTileCord(x);
	ty = findTileCord(y);
	
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
public boolean hasOwner() {
	if(owner == "p0") {
	return false;
	}else {
		return true;
	}
}
public void resetOwner() {
	owner = "p0";
}

}

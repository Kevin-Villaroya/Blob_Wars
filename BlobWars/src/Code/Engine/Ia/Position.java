package Code.Engine.Ia;

public class Position {
	public int posX;
	public int posY;
	
	Position(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	Position(Position position){
		this.posX = position.posX;
		this.posY = position.posY;
	}
	
}

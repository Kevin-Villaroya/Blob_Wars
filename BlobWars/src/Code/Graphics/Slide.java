package Code.Graphics;

public class Slide {

	private float min;
	private float max;

	private float taille;
	private float tailleY;
	private int x;
	private int y;

	private float curseur;

	public Slide(float min, float max, int x, int y, float taille) {
		this.min = min;
		this.max = max;
		this.taille = taille;
		this.x = x;
		this.y = y;
		this.setTailleY(15);
	}
	
	public Boolean estCliquer(int x, int y) {
		if( x > this.x && x< this.x + this.taille) {
			if( y >= this.y - 7 && y <= this.y + 7 + tailleY) {
				return true;
			}
		}
		return false;
	}
	
	public void deplaceCurseur(int x ,int y) {
		this.curseur = (x-this.x)/this.taille;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getTaille() {
		return taille;
	}

	public void setTaille(float taille) {
		this.taille = taille;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getCurseur() {
		return curseur;
	}

	public void setCurseur(float curseur) {
		this.curseur = curseur;
	}

	public float getTailleY() {
		return tailleY;
	}

	public void setTailleY(float tailleY) {
		this.tailleY = tailleY;
	}
}

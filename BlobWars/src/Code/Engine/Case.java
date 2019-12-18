package Code.Engine;

public class Case {
	private static final Joueur NULL = null;
	private Boolean accessible;
	private Joueur player;
	private int x;
	private int y;
	private int largeur;
	private int hauteur;

	public Case(Boolean accessible, int x, int y, int largeur, int hauteur) {
		this.accessible = accessible;
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		player = NULL;
	}

	public Boolean estCliquer(int x, int y) {
		if (x > this.x && x < this.x + this.largeur) {
			if (y > this.y && y < this.y + this.hauteur) {
				return true;
			}
		}
		return false;
	}

	public Boolean getOccuper() {
		if(player != NULL) {
			return true;
		}
		return false;
	}
	
	public Boolean getAccessible() {
		return accessible;
	}

	public void setAccessible(Boolean accessible) {
		this.accessible = accessible;
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

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public Joueur getPlayer() {
		return player;
	}

	public void setPlayer(Joueur player) {
		this.player = player;
	}
	
	public boolean estDisponible() {
		return this.getAccessible() && !this.getOccuper();
	}
}

package Code.Graphics;


public class Bouton {
	private int x;
	private int y;
	private int largeur;
	private int hauteur;

	private String contenu;

	public Bouton(int x, int y, int hauteur, int largeur, String contenu) {
		this.x = x;
		this.y = y;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.contenu = contenu;
	}

	public Boolean estCliquer(int x, int y) {
		if (x > this.x && x < this.x + this.largeur) {
			if (y > this.y && y < this.y + this.hauteur) {
				return true;
			}
		}
		return false;
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

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public int getHauteur() {
		return this.hauteur;
	}

	public void setHauteur(int y) {
		this.hauteur = y;
	}

	public int getLargeur() {
		return this.largeur;
	}

	public void setLargeur(int y) {
		this.largeur = y;
	}

}

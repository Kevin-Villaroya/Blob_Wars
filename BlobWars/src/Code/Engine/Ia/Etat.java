package Code.Engine.Ia;

import java.util.ArrayList;

import Code.Engine.Joueur;

public class Etat {

	int[][] jeu;
	int valeur; // la valeur dependras du joueur qui l'appelle

	ArrayList<Position> posJoueur0;
	ArrayList<Position> posJoueur1;
	ArrayList<Position> posJoueur2;
	ArrayList<Position> posJoueur3;

	public Etat(int[][] etat, int joueur) {

		this.jeu = IA.clone2D(etat);

		this.posJoueur0 = new ArrayList<>();
		this.posJoueur1 = new ArrayList<>();
		this.posJoueur2 = new ArrayList<>();
		this.posJoueur3 = new ArrayList<>();
	}

	public Etat(Etat etat) {

		this.jeu = IA.clone2D(etat.jeu);

		this.posJoueur0 = copyArrayPostition(etat.posJoueur0);
		this.posJoueur1 = copyArrayPostition(etat.posJoueur1);
		this.posJoueur2 = copyArrayPostition(etat.posJoueur2);
		this.posJoueur3 = copyArrayPostition(etat.posJoueur3);

	}

	public int calculerValeurEtatJeu(int joueur) {
		int value = 0;

		for (int[] array : this.jeu) {
			for (int player : array) {
				if (!(player < 0) && Joueur.isAllied(player, joueur)) {
					value += 1;
				}
			}
		}

		return value;
	}

	private boolean isWin(int joueur) {
		boolean aGagner = true;

		for (int i = 0; i < 4; i++) {
			if (!Joueur.isAllied(joueur, i) && getPositionJoueur(i).size() > 0) {
				aGagner = false;
			}
		}
		return aGagner;
	}

	private boolean isLose(int joueur) {
		return !(getPositionJoueur(joueur).size() > 0);
	}

	public void setPosJoueur() {
		for (int i = 0; i < jeu.length; i++) {
			for (int j = 0; j < jeu.length; j++) {
				switch (jeu[i][j]) {
				case 0:
					posJoueur0.add(new Position(i, j));
					break;
				case 1:
					posJoueur1.add(new Position(i, j));
					break;
				case 2:
					posJoueur2.add(new Position(i, j));
					break;
				case 3:
					posJoueur3.add(new Position(i, j));
					break;
				}
			}
		}
	}

	public ArrayList<Position> getPositionJoueur(int joueur) {
		switch (joueur) {
		case 0:
			return this.posJoueur0;
		case 1:
			return this.posJoueur1;
		case 2:
			return this.posJoueur2;
		case 3:
			return this.posJoueur3;
		}
		return null;
	}

	public void setPositionJoueur(int joueur, ArrayList<Position> position) {
		switch (joueur) {
		case 0:
			this.posJoueur0 = position;
		case 1:
			this.posJoueur1 = position;
		case 2:
			this.posJoueur2 = position;
		case 3:
			this.posJoueur3 = position;
		}
	}

	private ArrayList<Position> copyArrayPostition(ArrayList<Position> posJoueur) {
		ArrayList<Position> newArray = new ArrayList<Position>();
		for (int i = 0; i < posJoueur.size(); i++) {
			newArray.add(new Position(posJoueur.get(i)));
		}
		return newArray;
	}

	public void removePosJoueur(int x, int y, int joueur) {
		for (int i = 0; i < this.getPositionJoueur(joueur).size(); i++) {
			if (this.getPositionJoueur(joueur).get(i).posX == x && this.getPositionJoueur(joueur).get(i).posY == y) {
				this.getPositionJoueur(joueur).remove(i);
			}
		}
	}

}
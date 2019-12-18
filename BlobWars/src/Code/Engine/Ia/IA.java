package Code.Engine.Ia;

import java.util.ArrayList;
import java.lang.Math;

import Code.Engine.Case;
import Code.Engine.GestionTour;
import Code.Engine.Joueur;
import Code.Graphics.State.Plateau;
import Code.Graphics.State.Parameters;

public class IA {
	static private IA ia;

	public int typeDeMouvement;
	public Case caseAJouer;
	public Case caseCourante;

	private IA() {
	}

	static public IA getInstance() {
		if (ia == null) {
			ia = new IA();
		}
		return ia;
	}

	public void calculProchainCoup(int joueur, GestionTour gestionTour, int profondeur) {

		Etat jeuActuel = new Etat(convert(), joueur);

		jeuActuel.setPosJoueur();

		Noeud noeud = new Noeud(profondeur, jeuActuel, -1, 9999);
		Etat etatDeJeu = minImax(joueur, gestionTour, noeud, noeud);
		System.out.println(etatDeJeu.valeur);
		caseAJouer = chercheCaseAJouer(jeuActuel, etatDeJeu);

		if (this.thereIsCaseAdjacente(caseAJouer, jeuActuel, joueur)) {
			this.typeDeMouvement = 0;
		} else {
			this.typeDeMouvement = 1;
			caseCourante = chercheCaseCourante(jeuActuel, etatDeJeu);
		}
	}

	private Case chercheCaseCourante(Etat jeuActuel, Etat nouveauJeu) {
		for (int i = 0; i < jeuActuel.jeu.length; i++) {
			for (int j = 0; j < jeuActuel.jeu.length; j++) {
				if (jeuActuel.jeu[i][j] != nouveauJeu.jeu[i][j] && nouveauJeu.jeu[i][j] == -1) {

					return Plateau.getTerrain().getZone().get(i).get(j);

				}
			}
		}
		return null;
	}

	private Case chercheCaseAJouer(Etat jeuActuel, Etat nouveauJeu) {
		for (int i = 0; i < jeuActuel.jeu.length; i++) {
			for (int j = 0; j < jeuActuel.jeu.length; j++) {
				if (jeuActuel.jeu[i][j] != nouveauJeu.jeu[i][j] && jeuActuel.jeu[i][j] == -1) {

					return Plateau.getTerrain().getZone().get(i).get(j);

				}
			}
		}
		return null;
	}

	private Etat minImax(int joueur, GestionTour gestionTour, Noeud noeudCourant, Noeud noeudPere) {

		Etat choisis = noeudCourant.terrain;

		boolean myTurn = gestionTour.getTour().getID() == joueur;
		boolean turnAllie = Joueur.isAllied(gestionTour.getTour().getID(), joueur);
		noeudCourant.terrains = simulerTousLesCoups(noeudCourant.terrain, gestionTour.getTour().getID());

		noeudCourant.profondeur--;

		gestionTour.nextTour();

		if (noeudCourant.profondeur >= 0 && noeudCourant.terrains.length != 0) {

			for (int i = 0; i < noeudCourant.terrains.length; i++) {
				if (minImax(joueur, new GestionTour(gestionTour), new Noeud(noeudCourant.profondeur,
						noeudCourant.terrains[i], noeudCourant.alpha, noeudCourant.beta),noeudCourant) == null) {
					break;
				}
			}

			if (myTurn || turnAllie) {
				choisis = max(noeudCourant.terrains);
				if (noeudCourant.beta <= noeudCourant.alpha) {
					return null;
				}
				noeudCourant.terrain.valeur = choisis.valeur;
				noeudPere.alpha = java.lang.Math.max(choisis.valeur, noeudCourant.alpha);

				

			} else {
				
				choisis = min(noeudCourant.terrains);
				if (noeudCourant.beta <= noeudCourant.alpha) {
					return null;
				}
				noeudCourant.terrain.valeur = choisis.valeur;
				noeudPere.beta = java.lang.Math.min(choisis.valeur, noeudCourant.beta);
				

			}
		} else {
			noeudCourant.terrain.valeur = noeudCourant.terrain.calculerValeurEtatJeu(joueur);
		}

		return choisis;
	}

	private Etat min(Etat[] etatTerrain) {
		int min = etatTerrain[0].valeur;
		Etat monChoix = etatTerrain[0];

		for (int i = 0; i < etatTerrain.length; i++) {

			if (etatTerrain[i].valeur < min) {
				min = etatTerrain[i].valeur;
				monChoix = etatTerrain[i];
			}

		}
		return monChoix;
	}

	private Etat max(Etat[] etatTerrain) {
		int max = etatTerrain[0].valeur;
		Etat monChoix = etatTerrain[0];

		for (int i = 0; i < etatTerrain.length; i++) {

			if (etatTerrain[i].valeur > max) {
				max = etatTerrain[i].valeur;
				monChoix = etatTerrain[i];
			}

		}
		return monChoix;
	}

	private Etat[] simulerTousLesCoups(Etat terrain, int joueur) {
		ArrayList<Etat> choix = new ArrayList<>();

		for (int i = 0; i < terrain.getPositionJoueur(joueur).size(); i++) {
			simulerCoupsDePosition(choix, terrain, joueur, terrain.getPositionJoueur(joueur).get(i).posX,
					terrain.getPositionJoueur(joueur).get(i).posY);
		}

		Etat[] tabRetour = new Etat[choix.size()];
		for (int i = 0; i < choix.size(); i++) {
			tabRetour[i] = choix.get(i);
		}
		return tabRetour;
	}

	private void simulerCoupsDePosition(ArrayList<Etat> choix, Etat terrain, int joueur, int posX, int posY) {
		for (int p = -2; p <= 2; p++) {
			for (int k = -2; k <= 2; k++) {
				int x = posX + p;
				int y = posY + k;
				boolean isNotOutOfBounds = x >= 0 && x < terrain.jeu.length && y >= 0 && y < terrain.jeu[posX].length;
				boolean notADeplacement = p == 0 && k == 0;

				if (isNotOutOfBounds && terrain.jeu[x][y] == -1 && !notADeplacement) {

					Etat temp = new Etat(terrain);

					if (Math.abs(k) == 2 || Math.abs(p) == 2) {
						temp.jeu[posX][posY] = -1;
						temp.removePosJoueur(x, y, joueur);
					}

					temp.jeu[x][y] = joueur;
					temp.getPositionJoueur(joueur).add(new Position(x, y));
					convertEnemies(joueur, x, y, temp);

					if (!doesContain(choix, temp)) {
						choix.add(temp);
					}

				}
			}
		}
	}

	private void convertEnemies(int joueur, int x, int y, Etat temp) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				boolean notOutOfBounds = x + i >= 0 && x + i < temp.jeu.length && y + j >= 0 && y + j < temp.jeu.length;

				if (notOutOfBounds) {

					int playerOnCase = temp.jeu[x + i][y + j];
					boolean characterOnIt = !(playerOnCase == -1 || playerOnCase == -2);

					if (characterOnIt && !Joueur.isAllied(joueur, playerOnCase)) {
						temp.removePosJoueur(x + i, y + j, playerOnCase);
						temp.jeu[x + i][y + j] = joueur;
						temp.getPositionJoueur(joueur).add(new Position(x + i, y + j));
					}
				}
			}
		}
	}

	public int[][] convert() {
		int[][] terrain = new int[Plateau.getTerrain().getZone().size()][];
		for (int i = 0; i < Plateau.getTerrain().getZone().size(); i++) {
			terrain[i] = new int[Plateau.getTerrain().getZone().get(i).size()];
			for (int j = 0; j < Plateau.getTerrain().getZone().get(i).size(); j++) {
				if (Plateau.getTerrain().getZone().get(i).get(j).getPlayer() != null) {
					terrain[i][j] = Plateau.getTerrain().getZone().get(i).get(j).getPlayer().getID();
				} else if (Plateau.getTerrain().getZone().get(i).get(j).getAccessible()) {
					terrain[i][j] = -1;
				} else {
					terrain[i][j] = -2;
				}
			}
		}
		return terrain;
	}

	public boolean doesContain(ArrayList<Etat> choix, Etat temp) {
		for (Etat t : choix) {
			boolean b = true;
			for (int x = 0; x < t.jeu.length; x++) {
				for (int y = 0; y < t.jeu[x].length; y++) {
					if (t.jeu[x][y] != temp.jeu[x][y]) {
						b = false;
					}
				}
			}

			if (b) {
				return true;
			}
		}
		return false;
	}

	public int indexOf(Object[] tab, Object o) {
		int i = 0;
		for (Object object : tab) {
			if (o.equals(object)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private boolean thereIsCaseAdjacente(Case c, Etat terrain, int joueur) {
		int posX = Plateau.getTerrain().getPosXCase(c);
		int posY = Plateau.getTerrain().getPosYCase(c);
		for (int p = -1; p <= 1; p++) {
			for (int k = -1; k <= 1; k++) {
				int x = posX + p;
				int y = posY + k;
				boolean isNotOutOfBounds = x >= 0 && x < terrain.jeu.length && y >= 0 && y < terrain.jeu[x].length;
				boolean notADeplacement = p == 0 && k == 0;

				if (isNotOutOfBounds && terrain.jeu[x][y] == joueur && !notADeplacement) {
					return true;
				}
			}
		}
		return false;
	}

	public static int[][] clone2D(int[][] tab) {
		int[][] temp = tab.clone();
		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i].clone();
		}
		return temp;
	}

}
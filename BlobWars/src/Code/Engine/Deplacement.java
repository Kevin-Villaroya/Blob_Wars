package Code.Engine;

import java.util.ArrayList;

import Code.Graphics.State.Plateau;
import Code.Graphics.State.MenuState;
import Code.Graphics.State.Parameters;

public class Deplacement {

	private boolean joueurStuck[];

	private ArrayList<Case> caseAdjacente;
	private ArrayList<Case> caseLointaine;

	public Case caseCourante;

	public Deplacement() {
		caseAdjacente = new ArrayList<Case>();
		caseLointaine = new ArrayList<Case>();

		joueurStuck = new boolean[4];
		for (int i = 0; i < 4; i++) {
			joueurStuck[i] = false;
		}
	}

	public void resetDeplacement() {
		caseAdjacente = new ArrayList<Case>();
		caseLointaine = new ArrayList<Case>();
	}

	public void AjouterCaseAccessible(Case c, int x, int y) {
		this.resetDeplacement();
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {

				ajouterUneCase(c, x, y, i, j);
			}
		}
	}

	private void ajouterUneCase(Case c, int x, int y, int i, int j) {
		if (!Plateau.getTerrain().outOfGame(x + i, y + j)
				&& Plateau.getTerrain().getZone().get(x + i).get(y + j).estDisponible()) {
			if (i == -2 || i == 2 || j == -2 || j == 2) {
				ajouteCaseLointaine(Plateau.getTerrain().getZone().get(x + i).get(y + j));
			} else if (i != 0 || j != 0) {
				ajouteCaseAdjacente(Plateau.getTerrain().getZone().get(x + i).get(y + j));
			}
		}
	}

	public void convertion(Case cases, Joueur joueur, GestionTour gestionTour) {
		int x = 0;
		int y = 0;
		boolean convertit = false;

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

				for (int k = 0; k < Plateau.getTerrain().getZone().size(); k++) {
					if (Plateau.getTerrain().getZone().get(k).contains(cases)) {
						y = Plateau.getTerrain().getZone().get(k).indexOf(cases) + j;
						x = k + i;
					}
				}

				if (x >= 0 && y >= 0 && x < Plateau.getTerrain().getZone().size()
						&& y < Plateau.getTerrain().getZone().get(0).size()) {

					if (Plateau.getTerrain().getZone().get(x).get(y).getPlayer() != null && !Plateau.getTerrain()
							.getZone().get(x).get(y).getPlayer().getEquipe().equals(joueur.getEquipe())) {

						Plateau.getTerrain().getZone().get(x).get(y).getPlayer()
								.setScore(Plateau.getTerrain().getZone().get(x).get(y).getPlayer().getScore() - 1);
						convertit = true;

						if (Plateau.getTerrain().getZone().get(x).get(y).getPlayer().getScore() == 0) {

							Parameters.nbrPlayer -= 1;

							int emplacement = gestionTour.getOrdreJoueur()
									.get(Plateau.getTerrain().getZone().get(x).get(y).getPlayer().getEquipe()
											.ordinal())
									.indexOf(Plateau.getTerrain().getZone().get(x).get(y).getPlayer());
							gestionTour.getOrdreJoueur().get(
									Plateau.getTerrain().getZone().get(x).get(y).getPlayer().getEquipe().ordinal())
									.remove(emplacement);

							gestionTour.editEquipe(Plateau.getTerrain().getZone().get(x).get(y).getPlayer());
						}

						Plateau.getTerrain().getZone().get(x).get(y).setPlayer(joueur);
						joueur.setScore(joueur.getScore() + 1);

					}
				}
			}
		}
		if (convertit) {
			MenuState.conquete.play(1, MenuState.volumeSounds);
		}
	}

	public void deplacer(Case arriver, int type, Deplacement deplacement, GestionTour gestionTour) {
		MenuState.dedoublement.play(1, MenuState.volumeSounds);
		if (type == 0) {
			arriver.setPlayer(gestionTour.getTour());
			gestionTour.getTour().setScore(gestionTour.getTour().getScore() + 1);
		} else {
			arriver.setPlayer(gestionTour.getTour());
			this.caseCourante.setPlayer(null);
		}
		

		if (type == 0) {
			convertion(arriver, gestionTour.getTour(), gestionTour);
		} else {
			convertion(arriver, gestionTour.getTour(), gestionTour);
		}
	}

	private void ajouteCaseAdjacente(Case c) {
		if (!this.caseAdjacente.contains(c)) {
			this.caseAdjacente.add(c);
		}
	}

	private void ajouteCaseLointaine(Case c) {
		if (!this.caseLointaine.contains(c)) {
			this.caseLointaine.add(c);
		}
	}

	public boolean isStuck(Joueur joueur) {
		for (int i = 0; i < Plateau.getTerrain().getZone().size(); i++) {
			for (int j = 0; j < Plateau.getTerrain().getZone().get(0).size(); j++) {
				if (Plateau.getTerrain().getZone().get(i).get(j).getPlayer() == joueur) {
					for(int k=-2;k<=2;k++) {
						for(int m=-2;m<=2;m++) {
							if(!Plateau.getTerrain().outOfGame(i+k, j+m) && Plateau.getTerrain().getZone().get(i+k).get(j+m).estDisponible()) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
	}

	public void coupPossibleDeJoueur(Joueur joueur) {
		for (int i = 0; i < Plateau.getTerrain().getZone().size(); i++) {
			for (int j = 0; j < Plateau.getTerrain().getZone().get(0).size(); j++) {
				if (Plateau.getTerrain().getZone().get(i).get(j).getPlayer() == joueur) {
					AjouterCaseAccessible(Plateau.getTerrain().getZone().get(i).get(j), i, j);
				}
			}
		}
	}

	public boolean endGameAllStucks(){
		
		int numberOfStucks = 0;
		
		for(int i=0;i<4;i++) {
			if(!Parameters.getJoueurs()[i].getType().name().equals("none")) {
				if(this.isStuck(Parameters.getJoueurs()[i])) {
					numberOfStucks++;
				}
				
			}
		}
		
		if(numberOfStucks >= Parameters.nbrPlayer - 1) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Case> getCaseAdjacente() {
		return caseAdjacente;
	}

	public void setCaseAdjacente(ArrayList<Case> caseAdjacente) {
		this.caseAdjacente = caseAdjacente;
	}

	public ArrayList<Case> getCaseLointaine() {
		return caseLointaine;
	}

	public void setCaseLointaine(ArrayList<Case> caseLointaine) {
		this.caseLointaine = caseLointaine;
	}

}

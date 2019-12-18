package Code.Engine;

import java.util.ArrayList;

import Code.Graphics.State.Parameters;

public class GestionTour {

	private ArrayList<ArrayList<Joueur>> ordreJoueur;

	private Joueur tour;
	private int tourEquipe;
	private int tourJoueur[];
	private int nbJoueur;

	public GestionTour() {
		ordreJoueur = new ArrayList<ArrayList<Joueur>>();
		tourEquipe = 0;
		tourJoueur = new int[4];
		this.nbJoueur = Parameters.nbrPlayer;

	}

	public GestionTour(GestionTour gestionTour) {

		ordreJoueur = new ArrayList<ArrayList<Joueur>>();
		tourJoueur = new int[4];

		tourEquipe = gestionTour.tourEquipe;

		this.nbJoueur = gestionTour.getNbJoueur();

		for (int i = 0; i < gestionTour.ordreJoueur.size(); i++) {

			this.ordreJoueur.add(new ArrayList<Joueur>());

			for (int j = 0; j < gestionTour.ordreJoueur.get(i).size(); j++) {
				this.ordreJoueur.get(i).add(gestionTour.ordreJoueur.get(i).get(j));
			}

		}
		for (int i = 0; i < 4; i++) {
			tourJoueur[i] = gestionTour.tourJoueur[i];
		}
		this.tour = gestionTour.getTour();

	}

	public boolean arretTour() {
		boolean test1 = false;
		for (int i = 0; i < this.ordreJoueur.size(); i++) {
			if (this.ordreJoueur.get(i).size() != 0) {
				if (this.ordreJoueur.get(i).size() != 0 && test1) {
					return false;
				}
				test1 = true;
			}
		}
		return true;
	}

	public void nextTour() { // change le joueur par le bon

		this.tourEquipe++;

		this.tourEquipe = this.tourEquipe % 4;

		while (this.ordreJoueur.get(this.tourEquipe).size() == 0)

		{
			this.tourEquipe++;
			this.tourEquipe = this.tourEquipe % 4;
		}

		this.tourJoueur();

		this.tourJoueur[this.tourEquipe]++;

		if (this.ordreJoueur.get(this.tourEquipe).size() != 0) {
			this.tourJoueur[this.tourEquipe] = this.tourJoueur[this.tourEquipe]
					% this.ordreJoueur.get(this.tourEquipe).size();
		}
	}

	public void editEquipe(Joueur joueur) {
		if (getOrdreJoueur().get(joueur.getEquipe().ordinal()).size() == 0) {
			Parameters.nbrEquipe -= 1;
		}
	}

	public void initGestionJoueur() {
		this.tourEquipe++;

		this.tourEquipe = this.tourEquipe % 4;

		while (this.ordreJoueur.get(this.tourEquipe).size() == 0)
		{
			this.tourEquipe++;
			this.tourEquipe = this.tourEquipe % 4;
		}

		this.tourEquipe = Parameters.tourEquipe;

		tourJoueur[tourEquipe] = Parameters.tourJoueur;
		

		this.tourJoueur[this.tourEquipe]++;

		if (this.ordreJoueur.get(this.tourEquipe).size() != 0) {
			this.tourJoueur[this.tourEquipe] = this.tourJoueur[this.tourEquipe]
					% this.ordreJoueur.get(this.tourEquipe).size();
		}
	}

	public void tourJoueur() {
		if (this.tourJoueur[this.tourEquipe] >= ordreJoueur.get(this.tourEquipe).size()) {
			this.tourJoueur[this.tourEquipe]--;
		}
		this.tour = ordreJoueur.get(this.tourEquipe).get(this.tourJoueur[this.tourEquipe]);
	}

	public Joueur getTour() {
		return tour;
	}

	public void setTour(Joueur tour) {
		this.tour = tour;
	}

	public ArrayList<ArrayList<Joueur>> getOrdreJoueur() {
		return ordreJoueur;
	}

	public int getTourEquipe() {
		return tourEquipe;
	}

	public void setTourEquipe(int tourEquipe) {
		this.tourEquipe = tourEquipe;
	}

	public int[] getTourJoueur() {
		return tourJoueur;
	}

	public void setTourJoueur(int tourJoueur[]) {
		this.tourJoueur = tourJoueur;
	}

	public int getNbJoueur() {
		return nbJoueur;
	}

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

}

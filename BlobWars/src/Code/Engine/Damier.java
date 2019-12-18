package Code.Engine;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import Code.Graphics.State.Editor;
import Code.Graphics.State.MenuState;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.Math;

public class Damier {
	private ArrayList<ArrayList<Case>> zone = new ArrayList<ArrayList<Case>>();

	private Color couleur;

	public Damier(int nbhauteur, int nblargeur, int bordure, int fenetreHauteur, int fenetreLargeur, Color couleur) {

		this.couleur = couleur;

		int tailleX = fenetreLargeur / (nblargeur + 2);
		int tailleY = fenetreHauteur / (nbhauteur + 2);

		int taille = Math.min(tailleX, tailleY);

		for (int i = 0; i < nblargeur; i++) {

			zone.add(i, new ArrayList<Case>());

			for (int j = 0; j < nbhauteur; j++) {

				zone.get(i).add(j, new Case(true, fenetreLargeur / 2 - (MenuState.zorque.getWidth("W") * 15) + i * taille, taille + j * taille, taille, taille));

			}

		}

	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public ArrayList<ArrayList<Case>> getZone() {
		return zone;
	}

	public void setZone(ArrayList<ArrayList<Case>> zone) {
		this.zone = zone;
	}
	
	public boolean outOfGame(int x, int y) {
		if (x >= 0 && x < getZone().size()) {
			if (y >= 0 && y < getZone().get(0).size()) {
				return false;
			}
		}
		return true;
	}
	
	public int getPosXCase(Case c) {
		for(int i=0;i<this.getZone().size();i++) {
			for(int j=0;j<this.getZone().size();j++) {
				if(c==this.getZone().get(i).get(j)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getPosYCase(Case c) {
		for(int i=0;i<this.getZone().size();i++) {
			for(int j=0;j<this.getZone().size();j++) {
				if(c==this.getZone().get(i).get(j)) {
					return j;
				}
			}
		}
		return -1;
	}
	
	static public void terrain1(Damier terrain) {

		for (int i = 0; i < terrain.getZone().size(); i++) {
			for (int j = 0; j < terrain.getZone().get(0).size(); j++) {
				if ((i == 0 && j == 1) || (i == 0 && j == terrain.getZone().get(0).size() - 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == 1 && j == 0) || (i == 1 && j == terrain.getZone().get(0).size() - 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == terrain.getZone().size() - 2 && j == 0)
						|| (i == terrain.getZone().size() - 2 && j == terrain.getZone().get(0).size() - 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == terrain.getZone().size() - 1 && j == 1)
						|| (i == terrain.getZone().size() - 1 && j == terrain.getZone().get(0).size() - 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				}
			}

		}

	}

	static public void terrain2(Damier terrain) {

		for (int i = 0; i < terrain.getZone().size(); i++) {
			for (int j = 0; j < terrain.getZone().get(0).size(); j++) {

				if ((i == 0 && j == 2) || (i == 0 && j == terrain.getZone().get(0).size() - 3)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == 1 && j == 2) || (i == 1 && j == terrain.getZone().get(0).size() - 3)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == 2 && j == 0) || (i == 2 && j == 1)
						|| (i == 2 && j == terrain.getZone().get(0).size() - 2)
						|| (i == 2 && j == terrain.getZone().get(0).size() - 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == terrain.getZone().size() - 3 && j == 0)
						|| (i == terrain.getZone().size() - 3 && j == 1)
						|| (i == terrain.getZone().size() - 3 && j == terrain.getZone().get(0).size() - 1)
						|| (i == terrain.getZone().size() - 3 && j == terrain.getZone().get(0).size() - 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == terrain.getZone().size() - 2 && j == 2)
						|| (i == terrain.getZone().size() - 2 && j == terrain.getZone().get(0).size() - 3)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == terrain.getZone().size() - 1 && j == 2)
						|| (i == terrain.getZone().size() - 1 && j == terrain.getZone().get(0).size() - 3)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				}

			}
		}

	}

	static public void terrain3(Damier terrain) {
		int centreX = (terrain.getZone().size() - 1) / 2;// 3
		int centreY = (terrain.getZone().get(0).size() - 1) / 2;// 3

		for (int i = 0; i < terrain.getZone().size(); i++) {
			for (int j = 0; j < terrain.getZone().get(0).size(); j++) {

				if ((i == centreX - 1 && j == centreY) || (i == centreX - 1 && j == centreY + 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX && j >= centreY - 1) && (i == centreX && j <= centreY + 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX + 1 && j >= centreY - 1) && (i == centreX + 1 && j <= centreY + 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX + 2 && j == centreY) || (i == centreX + 2 && j == centreY + 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				}
			}
		}
	}

	static public void terrain4(Damier terrain) {

		int centreX = (terrain.getZone().size() - 1) / 2;// 3
		int centreY = (terrain.getZone().get(0).size() - 1) / 2;// 3

		for (int i = 0; i < terrain.getZone().size(); i++) {
			for (int j = 0; j < terrain.getZone().get(0).size(); j++) {

				if ((i == centreX - 1 && j == centreY) || (i == centreX - 1 && j == centreY + 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX && j == centreY - 1) || (i == centreX && j == centreY + 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX + 1 && j == centreY - 1) || (i == centreX + 1 && j == centreY + 2)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				} else if ((i == centreX + 2 && j == centreY) || (i == centreX + 2 && j == centreY + 1)) {
					terrain.getZone().get(i).get(j).setAccessible(false);
				}
			}
		}
	}
	
	static public Damier terrain5(Damier map) {

		Damier carte = new Damier(map.getZone().size(), map.getZone().get(0).size(), 1, MenuState.hauteurFenetre,
				MenuState.largeurFenetre, Color.black);

		int hd = 0;
		int hg = Editor.terrainEdit.getZone().size() - 1;

		int bdY = Editor.terrainEdit.getZone().get(0).size() - 1;
		int bdX = 0;

		int bgX = Editor.terrainEdit.getZone().size() - 1;
		int bgY = Editor.terrainEdit.getZone().get(0).size() - 1;

		for (int i = 0; i < map.getZone().size(); i++) {

			for (int j = 0; j < map.getZone().get(0).size(); j++) {

				if (i < map.getZone().size() / 2 && j < map.getZone().get(0).size() / 2) { // en
																							// haut
																							// a
																							// gauche

					carte.getZone().get(i).get(j)
							.setAccessible(Editor.terrainEdit.getZone().get(hg).get(j).getAccessible());

				} else if (i >= map.getZone().size() / 2 && j < map.getZone().get(0).size() / 2) {// haut
																									// droite

					carte.getZone().get(i).get(j)
							.setAccessible(Editor.terrainEdit.getZone().get(i).get(j).getAccessible());

				} else if (i < map.getZone().size() / 2 && j >= map.getZone().get(0).size() / 2) { // bas
																									// a
																									// gauche

					int y = map.getZone().size() - (j - map.getZone().get(0).size() / 2) - 1;
					carte.getZone().get(i).get(y).setAccessible(Editor.terrainEdit.getZone().get(bgX)
							.get((j - map.getZone().get(0).size() / 2)).getAccessible());

				} else if (i >= map.getZone().size() / 2 && j >= map.getZone().get(0).size() / 2) { // bas
																									// a
																									// droite

					int y = map.getZone().size() - (j - map.getZone().get(0).size() / 2) - 1;
					carte.getZone().get(i).get(y).setAccessible(Editor.terrainEdit.getZone().get(i)
							.get((j - map.getZone().get(0).size() / 2)).getAccessible());

				}

				if (i < map.getZone().size() / 2) {
					hd = -1;
					bdX = -1;
				}

				if (j < map.getZone().size() / 2) {
					bdY = Editor.terrainEdit.getZone().get(0).size();
					bgY = Editor.terrainEdit.getZone().get(0).size();
				}
				bdY--;
				bgY--;
			}
			bdY = Editor.terrainEdit.getZone().get(0).size();
			hd++;
			hg--;
			bdX++;
			bgX--;
			bgY--;
		}

		return carte;
	}
	
}

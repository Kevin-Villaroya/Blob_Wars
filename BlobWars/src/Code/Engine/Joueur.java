package Code.Engine;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Code.Graphics.State.Parameters;

public class Joueur {

	public enum Equipe {
		red, blue, yellow, green;
	}

	public enum Type {
		none, joueur, ia;
	}

	public enum Niveau {
		tres_facile, facile, moyen, difficile, tres_difficile, overkill;
	}

	private static final Image NULL = null;

	private String nom;
	private Image icone;
	private Type type;
	private Equipe equipe;
	private Niveau niveau;
	private int indexIcone;
	private int score;

	static public ArrayList<Image> listeImage;
	static public ArrayList<Animation> listeAnimation;

	public Joueur(String nom) {
		this.score = 0;
		this.setNom(nom);
		this.type = Type.none;
		this.equipe = Equipe.red;
		niveau = Niveau.tres_facile;

		try {
			icone = new Image("Assets/Image/Characters/linux.png");
			icone.setName("linux");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indexIcone = 0;
	}

	public void noIcone() {
		this.icone = NULL;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Image getIcone() {
		return icone;
	}

	public void setIcone() {
		indexIcone++;
		if (indexIcone == listeImage.size()) {
			indexIcone = 0;
		}
		this.icone = listeImage.get(indexIcone);
	}

	public void setIcone(Image icone) {
		this.icone = icone;
	}

	public int getIndexIcone() {
		return this.indexIcone;
	}

	public void setIndexIcone(int indexIcone) {
		this.indexIcone = indexIcone;
	}

	public Type getType() {
		return type;
	}

	public void setType() {

		int index = this.getType().ordinal();

		index++;
		if (index == Type.values().length) {
			index = 0;
		}
		this.type = Type.values()[index];

	}

	public void setType(Type type) {
		this.type = type;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe() {

		int index = this.getEquipe().ordinal();

		index++;
		if (index == Equipe.values().length) {
			index = 0;
		}
		this.equipe = Equipe.values()[index];

	}

	public void setEquipe(Joueur.Equipe equipe) {
		this.equipe = equipe;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public int getProfondeur() {
		return this.niveau.ordinal() + 1;
	}

	public void setNiveau() {

		int index = this.getNiveau().ordinal();

		index++;
		if (index == Niveau.values().length) {
			index = 0;
		}
		this.niveau = Niveau.values()[index];

	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Animation getAnimation() {
		return Joueur.listeAnimation.get(this.indexIcone);
	}

	public int getID() {
		for (int i = 0; i < 4; i++) {

			if (Parameters.getJoueurs()[i] == this) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isAllied(int j1, int j2) {
		if (j1 > 0 && j1 <= 3 && j2 > 0 && j2 <= 3) {
			if (Parameters.getJoueurs()[j1] != null && Parameters.getJoueurs()[j2] != null) {
				return Parameters.getJoueurs()[j1].getEquipe().equals(Parameters.getJoueurs()[j2].getEquipe());
			}
		}
		return false;
	}

	public String toString() {
		String buffer = (this.getID() + 1) + " ";
		if (this.type.name().equals("joueur"))
			buffer += this.type.name() + " ";
		else
			buffer += this.niveau.name() + " ";
		buffer += this.nom + " " + this.equipe.name() + " " + this.icone.getName() + " " + this.getIndexIcone();
		return buffer;
	}

	public Color couleurJoueur() {
		Color couleur;

		if (getEquipe().name().equals("red")) {
			couleur = new Color(255, 0, 0, (float) 0.8);
		} else if (getEquipe().name().equals("green")) {
			couleur = new Color(0, 255, 0, (float) 0.8);
		} else if (getEquipe().name().equals("blue")) {
			couleur = new Color(0, 0, 255, (float) 0.8);
		} else {
			couleur = new Color(230, 229, 0, (float) 0.8);
		}
		return couleur;
	}

}

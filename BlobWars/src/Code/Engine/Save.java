package Code.Engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Code.Graphics.State.Plateau;
import Code.Graphics.State.MenuState;
import Code.Graphics.State.Parameters;
import Code.Engine.Case;

public class Save {
	public String date;
	public int nbJoueurs;
	public int nbEquipe = 0;
	public ArrayList<Joueur> joueurs;
	public int joueurTour = 0;
	public int tailleCarte;
	public Damier zone;
	public int tourEquipe;

	public Save() {
		this.date = "";
		this.nbJoueurs = 0;
		this.joueurs = new ArrayList<Joueur>();
	}

	public void ecrireDate(FileOutputStream save) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			save.write(dateFormat.format(date).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sautDeLigne(FileOutputStream save, int nb) {
		for (int i = 1; i <= nb; i++) {
			try {
				save.write("\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ecrireJoueurs(FileOutputStream save) {
		Joueur temp;
		for (int i = 0; i < 4; i++) {
			if (Parameters.getJoueurs()[i].getScore()==0) {
				for (int j = i+1; j < 4; j++) {
					if (Parameters.getJoueurs()[j].getScore()>0) {
						temp = Parameters.joueurs[i];
						Parameters.joueurs[i] = Parameters.getJoueurs()[j];
						Parameters.joueurs[j] = temp;
						i++;
					}
				}
			}
		}
		
		for (Joueur joueur : Parameters.getJoueurs()) {
			if (!(joueur.getType().equals(Joueur.Type.none)) && joueur.getScore()>0) {
				try {
					save.write(joueur.toString().getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sautDeLigne(save, 1);
			}
		}
	}

	public void ecrireTerrain(FileOutputStream save) {
		for (int i = 0; i < Plateau.getTerrain().getZone().size(); i++) {
			for (int j = 0; j < Plateau.getTerrain().getZone().get(0).size(); j++) {
				if (Plateau.getTerrain().getZone().get(j).get(i).getAccessible()) {
					if (Plateau.getTerrain().getZone().get(j).get(i).getOccuper())
						try {
							save.write(
									String.valueOf(Plateau.getTerrain().getZone().get(j).get(i).getPlayer().getID() + 1)
											.getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else
						try {
							save.write(String.valueOf(0).getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} else
					try {
						save.write("X".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			sautDeLigne(save, 1);
		}
	}

	// Sauvegarde de la partie
	public void save(String name, int id) {
		FileOutputStream save;
		try {
			save = new FileOutputStream("saves/" + name + ".blob");

			ecrireDate(save);
			sautDeLigne(save, 2);

			save.write(String.valueOf(Parameters.nbrPlayer).getBytes());
			sautDeLigne(save, 2);

			ecrireJoueurs(save);

			save.write(String.valueOf(id + 1).getBytes());
			sautDeLigne(save, 2);

			save.write(String.valueOf(Parameters.getTailleMap()).getBytes());
			sautDeLigne(save, 2);

			ecrireTerrain(save);

			save.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		String buffer = new String();
		for (int i = -1; i <= this.tailleCarte; i++) {
			for (int j = -1; j <= this.tailleCarte; j++) {
				if (i == -1 || i == this.tailleCarte)
					buffer += "-";
				else if (j == -1 || j == this.tailleCarte)
					buffer += "|";
				else {
					if (zone.getZone().get(j).get(i).getAccessible()) {
						if (zone.getZone().get(j).get(i).getOccuper())
							buffer += zone.getZone().get(j).get(i).getPlayer().getID();
						else
							buffer += "0";
					} else
						buffer += "X";
				}
			}
			buffer += "\n";
		}
		return buffer;
	}

	public void afficherListeLogs(FileInputStream F, BufferedReader br, String line, ArrayList<String> lines) {
		try {
			while ((line = br.readLine()) != null) {
				if (!(line.equals("")))
					lines.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			F.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int loadDate(ArrayList<String> lines, int index) {
		this.date = lines.get(index);
		return index + 1;
	}

	public int loadNbJoueurs(ArrayList<String> lines, int index) {
		this.nbJoueurs = Integer.valueOf(lines.get(index));
		return index + 1;
	}

	public void loadTypeNiveau(Joueur joueur, String[] carJoueur) {
		if (carJoueur[1].equals("joueur"))
			joueur.setType(Joueur.Type.joueur);
		else {
			joueur.setType(Joueur.Type.ia);

			if (carJoueur[1].equals("tres_facile"))
				joueur.setNiveau(Joueur.Niveau.tres_facile);
			else if (carJoueur[1].equals("facile"))
				joueur.setNiveau(Joueur.Niveau.facile);
			else if (carJoueur[1].equals("moyen"))
				joueur.setNiveau(Joueur.Niveau.moyen);
			else if (carJoueur[1].equals("difficile"))
				joueur.setNiveau(Joueur.Niveau.difficile);
			else if (carJoueur[1].equals("tres_difficile"))
				joueur.setNiveau(Joueur.Niveau.tres_difficile);
			else
				joueur.setNiveau(Joueur.Niveau.overkill);
		}
	}

	public void loadEquipe(Joueur joueur, String[] carJoueur) {
		if (carJoueur[3].equals("red"))
			joueur.setEquipe(Joueur.Equipe.red);
		else if (carJoueur[3].equals("blue"))
			joueur.setEquipe(Joueur.Equipe.blue);
		else if (carJoueur[3].equals("yellow"))
			joueur.setEquipe(Joueur.Equipe.yellow);
		else
			joueur.setEquipe(Joueur.Equipe.green);
	}

	public void loadIcone(Joueur joueur, String[] carJoueur, Image icone) {
		icone.setName(carJoueur[4]);
		joueur.setIcone(icone);
		joueur.setIndexIcone(Integer.valueOf(carJoueur[5]));
	}

	private int loadJoueurs(ArrayList<String> lines, int index) {

		for (int i = 0; i < this.nbJoueurs; i++) {
			String[] carJoueur = lines.get(index).split(" ");

			this.joueurs.add(loadJoueur(lines, carJoueur));
			index++;
		}
		this.nbEquipe = nbEquipe(this.nbJoueurs);
		return index;
	}

	private int nbEquipe(int seuil) {
		boolean equipe1 = false;
		boolean equipe2 = false;
		boolean equipe3 = false;
		boolean equipe4 = false;
		
		int nbEquipe = 0;
		
		for (int i = 0; i < seuil; i++) {
			if (this.joueurs.get(i).getEquipe().ordinal() == 0 && !equipe1) {
				equipe1 = true;
				nbEquipe++;
			} else if (this.joueurs.get(i).getEquipe().ordinal() == 1 && !equipe2) {
				equipe2 = true;
				nbEquipe++;
			} else if (this.joueurs.get(i).getEquipe().ordinal() == 2 && !equipe3) {
				equipe3 = true;
				nbEquipe++;
			} else if (this.joueurs.get(i).getEquipe().ordinal() == 3 && !equipe4) {
				equipe4 = true;
				nbEquipe++;
			}
		}
		return nbEquipe;
	}
	
	private Joueur loadJoueur(ArrayList<String> lines, String[] carJoueur) {

		Joueur joueur = new Joueur(carJoueur[2]);

		Image icone = null;
		try {
			icone = new Image("Assets/Image/Characters/" + carJoueur[4] + ".png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadTypeNiveau(joueur, carJoueur);
		loadEquipe(joueur, carJoueur);
		loadIcone(joueur, carJoueur, icone);

		return joueur;
	}

	public int loadJoueurTour(ArrayList<String> lines, int index) {
		int tourAssigner = Integer.valueOf(lines.get(index));
		for (int i = 0; i < tourAssigner-1; i++) {
			if (this.joueurs.get(i).getEquipe().ordinal() == this.joueurs.get(tourAssigner-1).getEquipe().ordinal()
					&& tourAssigner != i) {
				this.joueurTour++;
			}
		}
		this.tourEquipe = nbEquipe(tourAssigner - 1);
		return index + 1;
	}

	private int nombreEquipeAvant(int tourAssigner) {
		int nbEquipe = 0;

		for (int i = 0; i < this.nbJoueurs; i++) {
			if (this.joueurs.get(tourAssigner).getEquipe().ordinal() != this.joueurs.get(i).getEquipe().ordinal()
					&& i != tourAssigner) {
				nbEquipe++;
			}
		}
		return nbEquipe;
	}

	public int loadTailleCarte(ArrayList<String> lines, int index) {
		this.tailleCarte = Integer.valueOf(lines.get(index));
		return index + 1;
	}

	public int loadGrille(ArrayList<String> lines, int index) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int fenetreLargeur = (int) screenSize.getWidth();
		int fenetreHauteur = (int) screenSize.getHeight();
		int tailleX = fenetreLargeur / (this.tailleCarte + 2);
		int tailleY = fenetreHauteur / (this.tailleCarte + 2);
		int taille = Math.min(tailleX, tailleY);
		Case place;

		for (int i = 0; i < this.tailleCarte; i++) {
			char[] ligne = lines.get(index).toCharArray();
			for (int j = 0; j < this.tailleCarte; j++) {

				boolean accessible;
				if (ligne[j] == 'X') {
					accessible = false;
					place = new Case(accessible,
							fenetreLargeur / 2 - (MenuState.zorque.getWidth("W") * 15) + i * taille,
							taille + j * taille, taille, taille);
				} else {
					accessible = true;

					place = new Case(accessible,
							fenetreLargeur / 2 - (MenuState.zorque.getWidth("W") * 15) + i * taille,
							taille + j * taille, taille, taille);
					if (ligne[j] == '0') {
						Joueur NULL = null;
						place.setPlayer(NULL);
					} else {
						Joueur joueur = joueurs.get(Character.getNumericValue(ligne[j]) - 1);
						place.setPlayer(joueur);
					}
				}
				this.zone.getZone().get(i).set(j, place);
			}
			index++;
		}
		return index;
	}

	// Chargement de la partie
	public void load(FileInputStream F) {

		BufferedReader br = new BufferedReader(new InputStreamReader(F));
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();

		afficherListeLogs(F, br, line, lines);

		int index = 0;

		index = loadDate(lines, index);
		index = loadNbJoueurs(lines, index);
		index = loadJoueurs(lines, index);

		index = loadJoueurTour(lines, index);
		index = loadTailleCarte(lines, index);
		this.zone = new Damier(this.tailleCarte, this.tailleCarte, 1, MenuState.hauteurFenetre,
				MenuState.largeurFenetre, Color.black);
		index = loadGrille(lines, index);
	}
}

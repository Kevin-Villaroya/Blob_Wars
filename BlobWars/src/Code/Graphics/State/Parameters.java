package Code.Graphics.State;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Code.Engine.Joueur;
import Code.Engine.Joueur.Niveau;
import Code.Graphics.Bouton;

public class Parameters extends BasicGameState {

	public static final int ID = 5;
	private static final Image NULL = null;

	private StateBasedGame game;
	private GameContainer container;

	private Bouton retour;
	private Bouton confirmer;
	private Bouton boutonTailleMap;
	private Bouton editor;

	private Bouton boutonJoueur[];

	private Bouton nom[];
	private Bouton type[];
	private Bouton niveau[];
	private Bouton icone[];
	private Bouton equipe[];

	static public int tourJoueur;
	static public int tourEquipe;

	static public Joueur joueurs[];
	static public int nbrEquipe;
	static public int nbrPlayer;
	static public int nbrPlayerInit;
	static private int map;
	static private int tailleMap;
	private int changeName;

	private Image maps[];
	private Bouton boutonsMaps[];

	private Image imagePerso;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		Joueur.listeAnimation = new ArrayList<Animation>();

		setImageCharacters();
		setImageMaps();

		this.game = game;
		this.container = container;
		changeName = -1;

		boutonJoueur = new Bouton[4];
		boutonsMaps = new Bouton[5];

		nom = new Bouton[4];
		type = new Bouton[4];
		niveau = new Bouton[4];
		icone = new Bouton[4];
		equipe = new Bouton[4];

		tailleMap = 10;
		nbrEquipe = 0;

		imagePerso = new Image("Assets/Image/Maps/5.png");

		for (int i = 0; i < 4; i++) {
			boutonJoueur[i] = new Bouton((int) (MenuState.largeurFenetre * 0.05),
					(int) (MenuState.hauteurFenetre * (i * 0.17) + MenuState.hauteurFenetre * 0.05),
					(int) (MenuState.hauteurFenetre * 0.15), (int) (MenuState.largeurFenetre * 0.5), "");

		}

		int sizeMaps = (int) ((MenuState.hauteurFenetre * 0.9));

		for (int i = 0; i < 5; i++) {
			boutonsMaps[i] = new Bouton((int) (MenuState.largeurFenetre - sizeMaps / 5 - (sizeMaps / 5 - 40) / 2),
					(MenuState.hauteurFenetre - sizeMaps) / 2 + i * (sizeMaps / 5 + 10), (sizeMaps / 5 - 40),
					sizeMaps / 5 - 40, "");

		}

		retour = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.2),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Retour");
		confirmer = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Confirmer");
		joueurs = new Joueur[4];

		boutonTailleMap = new Bouton((int) (MenuState.largeurFenetre / 2 + MenuState.largeurFenetre * 0.15),
				(int) (MenuState.hauteurFenetre * (1 * 0.17) + MenuState.hauteurFenetre * 0.05)
						- (int) (MenuState.hauteurFenetre * 0.05),
				(int) (MenuState.hauteurFenetre * 0.1), (int) (MenuState.largeurFenetre * 0.1), "Taille : 10");

		editor = new Bouton((int) (MenuState.largeurFenetre / 2 + MenuState.largeurFenetre * 0.15),
				(int) (MenuState.hauteurFenetre * (3 * 0.17) + MenuState.hauteurFenetre * 0.05) - 90, 180, 200, "");

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				joueurs[i] = new Joueur("Joe");
				joueurs[i].setEquipe(Joueur.Equipe.red);
				break;
			case 1:
				joueurs[i] = new Joueur("William");
				joueurs[i].setEquipe(Joueur.Equipe.blue);
				break;
			case 2:
				joueurs[i] = new Joueur("Jack");
				joueurs[i].setEquipe(Joueur.Equipe.green);
				break;
			case 3:
				joueurs[i] = new Joueur("Averell");
				joueurs[i].setEquipe(Joueur.Equipe.yellow);
				break;
			}

		}

		for (int i = 0; i < 4; i++) {
			initBoutons(i);
			nom[i].setContenu("Roger");
			niveau[i].setContenu("2");
			equipe[i].setContenu("red");
			icone[i].setContenu("linux");
			type[i].setContenu("none");
		}

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(MenuState.background, 0, 0);

		Color boutonColor = new Color(0, 0, 0, (float) 0.9);

		drawButton(retour, boutonColor, g);
		drawButton(confirmer, boutonColor, g);

		for (int i = 0; i < 4; i++) {

			drawButton(boutonJoueur[i], boutonColor, g);
			dessineJoueur(i, joueurs[i].getNom(), joueurs[i].getType(), joueurs[i].getNiveau(), joueurs[i].getEquipe(),
					g);

			MenuState.zorque.drawString(
					nom[i].getX() + nom[i].getLargeur() / 2 - MenuState.zorque.getWidth(nom[i].getContenu()) / 2,
					nom[i].getY() + nom[i].getHauteur() / 2 - MenuState.zorque.getHeight(nom[i].getContenu()) / 2,
					nom[i].getContenu(), Color.black);
			MenuState.zorque.drawString(
					type[i].getX() + type[i].getLargeur() / 2 - MenuState.zorque.getWidth(type[i].getContenu()) / 2,
					type[i].getY() + type[i].getHauteur() / 2 - MenuState.zorque.getHeight(type[i].getContenu()) / 2,
					type[i].getContenu(), Color.black);
			MenuState.zorque.drawString(
					niveau[i].getX() + niveau[i].getLargeur() / 2
							- MenuState.zorque.getWidth(niveau[i].getContenu()) / 2,
					niveau[i].getY() + niveau[i].getHauteur() / 2
							- MenuState.zorque.getHeight(niveau[i].getContenu()) / 2,
					niveau[i].getContenu(), Color.black);
		}

		int sizeMaps = (int) ((MenuState.hauteurFenetre * 0.9));
		for (int i = 0; i < 5; i++) {
			maps[i].draw((float) (MenuState.largeurFenetre - sizeMaps / 5 - 50),
					(float) ((MenuState.hauteurFenetre - sizeMaps) / 2 + i * (sizeMaps / 5 + 10)),
					(float) (sizeMaps / 5 - 40), sizeMaps / 5 - 40);
			if (i == Parameters.map) {
				Rectangle rect = new Rectangle((MenuState.largeurFenetre - sizeMaps / 5 - 50 - 2),
						((MenuState.hauteurFenetre - sizeMaps) / 2 + i * (sizeMaps / 5 + 10) - 2),
						(sizeMaps / 5 - 40) + 4, sizeMaps / 5 - 36);
				g.setColor(new Color(255, 0, 0, (float) 0.4));
				g.draw(rect);
				g.fill(rect);
			}
		}
		drawButton(boutonTailleMap, boutonColor, g);
		imagePerso.draw((int) (MenuState.largeurFenetre / 2 + MenuState.largeurFenetre * 0.15),
				(int) (MenuState.hauteurFenetre * (3 * 0.17) + MenuState.hauteurFenetre * 0.05) - 90, 200, 180);
		if (Parameters.map == 5) {
			Rectangle rect = new Rectangle((int) (MenuState.largeurFenetre / 2 + MenuState.largeurFenetre * 0.15) - 2,
					(int) (MenuState.hauteurFenetre * (3 * 0.17) + MenuState.hauteurFenetre * 0.05) - 92, 204, 184);
			g.setColor(new Color(255, 0, 0, (float) 0.4));
			g.draw(rect);
			g.fill(rect);
		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		for (int i = 0; i < 4; i++) {
			if (type[i].getContenu().equals("none")) {
				nom[i].setContenu("");
				niveau[i].setContenu("");
				equipe[i].setContenu("");
				icone[i].setContenu("");
				type[i].setContenu("");
				joueurs[i].noIcone();
			} else if (type[i].getContenu().equals("joueur")) {
				niveau[i].setContenu("");
			} else {
				nom[i].setContenu(joueurs[i].getNom());
				niveau[i].setContenu(String.valueOf(joueurs[i].getNiveau().ordinal() + 1));
				equipe[i].setContenu(joueurs[i].getEquipe().name());
				if (joueurs[i].getIcone() != NULL) {
					icone[i].setContenu(joueurs[i].getIcone().getName());
				}

			}
		}
	}

	void drawButton(Bouton bouton, Color couleur, Graphics g) {
		Rectangle rect = new Rectangle(bouton.getX(), bouton.getY(), bouton.getLargeur(), bouton.getHauteur());
		g.setColor(couleur);
		g.draw(rect);
		g.fill(rect);

		MenuState.zorque.drawString(
				rect.getX() + rect.getWidth() / 2 - MenuState.zorque.getWidth(bouton.getContenu()) / 2,
				rect.getY() + rect.getHeight() / 2 - MenuState.zorque.getHeight(bouton.getContenu()) / 2,
				bouton.getContenu(), Color.white);
	}

	@Override
	public void keyPressed(int key, char c) {

		String letter;
		letter = Keyboard.getKeyName(key);
		if (changeName != -1) {
			if (key == Keyboard.KEY_RETURN) {
				changeName = -1;
			} else if (key == Keyboard.KEY_BACK) {
				if (joueurs[changeName].getNom().length() > 0) {
					joueurs[changeName].setNom(
							joueurs[changeName].getNom().substring(0, joueurs[changeName].getNom().length() - 1));
					nom[changeName].setContenu(joueurs[changeName].getNom());
				}

			} else if (letter.length() == 1 && joueurs[changeName].getNom().length() < 20) {
				joueurs[changeName].setNom(joueurs[changeName].getNom() + letter);
				nom[changeName].setContenu(joueurs[changeName].getNom());
			}

		}
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (changeName != -1) {
				changeName = -1;
			}

			if (retour.estCliquer(x, y)) {
				nbrPlayerInit = nbrPlayer;
				game.enterState(MenuState.ID);
			} else if (confirmer.estCliquer(x, y)) {

				if (conditionRespecter()) {
					Plateau.initGame = true;
					Plateau.fromSave = false;
					Plateau.hasBeenInit = false;
					
					game.enterState(Plateau.ID);
				}

			} else if (boutonTailleMap.estCliquer(x, y)) {

				setTailleMap();
				boutonTailleMap.setContenu("Taille : " + Integer.toString(tailleMap));
				if (map == 5) {
					Editor.initTerrain(tailleMap);
					game.enterState(Editor.ID);
				}
			} else if (editor.estCliquer(x, y)) {

				map = 5;
				Editor.initTerrain(tailleMap);
				game.enterState(Editor.ID);
			} else {
				for (int i = 0; i < 4; i++) {

					if (joueurs[i].getType().name().equals("none")) {

					} else if (joueurs[i].getType().name().equals("joueur")
							|| joueurs[i].getType().name().equals("ia")) {
						if (icone[i].estCliquer(x, y)) {

							boolean took = true;

							while (took) {
								joueurs[i].setIcone();
								took = false;
								for (int j = 0; j < 4; j++) {
									if (joueurs[j].getIcone() != NULL
											&& joueurs[j].getIcone().getName().equals(joueurs[i].getIcone().getName())
											&& i != j) {
										took = true;
									}
								}
							}

							icone[i].setContenu(joueurs[i].getIcone().getName());

						} else if (this.equipe[i].estCliquer(x, y)) {

							joueurs[i].setEquipe();
							equipe[i].setContenu(joueurs[i].getEquipe().name());

						} else if (this.nom[i].estCliquer(x, y)) {

							changeName = i;

						} else if (joueurs[i].getType().name().equals("ia") && this.niveau[i].estCliquer(x, y)) {

							joueurs[i].setNiveau();
							niveau[i].setContenu(String.valueOf(joueurs[i].getNiveau().ordinal() + 1));
						}
					}

					if (this.boutonJoueur[i].estCliquer(x, y) && joueurs[i].getType().name().equals("none")) {

						joueurs[i].setType();
						type[i].setContenu(joueurs[i].getType().name());

					} else if (this.boutonJoueur[i].estCliquer(x, y) && this.type[i].estCliquer(x, y)) {
						joueurs[i].setType();
						type[i].setContenu(joueurs[i].getType().name());
					}

				}

			}

			for (int i = 0; i < 5; i++) {
				if (boutonsMaps[i].estCliquer(x, y)) {
					Parameters.map = i;
				}
			}
		}

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		try {
			this.init(container, game);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private boolean conditionRespecter() {

		boolean iconeSet = true;
		boolean verifier = true;

		boolean equipe1 = true;
		boolean equipe2 = true;
		boolean equipe3 = true;
		boolean equipe4 = true;

		for (int i = 0; i < 4; i++) {

			if (joueurs[i].getType().name() != "none" && joueurs[i].getEquipe().ordinal() == 0 && equipe1) {
				nbrEquipe++;
				equipe1 = false;
			} else if (joueurs[i].getType().name() != "none" && joueurs[i].getEquipe().ordinal() == 1 && equipe2) {
				nbrEquipe++;
				equipe2 = false;
			} else if (joueurs[i].getType().name() != "none" && joueurs[i].getEquipe().ordinal() == 2 && equipe3) {
				nbrEquipe++;
				equipe3 = false;
			} else if (joueurs[i].getType().name() != "none" && joueurs[i].getEquipe().ordinal() == 3 && equipe4) {
				nbrEquipe++;
				equipe4 = false;
			}

			if (joueurs[i].getType().name() != "none" && joueurs[i].getIcone() == NULL) {
				iconeSet = false;
			}

		}

		if (nbrEquipe <= 1 || !iconeSet) {
			verifier = false;
			nbrEquipe = 0;
		}

		return verifier;
	}

	public void setImageCharacters() {
		Joueur.listeImage = new ArrayList<Image>();

		File repertory = new File("Assets/Image/Characters");
		File[] files = repertory.listFiles();
		int k = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains("png")) {
				String name = files[i].getName().substring(0, files[i].getName().length() - 4);
				try {
					Joueur.listeImage.add(new Image(files[i].getPath()));
					Joueur.listeImage.get(k).setName(name);
					this.setImageAnimations(name);
				} catch (SlickException e) {
					e.printStackTrace();

				}
				k++;
			}
		}
	}

	public void setImageAnimations(String name) {

		File repertory = new File("Assets/Image/Characters/" + name);
		File[] files = repertory.listFiles();

		Animation animation = new Animation();

		for (int i = 0; i < files.length; i++) {

			try {
				String path = files[i].getPath();
				animation.addFrame(new Image(path), 50);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Joueur.listeAnimation.add(animation);
		animation = new Animation();
	}

	public void setImageMaps() {
		maps = new Image[5];

		String repertory = "Assets/Image/Maps/";

		for (int i = 0; i < 5; i++) {
			try {
				maps[i] = new Image(repertory + i + ".png");
				maps[i].setName(Integer.toString(i));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

	}

	private void initBoutons(int nbr) {
		int xmin = (int) (MenuState.largeurFenetre * 0.05);
		int xmax = (int) ((MenuState.largeurFenetre * 0.05) + MenuState.largeurFenetre * 0.5);

		int ymin = (int) (MenuState.hauteurFenetre * (nbr * 0.17) + MenuState.hauteurFenetre * 0.05);
		int ymax = (int) (MenuState.hauteurFenetre * (nbr * 0.17) + MenuState.hauteurFenetre * 0.05
				+ (MenuState.hauteurFenetre * 0.15));

		int tailleX = (int) (MenuState.largeurFenetre * 0.5);
		int tailleY = (int) ymax - ymin;

		int difference = (int) (tailleX + xmin - (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1));

		this.icone[nbr] = new Bouton((int) (xmin + tailleX * 0.01), (int) (ymin + tailleY * 0.05),
				(int) (tailleY - tailleY * 0.1), tailleY, "");
		this.equipe[nbr] = new Bouton((int) (xmax - tailleY - tailleX * 0.01), (int) (ymin + tailleY * 0.05),
				(int) (tailleY - tailleY * 0.1), tailleY, "");
		this.nom[nbr] = new Bouton((int) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1),
				(int) (ymin + tailleY * 0.05), (int) (tailleY / 2),
				(int) (difference - (tailleX * 0.01 + tailleY + tailleY * 0.1)), "");
		this.type[nbr] = new Bouton((int) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1),
				(int) (ymin + tailleY * 0.1 + tailleY / 2), (int) (tailleY / 3),
				(int) (difference - (tailleX * 0.01 + tailleY + tailleY * 0.1 + tailleY * 0.5)), "");
		this.niveau[nbr] = new Bouton(
				(int) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1 + difference
						- (tailleX * 0.01 + tailleY + tailleY * 0.1 + tailleY * 0.5) + tailleY * 0.1),
				(int) (ymin + tailleY * 0.1 + tailleY / 2), (int) (tailleY / 3), (int) (tailleY * 0.5 - tailleY * 0.1),
				"");
	}

	void dessineJoueur(int nbr, String nom, Joueur.Type type, Joueur.Niveau niveau, Joueur.Equipe equipe, Graphics g) {
		g.setColor(Color.white);

		int xmin = (int) (MenuState.largeurFenetre * 0.05);
		int xmax = (int) ((MenuState.largeurFenetre * 0.05) + MenuState.largeurFenetre * 0.5);

		int ymin = (int) (MenuState.hauteurFenetre * (nbr * 0.17) + MenuState.hauteurFenetre * 0.05);
		int ymax = (int) (MenuState.hauteurFenetre * (nbr * 0.17) + MenuState.hauteurFenetre * 0.05
				+ (MenuState.hauteurFenetre * 0.15));

		int tailleX = (int) (MenuState.largeurFenetre * 0.5);
		int tailleY = (int) ymax - ymin;

		int difference = (int) (tailleX + xmin - (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1));

		Rectangle image = new Rectangle((float) (xmin + tailleX * 0.01), (float) (ymin + tailleY * 0.05),
				(float) (tailleY), (float) (tailleY - tailleY * 0.1));

		Rectangle carrequipe = new Rectangle((float) (xmax - tailleY - tailleX * 0.01), (float) (ymin + tailleY * 0.05),
				(float) (tailleY), (float) (tailleY - tailleY * 0.1));

		Rectangle name = new Rectangle((float) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1),
				(float) (ymin + tailleY * 0.05), (float) (difference - (tailleX * 0.01 + tailleY + tailleY * 0.1)),
				(float) (tailleY / 2));

		Rectangle types = new Rectangle((float) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1),
				(float) (ymin + tailleY * 0.1 + tailleY / 2),
				(float) (difference - (tailleX * 0.01 + tailleY + tailleY * 0.1 + tailleY * 0.5)),
				(float) (tailleY / 3));

		Rectangle level = new Rectangle(
				(float) (xmin + tailleX * 0.01 + tailleY + tailleY * 0.1 + difference
						- (tailleX * 0.01 + tailleY + tailleY * 0.1 + tailleY * 0.5) + tailleY * 0.1),
				(float) (ymin + tailleY * 0.1 + tailleY / 2), (float) (tailleY * 0.5 - tailleY * 0.1),
				(float) (tailleY / 3));

		g.draw(image);
		g.fill(image);

		switch (joueurs[nbr].getEquipe().name()) {
		case "red":
			g.setColor(Color.red);
			break;
		case "blue":
			g.setColor(Color.blue);
			break;
		case "green":
			g.setColor(Color.green);
			break;
		case "yellow":
			g.setColor(Color.yellow);
			break;
		}

		g.draw(carrequipe);
		g.fill(carrequipe);

		g.setColor(Color.white);

		if (changeName == nbr) {
			g.setColor(Color.cyan);
		}
		g.draw(name);
		g.fill(name);

		g.setColor(Color.white);

		g.draw(types);
		g.fill(types);

		g.draw(level);
		g.fill(level);

		if (joueurs[nbr].getIcone() != NULL) {
			joueurs[nbr].getIcone().draw((float) (xmin + tailleX * 0.01), (float) (ymin + tailleY * 0.05), tailleY,
					(float) (tailleY - tailleY * 0.1));
		}

		g.setColor(new Color(0, 0, 0, (float) 0.5));

		if (joueurs[nbr].getType().name().equals("none")) {

			g.draw(image);
			g.fill(image);

			g.draw(carrequipe);
			g.fill(carrequipe);

			g.draw(name);
			g.fill(name);

			g.draw(types);
			g.fill(types);

			g.draw(level);
			g.fill(level);

			this.niveau[nbr].setContenu("");
		} else if (joueurs[nbr].getType().name().equals("joueur")) {

			g.draw(level);
			g.fill(level);

		}
	}

	@Override
	public int getID() {

		return ID;
	}

	public static Joueur[] getJoueurs() {
		return joueurs;
	}

	public static void setJoueurs(Joueur joueurs[]) {
		Parameters.joueurs = joueurs;
	}

	public static int getMap() {
		return map;
	}

	public static void setMap(int map) {
		Parameters.map = map;
	}

	public static int getTailleMap() {
		return tailleMap;
	}

	public static void setTailleMap() {
		tailleMap++;
		if (tailleMap > 6 && tailleMap <= 15) {
			if (tailleMap % 2 == 1) {
				Parameters.tailleMap = tailleMap + 1;
			}

		} else {
			tailleMap = 6;
		}

	}

	public static void setTailleMap(int taille) {
		tailleMap = taille;
	}

}
package Code.Graphics.State;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Code.Engine.Case;
import Code.Engine.Damier;
import Code.Engine.Deplacement;
import Code.Engine.GestionTour;
import Code.Engine.Joueur;
import Code.Engine.Save;
import Code.Engine.Ia.IA;
import Code.Graphics.Slice;

public class Plateau extends BasicGameState {

	public static final int ID = 1;
	private GameContainer container;
	private StateBasedGame game;

	static public Damier terrain;
	static public boolean hasBeenInit;

	static public boolean fromSave = false;;

	static public boolean initGame;

	private boolean anim;
	private ArrayList<Object> aAnimer;

	private GestionTour gestionTour;
	private Deplacement deplacement;
	public static Save sauvegarde;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		this.container = container;
		this.game = game;

		anim = false;
		aAnimer = new ArrayList<Object>();

		hasBeenInit = false;

		this.gestionTour = new GestionTour();
		deplacement = new Deplacement();
		sauvegarde = new Save();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(MenuState.background, 0, 0);

		if (anim) {

			float size = (float) (terrain.getZone().get(0).get(0).getLargeur())
					/ this.gestionTour.getTour().getAnimation().getCurrentFrame().getWidth();

			int x = (int) aAnimer.get(1);
			int y = (int) aAnimer.get(2);

			this.gestionTour.getTour().getAnimation().getCurrentFrame().draw(x, y, size);

			this.gestionTour.getTour().getAnimation()
					.setCurrentFrame(this.gestionTour.getTour().getAnimation().getFrame() + 1);

			if (this.gestionTour.getTour().getAnimation()
					.getFrame() == this.gestionTour.getTour().getAnimation().getFrameCount() - 1) {
				anim = false;
				aAnimer = new ArrayList<Object>();
				this.gestionTour.getTour().getAnimation().setCurrentFrame(0);
				this.gestionTour.nextTour();

				verificationFinDeJeu();
			}
			this.deplacement.resetDeplacement();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

		for (int i = 0; i < terrain.getZone().size(); i++) {
			for (int j = 0; j < terrain.getZone().get(0).size(); j++) {

				int largeur = terrain.getZone().get(i).get(j).getLargeur();
				int hauteur = terrain.getZone().get(i).get(j).getHauteur();

				int x = terrain.getZone().get(i).get(j).getX();
				int y = terrain.getZone().get(i).get(j).getY();

				Rectangle rect = new Rectangle(x, y, largeur, hauteur);

				g.setColor(terrain.getCouleur());

				g.draw(rect);

				if (terrain.getZone().get(i).get(j).getAccessible()) {
					g.setColor(new Color(255, 255, 255, (float) 0.5));
				} else {
					g.setColor(Color.black);
				}

				Rectangle border = new Rectangle(rect.getX() + 1, rect.getY() + 1, rect.getWidth() - 2,
						rect.getHeight() - 2);

				if (terrain.getZone().get(i).get(j).getOccuper()) {

					g.setColor(terrain.getZone().get(i).get(j).getPlayer().couleurJoueur());

				}

				g.draw(border);
				g.fill(border);

				if (terrain.getZone().get(i).get(j).getOccuper()) {

					terrain.getZone().get(i).get(j).getPlayer().getIcone().draw(x, y, largeur, hauteur);

				}

				DrawScore(g);
				DrawTour(g);
				DrawScoreEquipe(g);

				if (!anim) {
					DrawCaseLointaine(g);
					DrawCaseAdjacente(g);
				}
			}
		}

	}

	private void verificationFinDeJeu() {
		if (this.deplacement.isStuck(this.gestionTour.getTour())) {

			if (this.deplacement.endGameAllStucks()) {
				this.endOfGame();
			}
			gestionTour.nextTour();
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		if (hasBeenInit == false && !fromSave) {
			initGame();
		}

		if (Parameters.getMap() != 0) {
			initTerrain();
			Parameters.setMap(0);
		}

		if (hasBeenInit && this.gestionTour.getTour().getType().name().equals("ia")) {
			callIA();
		}

		if (hasBeenInit == false) {
			if (!fromSave) {
				initPlayers();
			} else {
				this.gestionTour = new GestionTour();
				initPlayersFromSave();
			}
			hasBeenInit = true;

			for (int i = 0; i < Parameters.nbrPlayer; i++) {
				this.gestionTour.initGestionJoueur();
			}

			this.gestionTour.tourJoueur();
			this.deplacement.isStuck(this.gestionTour.getTour());
			this.deplacement.resetDeplacement();
		}

		if (Parameters.nbrEquipe == 1 || Parameters.nbrPlayer == 1) {
			if (Parameters.nbrPlayerInit == 2) {
				End.vainqueur = "Le gagnant est " + this.gestionTour.getTour().getNom();
			} else {
				End.vainqueur = "L'\u00e9quipe " + this.gestionTour.getTour().getEquipe().name()
						+ " gagne cette partie";
			}
			End.equipeVainqueur = this.gestionTour.getTour().couleurJoueur();
			this.game.enterState(End.ID);
		}

	}

	private void callIA() {
		if (this.gestionTour.getTour().getType().name().equals("ia") && !anim) {
			
			// programme, boucle dont il faut mesurer le temps
			IA.getInstance().calculProchainCoup(gestionTour.getTour().getID(), new GestionTour(this.gestionTour),
					gestionTour.getTour().getNiveau().ordinal() + 1);
			Case aAller = IA.getInstance().caseAJouer;
			int typeMouvement = IA.getInstance().typeDeMouvement;// 0 pour
																	// proche, 1
																	// pour loin

			this.deplacement.caseCourante = IA.getInstance().caseCourante;
			this.deplacement.deplacer(aAller, typeMouvement, this.deplacement, this.gestionTour);

			animerDeplacement(aAller, typeMouvement);
			this.deplacement.resetDeplacement();
		}
	}

	public void endOfGame() {
		int s1 = 0;
		int s2 = 0;
		int s3 = 0;
		int s4 = 0;

		for (int i = 0; i < this.gestionTour.getOrdreJoueur().size(); i++) {
			switch (Parameters.getJoueurs()[i].getEquipe().ordinal()) {
			case 0:
				s1 += Parameters.getJoueurs()[i].getScore();
				break;
			case 1:
				s2 += Parameters.getJoueurs()[i].getScore();
				break;
			case 2:
				s3 += Parameters.getJoueurs()[i].getScore();
				break;
			case 3:
				s4 += Parameters.getJoueurs()[i].getScore();
				break;
			}
		}

		if (s1 > s2 && s1 > s3 && s1 > s4) {
			End.vainqueur = "L'\u00e9quipe rouge gagne cette partie avec " + s1 + " points";
			End.equipeVainqueur = Color.red;
			System.out.println("red");
		} else if (s2 > s1 && s2 > s3 && s2 > s4) {
			End.vainqueur = "L'\u00e9quipe bleue gagne cette partie avec " + s2 + " points";
			End.equipeVainqueur = Color.blue;
		} else if (s3 > s1 && s3 > s2 && s3 > s4) {
			End.vainqueur = "L'\u00e9quipe jaune gagne cette partie avec " + s3 + " points";
			End.equipeVainqueur = Color.yellow;
		} else if (s4 > s1 && s4 > s3 && s4 > s2) {
			End.vainqueur = "L'\u00e9quipe verte gagne cette partie avec " + s4 + " points";
			End.equipeVainqueur = Color.green;
		} else {
			End.vainqueur = "Pat";
			End.equipeVainqueur = Color.white;
		}
		this.game.enterState(End.ID);
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE) {
			SaveState.IDSave = this.gestionTour.getTour().getID();
			this.game.enterState(Option.ID);
			Option.returnState = this.getID();
		}
		if (key == Keyboard.KEY_F1) {
			this.gestionTour.nextTour();
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		try {
			if (Plateau.initGame) {
				this.init(container, game);
				Plateau.initGame = false;
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {

			for (int i = 0; i < this.deplacement.getCaseAdjacente().size(); i++) {
				if (this.deplacement.getCaseAdjacente().get(i).estCliquer(x, y)) {
					this.deplacement.deplacer(this.deplacement.getCaseAdjacente().get(i), 0, this.deplacement,
							this.gestionTour);
					animerDeplacement(this.deplacement.getCaseAdjacente().get(i), 0);
				}
			}

			for (int i = 0; i < this.deplacement.getCaseLointaine().size(); i++) {
				if (this.deplacement.getCaseLointaine().get(i).estCliquer(x, y)) {
					this.deplacement.deplacer(this.deplacement.getCaseLointaine().get(i), 1, this.deplacement,
							this.gestionTour);
					animerDeplacement(this.deplacement.getCaseLointaine().get(i), 1);
				}
			}

			for (int i = 0; i < terrain.getZone().size(); i++) {
				for (int j = 0; j < terrain.getZone().size(); j++) {
					if (terrain.getZone().get(i).get(j).getPlayer() == this.gestionTour.getTour()
							&& terrain.getZone().get(i).get(j).estCliquer(x, y)) {
						this.deplacement.caseCourante = terrain.getZone().get(i).get(j);
						this.deplacement.resetDeplacement();
						this.deplacement.AjouterCaseAccessible(terrain.getZone().get(i).get(j), i, j);
					}
				}
			}

		}

	}

	void animerDeplacement(Case nouvelleCase, int type) {
		aAnimer.add(gestionTour.getTour());
		if (type == 0) {
			aAnimer.add(nouvelleCase.getX());
			aAnimer.add(nouvelleCase.getY());
		} else {
			aAnimer.add(nouvelleCase.getX());
			aAnimer.add(nouvelleCase.getY());
		}
		anim = true;
	}

	void drawPlayerMovement(int x, int y, Joueur joueur) {
		joueur.getIcone().draw(x, y, 1);
	}

	void animPlayer(Joueur joueur, int x, int y, Graphics g) {
		g.drawAnimation(joueur.getAnimation(), 10, 10);
	}

	static public boolean outOfGame(int x, int y) {
		if (x >= 0 && x < terrain.getZone().size()) {
			if (y >= 0 && y < terrain.getZone().get(0).size()) {
				return false;
			}
		}
		return true;
	}

	void DrawCaseAdjacente(Graphics g) {
		for (int i = 0; i < this.deplacement.getCaseAdjacente().size(); i++) {
			Rectangle rect = new Rectangle(this.deplacement.getCaseAdjacente().get(i).getX() + 2,
					this.deplacement.getCaseAdjacente().get(i).getY() + 2,
					this.deplacement.getCaseAdjacente().get(i).getLargeur() - 4,
					this.deplacement.getCaseAdjacente().get(i).getHauteur() - 4);
			g.setColor(Color.darkGray);
			g.draw(rect);
			g.fill(rect);
		}
	}

	void DrawCaseLointaine(Graphics g) {

		for (int i = 0; i < this.deplacement.getCaseLointaine().size(); i++) {

			Rectangle rect = new Rectangle(this.deplacement.getCaseLointaine().get(i).getX() + 2,
					this.deplacement.getCaseLointaine().get(i).getY() + 2,
					this.deplacement.getCaseLointaine().get(i).getLargeur() - 4,
					this.deplacement.getCaseLointaine().get(i).getHauteur() - 4);

			g.setColor(Color.gray);
			g.draw(rect);
			g.fill(rect);
		}
	}

	void DrawScoreEquipe(Graphics g) {
		int x = terrain.getZone().get(0).get(0).getX() / 2 - 10 * 10;
		int y = MenuState.hauteurFenetre / 3 + 30 * 5;

		Rectangle rect = new Rectangle(x, y, 200, 200);
		Slice[] scores = new Slice[Parameters.nbrPlayer];
		int k = 0;

		for (int i = 0; i < 4; i++) {

			if (!Parameters.getJoueurs()[i].getType().name().equals("none")
					&& Parameters.getJoueurs()[i].getScore() != 0) {

				scores[k] = new Slice(Parameters.getJoueurs()[i].getScore(),
						Parameters.getJoueurs()[i].couleurJoueur());
				if (k < Parameters.nbrPlayer - 1) {
					k++;
				}
			}
		}

		drawPie(g, rect, scores);

	}

	void drawPie(Graphics g, Rectangle area, Slice[] slices) {
		double total = 0.0D;
		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}

		double curValue = 0.0D;
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total) + startAngle;

			g.setColor(slices[i].color);
			g.fillArc((int) area.getX(), (int) area.getY(), (int) area.getWidth(), (int) area.getHeight(), startAngle,
					arcAngle);
			curValue += slices[i].value;
		}
	}

	void DrawTour(Graphics g) {

		int x = terrain.getZone().get(0).get(0).getX() / 2;
		int y = MenuState.hauteurFenetre / 4;

		String name = this.gestionTour.getTour().getType().name().equals("ia")
				? this.gestionTour.getTour().getNom() + " (IA " + this.gestionTour.getTour().getNiveau() + ")"
				: this.gestionTour.getTour().getNom();

		Rectangle rect;

		if ((x * 2) < (MenuState.zorque.getWidth("Tour du joueur:") * 2 + x
				- MenuState.zorque.getWidth("Tour du joueur:"))) {
			rect = new Rectangle(x - MenuState.zorque.getWidth("Tour du joueur:") / 2,
					y - MenuState.zorque.getHeight("T") * 2, MenuState.zorque.getWidth("Tour du joueur:"),
					MenuState.zorque.getHeight("T") * 4);
		} else {
			rect = new Rectangle(x - MenuState.zorque.getWidth("Tour du joueur:"),
					y - MenuState.zorque.getHeight("T") * 2, MenuState.zorque.getWidth("Tour du joueur:") * 2,
					MenuState.zorque.getHeight("T") * 4);
		}
		g.setColor(new Color(0, 0, 0, (float) 0.5));

		g.draw(rect);
		g.fill(rect);

		Color couleur = Color.red;

		couleur = this.gestionTour.getTour().couleurJoueur();

		MenuState.zorque.drawString(x - MenuState.zorque.getWidth("Tour du joueur:") / 2,
				y - MenuState.zorque.getHeight("T"), "Tour du joueur:", Color.white);
		MenuState.zorque.drawString(x - (MenuState.zorque.getWidth(name)) / 2, y, name, couleur);
	}

	void DrawScore(Graphics g) {

		int k = 0;
		int x = terrain.getZone().get(0).get(0).getX() / 2;
		int y = MenuState.hauteurFenetre / 3 + 15;
		int scaleMax = 0;

		for (Joueur j : Parameters.getJoueurs()) {
			if (scaleMax < MenuState.zorque.getWidth(j.getNom())) {
				scaleMax = MenuState.zorque.getWidth(j.getNom());
			}
		}
		scaleMax += MenuState.zorque.getWidth(": 99") + 10;

		Rectangle rect = new Rectangle(x - scaleMax / 2 - 10, y - 10, scaleMax, 30 * 4 + 10);
		g.setColor(Color.lightGray);
		g.draw(rect);
		g.fill(rect);

		for (int i = 0; i < 4; i++) {

			Joueur joueur = Parameters.getJoueurs()[i];
			String score = joueur.getNom() + ": " + joueur.getScore();

			if (!joueur.getType().name().equals("none") && joueur.getScore() != 0) {

				MenuState.zorque.drawString(x - MenuState.zorque.getWidth(score) / 2, y + k * 30, score,
						joueur.couleurJoueur());
				k++;
			}
		}
	}

	private void initPlayersFromSave() {

		int s1 = 0;
		int s2 = 0;
		int s3 = 0;
		int s4 = 0;

		for (int i = 0; i < this.terrain.getZone().size(); i++) {
			for (int j = 0; j < this.terrain.getZone().size(); j++) {
				if (this.terrain.getZone().get(i).get(j).getOccuper()) {
					Joueur joueur = this.terrain.getZone().get(i).get(j).getPlayer();
					if (joueur.getID() == 0) {
						s1++;
					} else if (joueur.getID() == 1) {
						s2++;
					} else if (joueur.getID() == 2) {
						s3++;
					} else if (joueur.getID() == 3) {
						s4++;
					}
				}
			}
		}

		Parameters.joueurs[0].setScore(s1);
		Parameters.joueurs[1].setScore(s2);
		Parameters.joueurs[2].setScore(s3);
		Parameters.joueurs[3].setScore(s4);

		for (int i = 0; i < 4; i++) {
			this.gestionTour.getOrdreJoueur().add(new ArrayList<Joueur>());
		}

		for (int i = 0; i < 4; i++) {
			if (!Parameters.getJoueurs()[i].getType().name().equals("none")) {
				this.gestionTour.getOrdreJoueur().get(Parameters.getJoueurs()[i].getEquipe().ordinal())
						.add(Parameters.getJoueurs()[i]);
			}
		}
	}

	void initPlayers() {
		int size = terrain.getZone().size();
		int nbPlayer = 0;
		Joueur p1 = null;
		Joueur p2 = null;

		for (int i = 0; i < 4; i++) {

			if (!Parameters.getJoueurs()[i].getType().name().equals("none")) {
				if (nbPlayer == 0) {
					terrain.getZone().get(0).get(0).setPlayer(Parameters.getJoueurs()[i]);
					p1 = Parameters.getJoueurs()[i];
				} else if (nbPlayer == 1) {
					terrain.getZone().get(size - 1).get(size - 1).setPlayer(Parameters.getJoueurs()[i]);
					p2 = Parameters.getJoueurs()[i];
				} else if (nbPlayer == 2) {
					terrain.getZone().get(0).get(size - 1).setPlayer(Parameters.getJoueurs()[i]);
				} else if (nbPlayer == 3) {
					terrain.getZone().get(size - 1).get(0).setPlayer(Parameters.getJoueurs()[i]);
				}
				nbPlayer++;
				Parameters.getJoueurs()[i].setScore(1);
				this.deplacement.isStuck(Parameters.getJoueurs()[i]);
			}
		}

		if (nbPlayer == 2) {
			p1.setScore(2);
			p2.setScore(2);
			for (int i = 0; i < 4; i++) {
				if (i == 0) {
					terrain.getZone().get(0).get(0).setPlayer(p1);
				} else if (i == 1) {
					terrain.getZone().get(size - 1).get(size - 1).setPlayer(p2);
				} else if (i == 2) {
					terrain.getZone().get(0).get(size - 1).setPlayer(p1);
				} else if (i == 3) {
					terrain.getZone().get(size - 1).get(0).setPlayer(p2);
				}
			}

		}

		Parameters.nbrPlayer = nbPlayer;
		for (int i = 0; i < 4; i++) {
			this.gestionTour.getOrdreJoueur().add(new ArrayList<Joueur>());
		}

		for (int i = 0; i < 4; i++) {
			if (!Parameters.getJoueurs()[i].getType().name().equals("none")) {
				this.gestionTour.getOrdreJoueur().get(Parameters.getJoueurs()[i].getEquipe().ordinal())
						.add(Parameters.getJoueurs()[i]);
			}

		}
	}

	void initTerrain() {

		int formation = Parameters.getMap();
		switch (formation) {
		case 1:
			Damier.terrain1(terrain);
			break;
		case 2:
			Damier.terrain2(terrain);
			break;
		case 3:
			Damier.terrain3(terrain);
			break;
		case 4:
			Damier.terrain4(terrain);
			break;
		case 5:
			terrain = Editor.terrainEdit;
			break;
		case 6:
			terrain = BridgeSave.terrain;
		}

	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public GameContainer getContainer() {
		return container;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.ID;
	}

	static public Damier getTerrain() {
		return terrain;
	}

	public void initGame() {
		terrain = new Damier(Parameters.getTailleMap(), Parameters.getTailleMap(), 1, MenuState.hauteurFenetre,
				MenuState.largeurFenetre, Color.black);
	}
}

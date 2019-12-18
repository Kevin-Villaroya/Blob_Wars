package Code.Graphics.State;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Code.Engine.Damier;
import Code.Engine.GestionTour;
import Code.Engine.Save;
import Code.Graphics.Bouton;
import Code.Graphics.Slide;

public class BridgeSave extends BasicGameState {

	public static final int ID = 8;

	private StateBasedGame game;
	private GameContainer container;

	private Bouton nouveau;

	private Bouton save1b;
	private Bouton save2b;
	private Bouton save3b;
	private Bouton save4b;
	private Bouton save5b;

	private Bouton retour;

	private Save save1;
	private Save save2;
	private Save save3;
	private Save save4;
	private Save save5;
	public static GestionTour gestionTour;
	
	public static Damier terrain;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		this.container = container;

		gestionTour = new GestionTour();
		
		this.nouveau = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.15),
				(int) (MenuState.hauteurFenetre * 0.1), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.3), "nouveau");
		this.save1b = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.2), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "");
		this.save2b = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.3), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "");
		this.save3b = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.4), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "");
		this.save4b = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.5), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "");
		this.save5b = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.6), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "");

		this.retour = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.8), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "Retour");

		loadAllSaves();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(MenuState.background, 0, 0);
		Color boutonColor = new Color(0, 0, 0, (float) 0.9);
		Color boutonColorDisabled = new Color(0, 0, 0, (float) 0.5);

		drawButton(this.nouveau, boutonColor, boutonColorDisabled, g);

		drawButton(this.retour, boutonColor, boutonColorDisabled, g);

		drawButton(this.save1b, boutonColor, boutonColorDisabled, g);
		drawButton(this.save2b, boutonColor, boutonColorDisabled, g);
		drawButton(this.save3b, boutonColor, boutonColorDisabled, g);
		drawButton(this.save4b, boutonColor, boutonColorDisabled, g);
		drawButton(this.save5b, boutonColor, boutonColorDisabled, g);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		loadAllSaves();
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (retour.estCliquer(x, y)) {
				this.game.enterState(MenuState.ID);
			} else if (this.nouveau.estCliquer(x, y)) {
				this.game.enterState(Parameters.ID);
			} else if (this.save1b.estCliquer(x, y) && !this.save1b.getContenu().equals("")) {

				load(save1);
				this.game.enterState(Plateau.ID);

			} else if (this.save2b.estCliquer(x, y) && !this.save2b.getContenu().equals("")) {

				load(save2);
				this.game.enterState(Plateau.ID);
				
			} else if (this.save3b.estCliquer(x, y) && !this.save3b.getContenu().equals("")) {

				load(save3);
				this.game.enterState(Plateau.ID);
				
			} else if (this.save4b.estCliquer(x, y) && !this.save4b.getContenu().equals("")) {

				load(save4);
				this.game.enterState(Plateau.ID);
				
			} else if (this.save5b.estCliquer(x, y) && !this.save5b.getContenu().equals("")) {

				load(save5);
				this.game.enterState(Plateau.ID);
				
			}
		}
	}

	private void load(Save save) {
		Plateau.fromSave = true;
		Plateau.hasBeenInit = false;
		Parameters.setMap(6);
		Parameters.setTailleMap(save.tailleCarte);

		Parameters.nbrPlayer = save.nbJoueurs;
		Parameters.nbrPlayerInit = save.nbJoueurs;
		Parameters.nbrEquipe = save.nbEquipe;
		Parameters.tourJoueur = save.joueurTour;
		Parameters.tourEquipe = save.tourEquipe;
		terrain = save.zone;
		
		for (int i = 0; i < save.nbJoueurs; i++) {
			Parameters.joueurs[i] = save.joueurs.get(i);
		}
	}

	private void loadAllSaves() {
		File repertory = new File("saves");
		File[] files = repertory.listFiles();

		for (int i = 0; i < files.length; i++) {
			switch (i) {
			case 0:
				this.save1 = new Save();
				try {
					this.save1.load(new FileInputStream(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.save1b.setContenu(this.save1.date);
				break;
			case 1:
				this.save2 = new Save();
				try {
					this.save2.load(new FileInputStream(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.save2b.setContenu(this.save2.date);
				break;
			case 2:
				this.save3 = new Save();
				try {
					this.save3.load(new FileInputStream(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.save3b.setContenu(this.save3.date);
				break;
			case 3:
				this.save4 = new Save();
				try {
					this.save4.load(new FileInputStream(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.save4b.setContenu(this.save4.date);
				break;
			case 4:
				this.save5 = new Save();
				try {
					this.save5.load(new FileInputStream(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.save5b.setContenu(this.save5.date);
				break;
			}
		}

	}

	void drawButton(Bouton bouton, Color couleur, Color couleur2, Graphics g) {
		Rectangle rect = new Rectangle(bouton.getX(), bouton.getY(), bouton.getLargeur(), bouton.getHauteur());
		if (!bouton.getContenu().equals("")) {
			g.setColor(couleur);
		} else {
			g.setColor(couleur2);
		}
		g.draw(rect);
		g.fill(rect);

		MenuState.zorque.drawString(
				rect.getX() + rect.getWidth() / 2 - MenuState.zorque.getWidth(bouton.getContenu()) / 2,
				rect.getY() + rect.getHeight() / 2 - MenuState.zorque.getHeight(bouton.getContenu()) / 2,
				bouton.getContenu(), Color.white);
	}

	@Override
	public int getID() {

		return ID;
	}

}
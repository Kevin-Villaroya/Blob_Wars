package Code.Graphics.State;

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

import Code.Graphics.Bouton;
import Code.Graphics.Slide;

public class Option extends BasicGameState {

	public static final int ID = 2;
	private float volume;
	private Image background;
	static public int returnState;
	private StateBasedGame game;
	private GameContainer container;

	private Bouton retour;
	private Bouton quitter;
	private Bouton sauvegarde;

	private Slide volumeBackground;
	private Slide volumeSounds;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		this.container = container;

		background = new Image("Assets/Image/Backgrounds/Table.jpg");

		
		sauvegarde = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.4),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Sauvegarde");
		retour = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.3),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Retour");
		quitter = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.2),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Quitter");

		volumeBackground = new Slide(0, 1, (int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.5),
				(int) (MenuState.largeurFenetre * 0.2));

		volumeSounds = new Slide(0, 1, (int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.7),
				(int) (MenuState.largeurFenetre * 0.2));

		volumeBackground.setCurseur(MenuState.volumeAmbiance);
		volumeSounds.setCurseur(MenuState.volumeSounds);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		g.setColor(new Color(0, 0, 0, (float) 0.9));
		g.drawImage(background, 0, 0);

		Rectangle rect = new Rectangle(retour.getX(), retour.getY(), retour.getLargeur(), retour.getHauteur());
		g.draw(rect);
		g.fill(rect);
		
		rect = new Rectangle(sauvegarde.getX(), sauvegarde.getY(), sauvegarde.getLargeur(), sauvegarde.getHauteur());
		g.draw(rect);
		g.fill(rect);

		rect = new Rectangle(quitter.getX(), quitter.getY(), quitter.getLargeur(), quitter.getHauteur());
		g.draw(rect);
		g.fill(rect);

		rect = new Rectangle(volumeBackground.getX(), volumeBackground.getY(), volumeBackground.getTaille(), 10);
		Circle circle = new Circle(
				volumeBackground.getX() + volumeBackground.getCurseur() * volumeBackground.getTaille(),
				volumeBackground.getY() + 5, volumeBackground.getTailleY());
		g.setColor(new Color(0, 0, 0, (float) 0.7));
		g.draw(rect);
		g.fill(rect);
		g.setColor(Color.black);
		g.draw(circle);
		g.fill(circle);

		g.setColor(new Color(0, 0, 0, (float) 0.9));

		rect = new Rectangle(volumeSounds.getX(), volumeSounds.getY(), volumeSounds.getTaille(), 10);
		circle = new Circle(volumeSounds.getX() + volumeSounds.getCurseur() * volumeSounds.getTaille(),
				volumeSounds.getY() + 5, volumeSounds.getTailleY());
		g.setColor(new Color(0, 0, 0, (float) 0.7));
		g.draw(rect);
		g.fill(rect);
		g.setColor(Color.black);
		g.draw(circle);
		g.fill(circle);

		g.setColor(Color.white);

		MenuState.zorque.drawString(retour.getX() + retour.getLargeur() / 2 - MenuState.zorque.getWidth("Retour") / 2,
				retour.getY() + retour.getHauteur() / 2 - MenuState.zorque.getHeight("Retour") / 2,
				retour.getContenu());

		MenuState.zorque.drawString(sauvegarde.getX() + sauvegarde.getLargeur() / 2 - MenuState.zorque.getWidth("Sauvegarde") / 2,
				sauvegarde.getY() + sauvegarde.getHauteur() / 2 - MenuState.zorque.getHeight("Sauvegarde") / 2,
				sauvegarde.getContenu());
		
		MenuState.zorque.drawString(
				quitter.getX() + quitter.getLargeur() / 2 - MenuState.zorque.getWidth("quitter") / 2,
				quitter.getY() + quitter.getHauteur() / 2 - MenuState.zorque.getHeight("quitter") / 2,
				quitter.getContenu());

		MenuState.zorque.drawString(
				volumeSounds.getX() + volumeSounds.getTaille() / 2 - MenuState.zorque.getWidth("Volume bruitages") / 2,
				volumeSounds.getY() - MenuState.zorque.getHeight("Volume") - 20, "Volume bruitages", Color.black);

		MenuState.zorque.drawString(
				volumeBackground.getX() + volumeBackground.getTaille() / 2
						- MenuState.zorque.getWidth("Volume musique") / 2,
				volumeBackground.getY() - MenuState.zorque.getHeight("Volume") - 20, "Volume musique", Color.black);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		MenuState.volumeAmbiance = volumeBackground.getCurseur();
		MenuState.volumeSounds = volumeSounds.getCurseur();

		MenuState.ambiance.setVolume(MenuState.volumeAmbiance);
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (retour.estCliquer(x, y)) {

				this.game.enterState(returnState);

			} else if (volumeBackground.estCliquer(x, y)) {
				volumeBackground.deplaceCurseur(x, y);

			} else if (volumeSounds.estCliquer(x, y)) {
				volumeSounds.deplaceCurseur(x, y);

			} else if (quitter.estCliquer(x, y)) {
				
				this.game.enterState(MenuState.ID);
			}else if(sauvegarde.estCliquer(x, y)) {
				this.game.enterState(SaveState.ID);
			}
		}
	}

	@Override
	public int getID() {

		return ID;
	}

}

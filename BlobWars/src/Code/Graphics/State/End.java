package Code.Graphics.State;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Code.Graphics.Bouton;

public class End extends BasicGameState {

	public static final int ID = 7;

	private StateBasedGame game;
	private GameContainer container;

	public static String vainqueur;
	public static Color equipeVainqueur;
	private Bouton menu;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		this.game = game;
		this.container = container;

		equipeVainqueur = Color.white;
		menu = new Bouton((int) (MenuState.largeurFenetre - (MenuState.largeurFenetre * 0.2)), 10,
				(int) (MenuState.hauteurFenetre * 0.1), (int) (MenuState.largeurFenetre * 0.2), "Menu");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		g.drawImage(MenuState.background, 0, 0);

		for (int i = 0; i < Plateau.getTerrain().getZone().size(); i++) {
			for (int j = 0; j < Plateau.getTerrain().getZone().get(0).size(); j++) {

				int largeur = Plateau.getTerrain().getZone().get(i).get(j).getLargeur();
				int hauteur = Plateau.getTerrain().getZone().get(i).get(j).getHauteur();

				int x = MenuState.largeurFenetre/2 - (largeur * Plateau.getTerrain().getZone().size())/2 + i*largeur;
				int y = Plateau.getTerrain().getZone().get(i).get(j).getY();

				Rectangle rect = new Rectangle(x, y, largeur, hauteur);

				g.setColor(Plateau.getTerrain().getCouleur());

				g.draw(rect);

				if (Plateau.getTerrain().getZone().get(i).get(j).getAccessible()) {
					g.setColor(new Color(255, 255, 255, (float) 0.5));
				} else {
					g.setColor(Color.black);
				}

				Rectangle border = new Rectangle(rect.getX() + 1, rect.getY() + 1, rect.getWidth() - 2,
						rect.getHeight() - 2);

				if (Plateau.getTerrain().getZone().get(i).get(j).getOccuper()) {

					g.setColor(Plateau.getTerrain().getZone().get(i).get(j).getPlayer().couleurJoueur());

				}

				g.draw(border);
				g.fill(border);

				if (Plateau.getTerrain().getZone().get(i).get(j).getOccuper()) {

					Plateau.getTerrain().getZone().get(i).get(j).getPlayer().getIcone().draw(x, y, largeur, hauteur);

				}
			}
		}

		Color boutonColor = new Color(0, 0, 0, (float) 0.9);

		MenuState.zorque.drawString(MenuState.largeurFenetre / 2 - MenuState.zorque.getWidth(vainqueur) / 2, 10,
				vainqueur, equipeVainqueur);
		drawButton(this.menu, boutonColor, g);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (this.menu.estCliquer(x, y)) {
				this.game.enterState(MenuState.ID);
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
	public int getID() {
		return ID;
	}

}

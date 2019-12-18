package Code.Graphics.State;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Code.Engine.Damier;
import Code.Graphics.Bouton;

public class Editor extends BasicGameState {

	public static final int ID = 6;
	GameContainer container;
	StateBasedGame game;

	private Bouton retour;
	private static ArrayList<ArrayList<Bouton>> terrain;
	public static Damier terrainEdit;

	ArrayList<String> texte;
	private Image cross;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		this.game = game;
		this.container = container;

		this.cross = new Image("Assets/Image/Divers/croix.png");

		retour = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.1), (int) (MenuState.hauteurFenetre * 0.07),
				(int) (MenuState.largeurFenetre * 0.2), "Confirmer");

		terrainEdit = new Damier(Parameters.getTailleMap(), (Parameters.getTailleMap()), 1, MenuState.hauteurFenetre, MenuState.largeurFenetre,
				Color.black);
		terrain = new ArrayList<ArrayList<Bouton>>();

		texte = new ArrayList<String>();

		texte.add("Personnaliser la carte:");
		texte.add("");
		texte.add(
				"Vous visualiser, un quart de la carte, la partie en haut à droite, qui seras ensuite reporter sur la map pour que ca soit symétrique");
		texte = transformText(texte);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		Color boutonColor = new Color(0, 0, 0, (float) 0.9);

		g.drawImage(MenuState.background, 0, 0);

		Rectangle rectangle = new Rectangle((float) (10),
				(float) (MenuState.hauteurFenetre / 2 - (MenuState.hauteurFenetre * 0.3 + texte.size() * 30) / 2),
				(float) (MenuState.largeurFenetre * 0.4), (float) (MenuState.hauteurFenetre * 0.3 + (texte.size() * 30)));

		g.draw(rectangle);
		g.fill(rectangle);

		MenuState.zorque.drawString((int) (MenuState.largeurFenetre * 0.1), (int) (MenuState.hauteurFenetre * 0.3 + (0 * 30)), texte.get(0),
				Color.red);

		for (int i = 1; i < texte.size(); i++) {

			MenuState.zorque.drawString((int) (MenuState.largeurFenetre * 0.1), (int) (MenuState.hauteurFenetre * 0.3 + (i * 30)), texte.get(i),
					Color.white);

		}

		for (int i = 0; i < terrainEdit.getZone().size(); i++) {
			for (int j = 0; j < terrainEdit.getZone().get(0).size(); j++) {

				int largeur = terrainEdit.getZone().get(i).get(j).getLargeur();
				int hauteur = terrainEdit.getZone().get(i).get(j).getHauteur();

				int x = (int) (MenuState.largeurFenetre - terrainEdit.getZone().get(i).get(j).getLargeur()*(i+1)-10);
				int y = (int) (terrainEdit.getZone().get(i).get(j).getHauteur()*j+10);

				if (i >= terrainEdit.getZone().size() / 2 && j < terrainEdit.getZone().get(0).size() / 2) {
					terrain.add(new ArrayList<Bouton>());
					terrain.get(i - terrainEdit.getZone().size() / 2).add(new Bouton(x, y, hauteur, largeur, "x"));
				}

				Rectangle rect = new Rectangle(x, y, largeur, hauteur);

				g.setColor(terrainEdit.getCouleur());

				g.draw(rect);
				
				if (!terrainEdit.getZone().get(i).get(j).getAccessible()) {
					g.setColor(Color.lightGray);
				} else if (!(i >= terrainEdit.getZone().size() / 2 && j < terrainEdit.getZone().get(0).size() / 2)) {
					g.setColor(new Color(255, 0, 0, (float) 0.3));
				} else {
					g.setColor(new Color(255, 255, 255, (float) 0.5));
				}

				Rectangle border = new Rectangle(rect.getX() + 1, rect.getY() + 1, rect.getWidth() - 2, rect.getHeight() - 2);

				g.draw(border);
				g.fill(border);

				terrainEdit.getZone().get(i).get(j).getX();

				if(i == terrainEdit.getZone().size() -1 && j==0) {
					cross.draw(x,y,hauteur,largeur);
				}else if(i== 0 && j==0) {
					cross.draw(x,y,hauteur,largeur);
				}else if(i==0 && j==terrainEdit.getZone().size() -1) {
					cross.draw(x,y,hauteur,largeur);
				}else if(i==terrainEdit.getZone().size() -1 && j==terrainEdit.getZone().size() -1) {
					cross.draw(x,y,hauteur,largeur);
				}
				
			}
		}

		drawButton(retour, boutonColor, g);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (retour.estCliquer(x, y)) {

				this.game.enterState(Parameters.ID);

			}
			for (int i = 0; i < terrainEdit.getZone().size() / 2; i++) {
				for (int j = 0; j < terrainEdit.getZone().get(0).size() / 2; j++) {

					if (terrain.get(i).get(j).estCliquer(x, y) && !(i == terrainEdit.getZone().size()/2 - 1 && j == 0)) {
						terrainEdit.getZone().get(i + terrainEdit.getZone().size() / 2).get(j)
								.setAccessible(!terrainEdit.getZone().get(i + terrainEdit.getZone().size() / 2).get(j).getAccessible());
						terrainEdit = Damier.terrain5(terrainEdit);

						terrainEdit.getZone().get(terrainEdit.getZone().size() - 1).get(0).setAccessible(true);
					}

				}
			}
		}
	}

	static public void initTerrain(int taille) {

		terrain = new ArrayList<ArrayList<Bouton>>();
		Damier terrainTemp = new Damier(taille, taille, 1, MenuState.hauteurFenetre, MenuState.largeurFenetre, Color.black);

		int difference = terrainTemp.getZone().size() - terrainEdit.getZone().size();

		terrainEdit = terrainTemp;
	}

	ArrayList<String> transformText(ArrayList<String> texte) {

		ArrayList<String> newTexte = new ArrayList<String>();
		int nbCaractere;
		int nbCaractereMax = (int) ((MenuState.largeurFenetre * 0.4) / MenuState.zorque.getWidth("W"));

		for (int i = 0; i < texte.size(); i++) {

			nbCaractere = texte.get(i).length();

			while (nbCaractere > nbCaractereMax) {

				int index = texte.get(i).substring(0, nbCaractereMax - 1).lastIndexOf(" ");

				newTexte.add(texte.get(i).substring(0, index));
				texte.set(i, texte.get(i).substring(index, texte.get(i).length()));
				nbCaractere = texte.get(i).length();

			}

			newTexte.add(texte.get(i));
		}

		return newTexte;
	}

	void drawButton(Bouton bouton, Color couleur, Graphics g) {
		Rectangle rect = new Rectangle(bouton.getX(), bouton.getY(), bouton.getLargeur(), bouton.getHauteur());
		g.setColor(couleur);
		g.draw(rect);
		g.fill(rect);

		MenuState.zorque.drawString(rect.getX() + rect.getWidth() / 2 - MenuState.zorque.getWidth(bouton.getContenu()) / 2,
				rect.getY() + rect.getHeight() / 2 - MenuState.zorque.getHeight(bouton.getContenu()) / 2, bouton.getContenu(), Color.white);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

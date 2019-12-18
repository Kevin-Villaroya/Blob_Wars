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

import Code.Graphics.Bouton;

public class Credits extends BasicGameState {

	public static final int ID = 4;
	GameContainer container;
	StateBasedGame game;

	Image titre;

	ArrayList<String> texte;
	
	Bouton retour;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		this.container = container;
		this.game = game;
		
		retour = new Bouton((int) (MenuState.largeurFenetre / 2 - MenuState.largeurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre - 10 - MenuState.hauteurFenetre * 0.1),
				(int) (MenuState.hauteurFenetre * 0.07), (int) (MenuState.largeurFenetre * 0.2), "Retour");

		titre = new Image("Assets/Image/Titre/Titre.png");
		texte = new ArrayList<String>();

		// faire un add pour ajouter ligne par ligne affichable (si ca depasse l'ecran
		// transform gere ca)
		texte.add("Projet de TER informatique");
		texte.add("");
		texte.add("Encadré par M. St\u00e9phane Bessy");
		texte.add("Réalisé par Yanis Allouch, J\u00e9r\u00e9mie Roux et Villaroya K\u00e9vin.");
		texte.add("");
		texte.add("Utilisation de la bibliothèque Slick qui facilite la cr\u00e9ation d'interfaces 2Davec LWJGL");
		texte = transformText(texte);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		g.drawImage(MenuState.background, 0, 0);
		g.drawImage(titre, MenuState.largeurFenetre / 2 - titre.getWidth() / 2,
				(int) (MenuState.hauteurFenetre * 0.15 - 30));
		g.setColor(new Color(0, 0, 0, (float) 0.9));

		Rectangle rect = new Rectangle((float) (MenuState.largeurFenetre * 0.1),
				(float) (MenuState.hauteurFenetre * 0.3), (float) (MenuState.largeurFenetre * 0.8),
				(float) (MenuState.hauteurFenetre * 0.3 + (texte.size() * 30)));

		g.draw(rect);
		g.fill(rect);

		for (int i = 0; i < texte.size(); i++) {

			MenuState.zorque.drawString((int) (MenuState.largeurFenetre * 0.1),
					(int) (MenuState.hauteurFenetre * 0.3 + (i * 30)), texte.get(i), Color.white);

		}
		
		rect = new Rectangle(retour.getX(), retour.getY(), retour.getLargeur(), retour.getHauteur());
		g.draw(rect);
		g.fill(rect);
		
		MenuState.zorque.drawString(retour.getX() + retour.getLargeur() / 2 - MenuState.zorque.getWidth("Retour") / 2,
				retour.getY() + retour.getHauteur() / 2 - MenuState.zorque.getHeight("Retour") / 2,
				retour.getContenu());

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	ArrayList<String> transformText(ArrayList<String> texte) {

		ArrayList<String> newTexte = new ArrayList<String>();
		int nbCaractere;
		int nbCaractereMax = (int) ((MenuState.largeurFenetre * 1.2) / MenuState.zorque.getWidth("W"));

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

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (retour.estCliquer(x, y)) {

				this.game.enterState(MenuState.ID);

			}
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
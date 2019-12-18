package Code.Graphics.State;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.FontFormatException;
import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import Code.Graphics.Bouton;

public class MenuState extends BasicGameState {

	public static final int ID = 0;
	static public ArrayList<Image> backgrounds;
	static public ArrayList<Image> characters;
	
	private Image titre;
	static public Image background;

	private GameContainer container;
	private StateBasedGame game;

	static float volumeAmbiance;
	public static float volumeSounds;

	static public Music ambiance;
	static public Sound saut;
	static public Sound dedoublement;
	static public Sound conquete;
	
	static public TrueTypeFont zorque;

	Dimension screenSize;
	static public int largeurFenetre;
	static public int hauteurFenetre;

	private Bouton jouer;
	private Bouton aide;
	private Bouton option;
	private Bouton credits;
	private Bouton quitter;


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		this.game = game;
		this.container = container;

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		largeurFenetre = (int) screenSize.getWidth();
		hauteurFenetre = (int) screenSize.getHeight();

		backgrounds = setBackgrounds();
		background = backgrounds.get(0);

		volumeAmbiance = (float) (0.2);
		volumeSounds =  (float) (0.2);

		ambiance = new Music("Assets/Sound/Ambiance.ogg");
		ambiance.loop();
		ambiance.setVolume(volumeAmbiance);
		
		saut = new Sound("Assets/Sound/BlobSound.ogg");
		dedoublement = new Sound("Assets/Sound/Dedoublement.ogg");
		conquete = new Sound("Assets/Sound/Conquete.ogg");

		titre = new Image("Assets/Image/Titre/Titre.png");

		InputStream inputStream = ResourceLoader.getResourceAsStream("Assets/Font/zorque.ttf");

		Font awFont;
		try {
			awFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awFont = awFont.deriveFont(24f); // set font size

			zorque = new TrueTypeFont(awFont, false);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jouer = new Bouton((int) (largeurFenetre / 2 - largeurFenetre * 0.1),
				(int) (hauteurFenetre - 30 - hauteurFenetre * 0.6), (int) (hauteurFenetre * 0.07),
				(int) (largeurFenetre * 0.2), "Jouer");
		aide = new Bouton((int) (largeurFenetre / 2 - largeurFenetre * 0.1),
				(int) (hauteurFenetre - 25 - hauteurFenetre * 0.5), (int) (hauteurFenetre * 0.07),
				(int) (largeurFenetre * 0.2), "Aide");
		option = new Bouton((int) (largeurFenetre / 2 - largeurFenetre * 0.1),
				(int) (hauteurFenetre - 20 - hauteurFenetre * 0.4), (int) (hauteurFenetre * 0.07),
				(int) (largeurFenetre * 0.2), "Options");
		credits = new Bouton((int) (largeurFenetre / 2 - largeurFenetre * 0.1),
				(int) (hauteurFenetre - 15 - hauteurFenetre * 0.3), (int) (hauteurFenetre * 0.07),
				(int) (largeurFenetre * 0.2), "Cr\u00e9dits");
		quitter = new Bouton((int) (largeurFenetre / 2 - largeurFenetre * 0.1),
				(int) (hauteurFenetre - 10 - hauteurFenetre * 0.2), (int) (hauteurFenetre * 0.07),
				(int) (largeurFenetre * 0.2), "Quitter");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		g.setFont(this.zorque);
		g.drawImage(background, 0, 0);

		g.drawImage(titre, largeurFenetre / 2 - titre.getWidth() / 2,
				(int) (hauteurFenetre - 30 - hauteurFenetre * 0.85));

		for (int i = 0; i < 5; i++) {

			Rectangle rect = null;
			g.setColor(new Color(0, 0, 0, (float) 0.9));

			switch (i) {
			case 0:
				rect = new Rectangle(jouer.getX(), jouer.getY(), jouer.getLargeur(), jouer.getHauteur());
				g.draw(rect);
				g.fill(rect);
				g.setColor(Color.white);
				zorque.drawString(jouer.getX() + jouer.getLargeur() / 2 - g.getFont().getWidth("Jouer") / 2,
						jouer.getY() + jouer.getHauteur() / 2 - g.getFont().getHeight("Jouer") / 2, jouer.getContenu());
				break;
			case 1:
				rect = new Rectangle(aide.getX(), aide.getY(), aide.getLargeur(), aide.getHauteur());
				g.draw(rect);
				g.fill(rect);
				g.setColor(Color.white);
				g.drawString(aide.getContenu(), aide.getX() + aide.getLargeur() / 2 - g.getFont().getWidth("Aide") / 2,
						aide.getY() + aide.getHauteur() / 2 - g.getFont().getHeight("Aide") / 2);
				break;
			case 2:
				rect = new Rectangle(option.getX(), option.getY(), option.getLargeur(), option.getHauteur());
				g.draw(rect);
				g.fill(rect);
				g.setColor(Color.white);
				g.drawString(option.getContenu(),
						option.getX() + option.getLargeur() / 2 - g.getFont().getWidth("Options") / 2,
						option.getY() + option.getHauteur() / 2 - g.getFont().getHeight("Options") / 2);
				break;
			case 3:
				rect = new Rectangle(credits.getX(), credits.getY(), credits.getLargeur(), credits.getHauteur());
				g.draw(rect);
				g.fill(rect);
				g.setColor(Color.white);
				g.drawString(credits.getContenu(),
						credits.getX() + credits.getLargeur() / 2 - g.getFont().getWidth("Credits") / 2,
						credits.getY() + credits.getHauteur() / 2 - g.getFont().getHeight("Credits") / 2);
				break;
			case 4:
				rect = new Rectangle(quitter.getX(), quitter.getY(), quitter.getLargeur(), quitter.getHauteur());
				g.draw(rect);
				g.fill(rect);
				g.setColor(Color.white);
				g.drawString(quitter.getContenu(),
						quitter.getX() + quitter.getLargeur() / 2 - g.getFont().getWidth("Quitter") / 2,
						quitter.getY() + quitter.getHauteur() / 2 - g.getFont().getHeight("Quitter") / 2);
				break;
			}

		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	public ArrayList<Image> setBackgrounds() {
		File repertory = new File("Assets/Image/Backgrounds");
		File[] files = repertory.listFiles();

		ArrayList<Image> images = new ArrayList<Image>();

		for (int i = 0; i < files.length; i++) {
			try {
				images.add(new Image(files[i].getPath()));
				images.get(i).setName(files[i].getName().substring(0, files[i].getName().length() - 4));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return images;
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {

			if (jouer.estCliquer(x, y)) {
				this.game.enterState(BridgeSave.ID);

			} else if (option.estCliquer(x, y)) {
				Option.returnState = ID;
				this.game.enterState(Option.ID);

			} else if (aide.estCliquer(x, y)) {

				this.game.enterState(Aide.ID);
			} else if (credits.estCliquer(x, y)) {
				
				this.game.enterState(Credits.ID);
			} else if (quitter.estCliquer(x, y)) {
				this.container.exit();
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package Code;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Code.Engine.Ia.IA;
import Code.Graphics.StateGame;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int largeurFenetre = (int) screenSize.getWidth();
		int hauteurFenetre = (int) screenSize.getHeight();

		try {
			AppGameContainer game = new AppGameContainer(new StateGame(), largeurFenetre, hauteurFenetre, false);
			game.setIcon("Assets/Image/Icon/icon.png");
			game.setShowFPS(false) ;
			game.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

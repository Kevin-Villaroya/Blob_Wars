package Code.Graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Code.Graphics.State.Aide;
import Code.Graphics.State.BridgeSave;
import Code.Graphics.State.Credits;
import Code.Graphics.State.Editor;
import Code.Graphics.State.End;
import Code.Graphics.State.Load;
import Code.Graphics.State.Plateau;
import Code.Graphics.State.SaveState;
import Code.Graphics.State.MenuState;
import Code.Graphics.State.Option;
import Code.Graphics.State.Parameters;

public class StateGame extends StateBasedGame {

	public StateGame() {
		super("Blob Wars");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MenuState());
		this.addState(new Plateau());
		this.addState(new Option());
		this.addState(new Aide());
		this.addState(new Credits());
		this.addState(new Parameters());
		this.addState(new Editor());
		this.addState(new End());
		this.addState(new BridgeSave());
		this.addState(new Load());
		this.addState(new SaveState());
	}

}

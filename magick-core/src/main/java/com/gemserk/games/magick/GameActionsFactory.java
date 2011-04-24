package com.gemserk.games.magick;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameActionsFactory {
	static private GameActions gameActions;

	static public GameActions getGameActions() {
		if (gameActions == null) {
			gameActions = Gdx.app.getType() == ApplicationType.Android ? new GameActionsAndroid() : new GameActionsDesktop();
		}
		return gameActions;
	}

	static class GameActionsAndroid implements GameActions {
		ButtonBar buttonBar = new ButtonBar(2);

		public boolean jump() {
			return buttonBar.isPressed(0);
		}

		public boolean dash() {
			return buttonBar.isPressed(1);
		}
	}

	static class GameActionsDesktop implements GameActions {
		public boolean jump() {
			return Gdx.input.isKeyPressed(Input.Keys.KEYCODE_Z) || Gdx.input.isButtonPressed(Input.Buttons.LEFT);
		}

		public boolean dash() {
			return Gdx.input.isKeyPressed(Input.Keys.KEYCODE_X) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
		}
	}

}

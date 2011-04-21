package com.gemserk.games.magick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameActions {
	ButtonBar buttonBar = new ButtonBar(2);
	
	public boolean jump(){
		return buttonBar.isPressed(0) || Gdx.input.isKeyPressed(Input.Keys.KEYCODE_Z) || Gdx.input.isButtonPressed(Input.Buttons.LEFT);
	}
	
	public boolean dash(){
		return buttonBar.isPressed(1) || Gdx.input.isKeyPressed(Input.Keys.KEYCODE_X) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
	}
}

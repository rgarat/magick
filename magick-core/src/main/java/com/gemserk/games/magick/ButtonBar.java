package com.gemserk.games.magick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class ButtonBar {

	private final int cantButtons;

	public ButtonBar(int cantButtons) {
		this.cantButtons = cantButtons;
	}

	public boolean isPressed(int button) {
		Input input = Gdx.input;
		int width = Gdx.graphics.getWidth();
		int buttonSize = width / cantButtons;
		for (int i = 0; i < 10; i++) {
			if (input.isTouched(i)) {
				int x = input.getX(i);
				int pressedButton = x / buttonSize;
				if (button == pressedButton) {
					return true;
				}
			}
		}
		return false;
	}

}

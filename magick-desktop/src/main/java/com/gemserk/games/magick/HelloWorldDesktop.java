package com.gemserk.games.magick;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class HelloWorldDesktop {

	public static void main(String[] argv) {
		new LwjglApplication(new Magick(), "Magick", 800, 480, false);
	}
}

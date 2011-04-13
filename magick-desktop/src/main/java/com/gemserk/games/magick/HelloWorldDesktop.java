package com.gemserk.games.magick;

import org.lwjgl.opengl.Display;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class HelloWorldDesktop {

	public static void main(String[] argv) {
		Magick listener = new Magick(){
			@Override
			public void create() {
				super.create();
				Display.setVSyncEnabled(true);
			}
		};
		new LwjglApplication(listener, "Magick", 800, 480, false);
	}
}

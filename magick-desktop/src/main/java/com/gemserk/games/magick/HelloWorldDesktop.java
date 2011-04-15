package com.gemserk.games.magick;

import org.lwjgl.opengl.Display;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class HelloWorldDesktop {

	public static void main(String[] argv) {
		Magick magick = new Magick(){
			@Override
			public void create() {
				Display.setVSyncEnabled(true);
				super.create();
			}
		};
		new LwjglApplication(magick, "Magick", 800, 480, false);
	}
}

package com.gemserk.games.magick;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TearingTest {
	public static void main(String[] argv) {
//		try {
//			Display.setFullscreen(true);
//		} catch (LWJGLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(0);
//		}
		new LwjglApplication(new TearingTestApp(), "Magick", 800, 480, false);
	}
}

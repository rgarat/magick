package com.gemserk.games.magick;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class LwjglAppletApplication extends Applet {

	Canvas display_parent;
	LwjglApplication application;

	public void start() {
	}

	public void stop() {
	}

	public void destroy() {
		remove(display_parent);
		super.destroy();
	}

	public void init() {
		try {
			setLayout(new BorderLayout());
			ApplicationListener game = (ApplicationListener) Class.forName(getParameter("game")).newInstance();

			display_parent = new Canvas() {
				public final void addNotify() {
					super.addNotify();
					application = new LwjglApplication(new Magick(), false, this);
				}

				public final void removeNotify() {
					application.stop();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display", e);
		}
	}
}

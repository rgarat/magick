package com.gemserk.games.magick;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;

public class HelloWorldAndroid extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Magick magick = new Magick();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useGL20 = false;
		config.resolutionStrategy = new FillResolutionStrategy();
		View gameView = initializeForView(magick, config);
		gameView.setKeepScreenOn(true); // optional
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		FrameLayout layout = new FrameLayout(this); // everything at the top left of the screen

		addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		layout.addView(gameView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

	}
}
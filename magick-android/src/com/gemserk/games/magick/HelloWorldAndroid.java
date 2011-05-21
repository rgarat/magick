package com.gemserk.games.magick;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
import com.adwhirl.AdWhirlManager;
import com.adwhirl.AdWhirlTargeting;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;

public class HelloWorldAndroid extends AndroidApplication implements AdWhirlInterface {
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

		FrameLayout layout = new FrameLayout(this); // everything at the top left of the screen

		addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		layout.addView(gameView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		
		
		AdWhirlManager.setConfigExpireTimeout(1000 * 60 * 5);

		AdWhirlTargeting.setAge(23);

		AdWhirlTargeting.setGender(AdWhirlTargeting.Gender.MALE);

		AdWhirlTargeting.setKeywords("online games gaming");

		AdWhirlTargeting.setPostalCode("94123");

		AdWhirlTargeting.setTestMode(false);

		AdWhirlLayout adWhirlLayout = new AdWhirlLayout(this, "c692866f1a864ff2a0d733281fa01a8e");
		adWhirlLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int diWidth = 320;

		int diHeight = 52;

		float density = getResources().getDisplayMetrics().density;

		adWhirlLayout.setAdWhirlInterface(this);

		adWhirlLayout.setMaxWidth((int) (diWidth * density));

		adWhirlLayout.setMaxHeight((int) (diHeight * density));

		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		layout.addView(adWhirlLayout, layoutParams);

		layout.invalidate();
	}

	@Override
	public void adWhirlGeneric() {
		System.out.println("Test adwhirlgeneric");

	}
}
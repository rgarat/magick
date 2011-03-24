package com.gemserk.games.magick;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.gemserk.games.magick.HelloWorld;

public class HelloWorldAndroid extends AndroidApplication {
	@Override public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new HelloWorld(), false);		
	}
}
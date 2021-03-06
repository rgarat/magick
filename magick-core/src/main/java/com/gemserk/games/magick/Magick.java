/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.gemserk.games.magick;

import com.gemserk.games.magick.libgdx.Game;

public class Magick extends Game {

	@Override
	public void create() {
		MagickGameScreen gameScreen = new MagickGameScreen(this);
		SplashScreen splashScreen = new SplashScreen("data/logo-gemserk-512x128-white.png", gameScreen, this);
		setScreen(splashScreen, false);
		
	}

}

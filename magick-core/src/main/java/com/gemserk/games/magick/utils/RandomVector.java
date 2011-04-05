package com.gemserk.games.magick.utils;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class RandomVector {

	static Random random = new Random();
	
	public static Vector2 randomVector(float xmin, float ymin, float xmax, float ymax){
		return randomVector(xmin, ymin, xmax, ymax,new Vector2());
	}
	
	public static Vector2 randomVector(float xmin, float ymin, float xmax, float ymax, Vector2 outVector){
		float x = xmin;
		x+= random.nextFloat()*(xmax - xmin);
		float y = ymin;
		y+=random.nextFloat()*(ymax - ymin);
		outVector.set(x,y);
		return outVector;
	}
}

package com.gemserk.games.magick;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.games.magick.utils.RandomVector;

public class LevelGenerator {
	Random random = new Random();
	static final float START_WIDTH = 10f;
	static final float CENTER_Y = 1f;

	private static final float MINSPACE = 1;
	private static final float VARIABLESPACE = 3;
	private static final int MINWIDTH = 6;
	private static final float VARIABLEWIDTH = 2;
	private static final float VARIABLEY = 1.5f;

	public Entities entities;

	float lastX;
	float lastY;

	public LevelGenerator(Entities entities) {
		this.entities = entities;
	}

	public LevelGenerator() {

	}

	public void init() {
		Vector2 startPosition = Entities.playerStartPosition.cpy();
		float startX = startPosition.x - START_WIDTH / 2f;
		float startY = startPosition.y - 1f;

		entities.floor(startX, startY, START_WIDTH);
		lastX = startX + START_WIDTH;
		lastY = startY;
	}

	public void generateNext() {
		float x = lastX + MINSPACE + VARIABLESPACE * random.nextFloat();
		float y = CENTER_Y + (VARIABLEY * random.nextFloat() - VARIABLEY / 2);
		float width = MINWIDTH + VARIABLEWIDTH * random.nextFloat();
		entities.floor(x, y, width);

		float nextX = x + width;
		float nextY = y;

		float generatedSpace = nextX - lastX;
		float cantClouds = Math.max(1, (generatedSpace * 5f / 10f));
		for (int i = 0; i < cantClouds; i++) {
			Vector2 pos = RandomVector.randomVector(lastX, 0, lastX + generatedSpace, Gdx.graphics.getHeight() / 100f);
			entities.cloud(pos);
		}

		lastX = nextX;
		lastY = nextY;
	}

	public float getLastX() {
		return lastX;
	}

	public float getLastY() {
		return lastY;
	}

}

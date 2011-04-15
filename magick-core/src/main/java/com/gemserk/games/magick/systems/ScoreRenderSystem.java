package com.gemserk.games.magick.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreRenderSystem extends EntitySystem {

	private ScoreSystem system;
	private final SpriteBatch spriteBatch;
	private final BitmapFont font;

	public ScoreRenderSystem(SpriteBatch spriteBatch, BitmapFont font) {
		this.spriteBatch = spriteBatch;
		this.font = font;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		spriteBatch.begin();
		{
			Color oldColor = Color.WHITE;

			font.draw(spriteBatch, "score: " + system.score, 0, 480f);
			if (system.score == system.maxScore) {
				font.setColor(Color.GREEN);
			}
			font.draw(spriteBatch, "HighScore: " + system.maxScore, 0, 460f);
			font.setColor(oldColor);
		}
		spriteBatch.end();

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		system = world.getSystemManager().getSystem(ScoreSystem.class);
	}

}

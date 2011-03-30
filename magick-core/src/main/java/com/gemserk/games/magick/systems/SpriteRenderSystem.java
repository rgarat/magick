package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;

public class SpriteRenderSystem extends EntityProcessingSystem {

	ComponentMapper<PositionComponent> positionMapper;
	ComponentMapper<SpriteComponent> spriteMapper;
	private final SpriteBatch spriteBatch;

	public SpriteRenderSystem(SpriteBatch spriteBatch) {
		super(SpriteComponent.class);
		this.spriteBatch = spriteBatch;
	}
	
	@Override
	protected void begin() {
		super.begin();
		spriteBatch.begin();
	}

	@Override
	protected void process(Entity e) {
		SpriteComponent spriteComponent = spriteMapper.get(e);
		Sprite sprite = spriteComponent.sprite;
		
		sprite.draw(spriteBatch);
	}
	
	@Override
	protected void end() {
		super.end();
		spriteBatch.end();
	}
	

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

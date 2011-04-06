package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;

public class SpriteUpdateSystem extends EntityProcessingSystem {

	ComponentMapper<PositionComponent> positionMapper;
	ComponentMapper<SpriteComponent> spriteMapper;

	public SpriteUpdateSystem() {
		super(PositionComponent.class, SpriteComponent.class);
	}

	@Override
	protected void process(Entity e) {
		PositionComponent positionComponent = positionMapper.get(e);
		SpriteComponent spriteComponent = spriteMapper.get(e);
		
		Vector2 pos = positionComponent.pos;
		
		Sprite sprite = spriteComponent.sprite;
		float width = sprite.getWidth();
		float height = sprite.getHeight();
		sprite.setPosition(pos.x - width/2f, pos.y - height/2f);
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

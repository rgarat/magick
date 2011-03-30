package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.PositionComponent;

public class InputSystem extends EntityProcessingSystem {

	ComponentMapper<PositionComponent> positionMapper;
	
	
	public InputSystem() {
		super(PositionComponent.class);
		
	}
	
	

	@Override
	protected void process(Entity e) {
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		PositionComponent positionComponent = positionMapper.get(e);
		positionComponent.pos.set(x, y);
	}



	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

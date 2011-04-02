package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.PositionComponent;

public class InputSystem extends EntitySystem {

	ComponentMapper<PositionComponent> positionMapper;

	public InputSystem() {
		super();

	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();

		PositionComponent positionComponent = positionMapper.get(entity);
		positionComponent.pos.set(x, y);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

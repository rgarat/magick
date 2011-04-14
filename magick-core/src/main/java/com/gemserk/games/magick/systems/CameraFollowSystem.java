package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.PositionComponent;

public class CameraFollowSystem extends EntitySystem {
	ComponentMapper<PositionComponent> positionMapper;
	
	private final OrthographicCamera camera;

	public CameraFollowSystem(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		PositionComponent positionComponent = positionMapper.get(entity);
		
		Vector2 pos = positionComponent.pos;
		camera.position.set(pos.x + 2, camera.position.y, 0);
		camera.update();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.PositionComponent;

public class PhysicsTransformationSystem extends EntityProcessingSystem {

	ComponentMapper<PositionComponent> positionMapper;
	ComponentMapper<BodyComponent> bodyMapper;

	public PhysicsTransformationSystem() {
		super(PositionComponent.class, BodyComponent.class);
	}

	@Override
	protected void process(Entity e) {
		PositionComponent positionComponent = positionMapper.get(e);
		BodyComponent bodyComponent = bodyMapper.get(e);
		
		Vector2 position = bodyComponent.body.getPosition();
		positionComponent.pos.set(position);
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

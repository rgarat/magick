package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.PositionComponent;

public class CleanupSystem extends IntervalEntitySystem {

	private static final float CLEANUPDISTANCE = 15;

	public CleanupSystem() {
		super(500, PositionComponent.class);
	}

	ComponentMapper<PositionComponent> positionMapper;
	ComponentMapper<BodyComponent> bodyMapper;

	@Override
	protected void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity playerEntity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		BodyComponent bodyComponent = bodyMapper.get(playerEntity);
		Body body = bodyComponent.body;
		float playerX = body.getPosition().x;

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			float entityPosX = positionMapper.get(entity).pos.x;

			if (entityPosX + CLEANUPDISTANCE < playerX) {
				entity.delete();
			}
		}

	}
	
	public void deleteEverything() {
		Entity playerEntity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		Entity backgroundEntity = world.getTagManager().getEntity(Entities.TAG_BACKGROUND);
		int entityCount = world.getEntityManager().getEntityCount();
		for (int i = 0; i < entityCount; i++) {
			Entity entity = world.getEntity(i);
			if(entity!=null && entity != playerEntity && entity!= backgroundEntity){
				entity.delete();
			}
		}
	}

}

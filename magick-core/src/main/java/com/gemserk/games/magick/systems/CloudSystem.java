package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.PositionComponent;

public class CloudSystem extends EntitySystem {

	ComponentMapper<PositionComponent> positionMapper;
	float speed = 500f/1000f;

	public CloudSystem() {
		super();

	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		ImmutableBag<Entity> clouds = world.getGroupManager().getEntities(Entities.GROUP_CLOUDS);
	
		int delta = world.getDelta();
		
		for (int i = 0; i < clouds.size(); i++) {
			Entity entity = clouds.get(i);
			PositionComponent positionComponent = positionMapper.get(entity);
			
			Vector2 pos = positionComponent.pos;
			pos.add(-speed*delta, 0);
			if(pos.x<0){
				pos.x = Gdx.graphics.getWidth();
			}
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

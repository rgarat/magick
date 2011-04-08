package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.PositionComponent;

public class PhysicsCloudSystem extends EntitySystem {

	ComponentMapper<PositionComponent> positionMapper;
	ComponentMapper<BodyComponent> bodyMapper;
	
	float speed = 500f/1000f;

	public PhysicsCloudSystem() {
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
		
		Vector2 force = new Vector2(0,0);
		
		for (int i = 0; i < clouds.size(); i++) {
			Entity entity = clouds.get(i);
			PositionComponent positionComponent = positionMapper.get(entity);
			BodyComponent bodyComponent = bodyMapper.get(entity);
			
			Body body = bodyComponent.body;
			Vector2 position = body.getPosition();
			force.set(-100,0);
			body.applyForce(force, position);
			if(position.x<0){
				position.x = Gdx.graphics.getWidth();
				body.setTransform(position, 0);
			}
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

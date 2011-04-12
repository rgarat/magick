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

public class RunningSystem extends EntitySystem {

	ComponentMapper<BodyComponent> bodyMapper;

	public RunningSystem() {
		super();

	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	Vector2 force = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		force.set(0.1f, 0);
		
		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;
		body.applyForce(force, body.getPosition());
//		Gdx.app.log("Magick", "Speed: " + body.getLinearVelocity());
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}

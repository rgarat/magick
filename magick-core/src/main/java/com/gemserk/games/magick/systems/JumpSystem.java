package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;

public class JumpSystem extends EntitySystem {

	ComponentMapper<BodyComponent> bodyMapper;

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	Vector2 force = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();

		if (Gdx.input.isTouched()) {
			BodyComponent bodyComponent = bodyMapper.get(entity);
			Body body = bodyComponent.body;
			force.set(0, 1f);
			body.applyForce(force, body.getPosition());
		}

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

package com.gemserk.games.magick.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsSystem extends EntitySystem {

	World physicsWorld;
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		physicsWorld.step(Gdx.graphics.getDeltaTime(), 3, 3);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		physicsWorld = new World(new Vector2(0,0), true);
	}
	
	public World getPhysicsWorld() {
		return physicsWorld;
	}

}

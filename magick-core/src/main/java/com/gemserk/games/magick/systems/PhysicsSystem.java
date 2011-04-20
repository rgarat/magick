package com.gemserk.games.magick.systems;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.gemserk.games.magick.utils.ContactListenerMultiplexer;

public class PhysicsSystem extends EntitySystem {

	public static final Vector2 GRAVITY = new Vector2(0,-10);
	World physicsWorld;
	private ContactListenerMultiplexer conctactListenerMultiplexer;
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		physicsWorld.step(world.getDelta()/1000f, 3, 3);
		
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		physicsWorld = new World(GRAVITY, true);
		conctactListenerMultiplexer = new ContactListenerMultiplexer();
		physicsWorld.setContactListener(conctactListenerMultiplexer);
	}
	
	public World getPhysicsWorld() {
		return physicsWorld;
	}
	
	public void addContactListener(ContactListener contactListener){
		conctactListenerMultiplexer.addListener(contactListener);		
	}
	
	public void removeContactListener(ContactListener contactListener){
		conctactListenerMultiplexer.removeListener(contactListener);		
	}

}

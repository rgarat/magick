package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.utils.ContactListenerMultiplexer;

public class PhysicsSystem extends EntitySystem implements Disposable{

	public static final Vector2 GRAVITY = new Vector2(0,-10);
//	public static final Vector2 GRAVITY = new Vector2(0,0);
	World physicsWorld;
	private ContactListenerMultiplexer contactListenerMultiplexer;
	ComponentMapper<BodyComponent> bodyMapper;
	
	public PhysicsSystem() {
		super(BodyComponent.class);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		physicsWorld.step(world.getDelta()/1000f, 3, 3);
		
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	@Override
	protected void removed(Entity e) {
		BodyComponent bodyComponent = bodyMapper.get(e);
		if(bodyComponent!=null){
			physicsWorld.destroyBody(bodyComponent.body);
		}
	}

	@Override
	public void initialize() {
		physicsWorld = new World(GRAVITY, true);
		contactListenerMultiplexer = new ContactListenerMultiplexer();
		physicsWorld.setContactListener(contactListenerMultiplexer);
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}
	
	public World getPhysicsWorld() {
		return physicsWorld;
	}
	
	public void addContactListener(ContactListener contactListener){
		contactListenerMultiplexer.addListener(contactListener);		
	}
	
	public void removeContactListener(ContactListener contactListener){
		contactListenerMultiplexer.removeListener(contactListener);		
	}
	
	public void cleanContactListeners(){
		contactListenerMultiplexer.removeAll();
	}
	
	
	@Override
	public void dispose() {
		physicsWorld.dispose();
	}

}

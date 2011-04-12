package com.gemserk.games.magick.systems;

import java.util.ArrayList;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;

public class GroundDetectionSystem extends EntitySystem {

	ComponentMapper<BodyComponent> bodyMapper;
	private PhysicsSystem physicsSystem;

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		Body body = bodyMapper.get(entity).body;
		ArrayList<Contact> contactList = (ArrayList<Contact>) physicsSystem.getPhysicsWorld().getContactList();
		for (int i = 0; i < contactList.size(); i++) {
			Contact contact = contactList.get(i);
			if((((contact.getFixtureA().getBody() == body)) || (contact.getFixtureB().getBody()==body))){
				Gdx.app.log("Magick", "touching");
				Fixture fixtureA = contact.getFixtureA();
			}
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public void initialize() {
		physicsSystem = world.getSystemManager().getSystem(PhysicsSystem.class);
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

}

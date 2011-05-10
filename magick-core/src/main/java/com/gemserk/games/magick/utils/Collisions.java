package com.gemserk.games.magick.utils;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.GroupManager;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.BodyComponent;

public class Collisions {

	ComponentMapper<BodyComponent> bodyMapper;
	
	private World world;

	public Collisions(World world) {
		this.world = world;
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	public enum Result {
		AB, BA, NONE;
		
		public boolean collided(){
			return this != NONE;
		}
	}

	public Result betweenTagGroup(String tag, String group, Contact contact) {
		Entity entity = world.getTagManager().getEntity(tag);
		GroupManager groupManager = world.getGroupManager();
		Body tagBody = bodyMapper.get(entity).body;
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		Entity entityA = (Entity) (bodyA.getUserData());
		Entity entityB = (Entity) (bodyB.getUserData());
		if ((bodyA == tagBody) && group.equals(groupManager.getGroupOf(entityB))) {
			return Result.AB;
		} else if (bodyB == tagBody && group.equals(groupManager.getGroupOf(entityA))) {
			return Result.BA;
		} else {
			return Result.NONE;
		}
	}

}

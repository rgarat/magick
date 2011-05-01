package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.LevelGenerator;
import com.gemserk.games.magick.components.BodyComponent;

public class GenerateLevelSystem extends EntitySystem {

	private static final float DISTANCETOGENERATE = 20;
	private final LevelGenerator levelGenerator;

	public GenerateLevelSystem(LevelGenerator levelGenerator) {
		this.levelGenerator = levelGenerator;
		// TODO Auto-generated constructor stub
	}

	ComponentMapper<BodyComponent> bodyMapper;

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
		reset();
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;
		
		float playerX = body.getPosition().x;
		float lastX = levelGenerator.getLastX();
		if(playerX > lastX - DISTANCETOGENERATE){
			levelGenerator.generateNext();
		}
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	void reset() {
		levelGenerator.init();
		levelGenerator.generateNext();
	}

}

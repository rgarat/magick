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
import com.gemserk.games.magick.MagickGameScreen;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.libgdx.Game;

public class DeadDetectionSystem extends EntitySystem {

	private static final float DEADALTITUDE = -1;
	ComponentMapper<BodyComponent> bodyMapper;
	private GenerateLevelSystem generateLevelSystem;
	private CleanupSystem cleanupSystem;
	private final Game game;

	public DeadDetectionSystem(Game game) {
		super();
		this.game = game;
		
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
		generateLevelSystem = world.getSystemManager().getSystem(GenerateLevelSystem.class);
		cleanupSystem = world.getSystemManager().getSystem(CleanupSystem.class);
	}

	Vector2 force = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		force.set(0.1f, 0);
		
		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;
		if(body.getPosition().y < DEADALTITUDE){
//			body.setTransform(Entities.playerStartPosition, 0);
//			body.setLinearVelocity(new Vector2(0,0));
//			cleanupSystem.deleteEverything();
//			generateLevelSystem.reset();
			game.setScreen(new MagickGameScreen(game), true);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}

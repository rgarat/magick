package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.MagickGameScreen;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.libgdx.Game;
import com.gemserk.games.magick.utils.Collisions;
import com.gemserk.games.magick.utils.Collisions.Result;

public class DeadDetectionSystem extends EntitySystem {

	private static final float DEADALTITUDE = -1;
	ComponentMapper<BodyComponent> bodyMapper;
	private GenerateLevelSystem generateLevelSystem;
	private CleanupSystem cleanupSystem;
	private final Game game;
	private Collisions collisions;
	
	boolean playerShouldDie = false;
	private final MagickGameScreen gameScreen;
	private final Entities entitiesBuilder;

	public DeadDetectionSystem(Game game, MagickGameScreen gameScreen, Entities entities) {
		super();
		this.game = game;
		this.gameScreen = gameScreen;
		this.entitiesBuilder = entities;

	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
		generateLevelSystem = world.getSystemManager().getSystem(GenerateLevelSystem.class);
		cleanupSystem = world.getSystemManager().getSystem(CleanupSystem.class);
		collisions = new Collisions(world);
		world.getSystemManager().getSystem(PhysicsSystem.class).addContactListener(new ContactListener() {

			Vector2 upNormal = new Vector2(0, 1);
			Vector2 downNormal = new Vector2(0, -1);

			@Override
			public void endContact(Contact contact) {
			}

			Vector2 directedNormal = new Vector2();

			@Override
			public void beginContact(Contact contact) {
				Result collisionResult = collisions.betweenTagGroup(Entities.TAG_PLAYER, Entities.GROUP_GROUND, contact);

				if (!collisionResult.collided())
					return;

				Vector2 normal = contact.getWorldManifold().getNormal();
				Vector2 desiredNormal = downNormal;

				if (collisionResult == Result.BA)
					desiredNormal = upNormal;

				if (normal.dst2(desiredNormal) > 0.1f) {
					playerShouldDie = true;
				}
			}

		});
	}

	Vector2 force = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		force.set(0.1f, 0);

		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;
		Vector2 position = body.getPosition();
		Vector2 velocity = body.getLinearVelocity();
		if (position.y < DEADALTITUDE || playerShouldDie || Gdx.input.isKeyPressed(Keys.K) ){
			world.getSystemManager().getSystem(PhysicsSystem.class).cleanContactListeners();
			for (int i = 0; i < 5; i++) {
				entitiesBuilder.explosionBalls(position.x, position.y, velocity.x, (float)Math.random() * 3 - 1.5f);				
			}
			entity.delete();
			gameScreen.playerDied = true;
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}

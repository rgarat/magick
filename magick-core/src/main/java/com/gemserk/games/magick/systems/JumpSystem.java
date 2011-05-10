package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.GroupManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.GameActions;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.utils.Collisions;
import com.gemserk.games.magick.utils.Collisions.Result;

public class JumpSystem extends EntitySystem {

	ComponentMapper<BodyComponent> bodyMapper;

	boolean onGround = false;
	int flyTime = 0;
	int maxFlyTime = 700;
	boolean lifted = false;
	
	private final GameActions gameActions;
	
	private Collisions collisions;

	public JumpSystem(GameActions gameActions) {
		this.gameActions = gameActions;
	}
	

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
		collisions = new Collisions(world);
		world.getSystemManager().getSystem(PhysicsSystem.class).addContactListener(new ContactListener() {

			Vector2 upNormal = new Vector2(0,1);
			Vector2 downNormal = new Vector2(0,-1);
			
			@Override
			public void endContact(Contact contact) {
				if (collisions.betweenTagGroup(Entities.TAG_PLAYER, Entities.GROUP_GROUND, contact).collided() ) {
					onGround = false;
				}

			}

			Vector2 directedNormal = new Vector2();
			@Override
			public void beginContact(Contact contact) {
				Result collisionResult = collisions.betweenTagGroup(Entities.TAG_PLAYER, Entities.GROUP_GROUND,  contact);
				
				if(!collisionResult.collided())
					return;
				
				Vector2 normal = contact.getWorldManifold().getNormal();
				Vector2 desiredNormal = downNormal;
				
				if(collisionResult == Result.BA)
					desiredNormal = upNormal;
				
				if(normal.dst2(desiredNormal) < 0.001f){
					onGround = true;
					resetJumps();
				}
			}

		});
	}

	Vector2 force = new Vector2();
	int jumpCount = 0;
	boolean canDoubleJump = true;
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		if(gameActions.jump()){
			BodyComponent bodyComponent = bodyMapper.get(entity);
			Body body = bodyComponent.body;
			if ( (jumpCount < 2 && lifted)) {
				flyTime = maxFlyTime;
				lifted = false;
				force.set(0, 0.2f);
				Vector2 linearVelocity = body.getLinearVelocity();
				linearVelocity.y = 0;
				body.setLinearVelocity(linearVelocity);
				body.applyLinearImpulse(force, body.getPosition());
				jumpCount +=1;
			} else {
				if(!lifted) {
					flyTime -= world.getDelta();
					if(flyTime > 0){
						force.set(0, 0.01f);
						body.applyLinearImpulse(force, body.getPosition());
					}
				}
			}
		} else {
			lifted = true;
		
		}

	}
	
	public void resetJumps(){
		jumpCount = 0;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

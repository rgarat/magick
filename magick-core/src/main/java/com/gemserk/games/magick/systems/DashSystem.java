package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.GameActions;
import com.gemserk.games.magick.components.BodyComponent;

public class DashSystem extends EntitySystem {

	static final long DASHTIME = 200;
	static final long DASHTIMEOUT = 500;

	ComponentMapper<BodyComponent> bodyMapper;
	private final GameActions gameActions;

	private boolean dashing = false;
	long dashTimeLeft = 0;
	long currentDashTimeout = 0;
	private JumpSystem jumpSystem;

	public DashSystem(GameActions gameActions) {
		this.gameActions = gameActions;
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
		jumpSystem = world.getSystemManager().getSystem(JumpSystem.class);
	}

	Vector2 negativeGravity = new Vector2();

	Vector2 linearVelocity = new Vector2();
	float oldLinearX = 0f;

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;

		if (!dashing && canDash() && gameActions.dash()) {
			dashing = true;
			dashTimeLeft = DASHTIME;
			oldLinearX = body.getLinearVelocity().x;
		} else if (!dashing && !canDash()){
			currentDashTimeout -= world.getDelta();
		} else if (dashing) {
			negativeGravity.set(0, 0).sub(PhysicsSystem.GRAVITY).mul(body.getMass());
			body.applyForce(negativeGravity, body.getPosition());
			body.setLinearVelocity(linearVelocity.set(10, 0));
			dashTimeLeft -= world.getDelta();
			if (dashTimeLeft < 0) {
				dashing = false;
				body.setLinearVelocity(linearVelocity.set(oldLinearX, body.getLinearVelocity().y));
//				jumpSystem.resetJumps();
				currentDashTimeout = DASHTIMEOUT;
			}
		}

	}
	
	private boolean canDash() {
		return currentDashTimeout <= 0;
	}

	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

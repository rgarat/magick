package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;

public class ScoreSystem extends EntitySystem {
	ComponentMapper<BodyComponent> bodyMapper;
	public int score = 0;
	public int maxScore = 0;

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	Vector2 force = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		force.set(0.1f, 0);

		BodyComponent bodyComponent = bodyMapper.get(entity);
		Body body = bodyComponent.body;
		float posX = body.getPosition().x;
		score = MathUtils.ceilPositive(posX);
		
		if (score > maxScore)
			maxScore = score;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

package com.gemserk.games.magick.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.Entities;
import com.gemserk.games.magick.components.BodyComponent;

public class InputSystem extends EntitySystem {

	ComponentMapper<BodyComponent> bodyMapper;
	private final Camera camera;

	public InputSystem(Camera camera) {
		super();
		this.camera = camera;

	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}
	
	Vector3 vector3 = new Vector3();
	Vector2 vector2 = new Vector2();

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = world.getTagManager().getEntity(Entities.TAG_PLAYER);
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		vector3.set(x, y, 0);
		camera.unproject(vector3);
		vector2.set(vector3.x, vector3.y);

		BodyComponent bodyComponent = bodyMapper.get(entity);
		bodyComponent.body.setTransform(vector2, 0);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

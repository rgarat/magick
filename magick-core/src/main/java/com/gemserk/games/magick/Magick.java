/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.gemserk.games.magick;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.gemserk.games.magick.systems.InputSystem;
import com.gemserk.games.magick.systems.PhysicsCloudSystem;
import com.gemserk.games.magick.systems.PhysicsSystem;
import com.gemserk.games.magick.systems.PhysicsTransformationSystem;
import com.gemserk.games.magick.systems.SpriteRenderSystem;
import com.gemserk.games.magick.systems.SpriteUpdateSystem;
import com.gemserk.games.magick.utils.RandomVector;

public class Magick implements ApplicationListener {
	SpriteBatch spriteBatch;
	Texture texture;
	private World world;
	private EntitySystem inputSystem;
	private EntitySystem spriteUpdateSystem;
	private EntitySystem spriteRenderSystem;
	private Entities entities;
	private EntitySystem cloudSystem;
	private EntitySystem physicsSystem;
	private EntitySystem physicsTransformationSystem;
	private Box2DDebugRenderer box2drenderer;
	private BitmapFont font;

	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("data/circle.png"));
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		inputSystem = systemManager.setSystem(new InputSystem());
		spriteUpdateSystem = systemManager.setSystem(new SpriteUpdateSystem());
		cloudSystem = systemManager.setSystem(new PhysicsCloudSystem());
		spriteRenderSystem = systemManager.setSystem(new SpriteRenderSystem(spriteBatch));
		physicsSystem = systemManager.setSystem(new PhysicsSystem());
		physicsTransformationSystem = systemManager.setSystem(new PhysicsTransformationSystem());

		systemManager.initializeAll();

		entities = new Entities(world);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		entities.player();
		for (int i = 0; i < 20; i++) {
			Vector2 pos = RandomVector.randomVector(0, 0, width, height);
			entities.cloud(pos);
		}

		box2drenderer = new Box2DDebugRenderer();

		System.out.println("Arranco");
	}

	@Override
	public void render() {

		update(Gdx.graphics.getDeltaTime());

		realRender();
	}

	private void update(float deltaTime) {
		world.loopStart();
		int delta = (int) (deltaTime * 1000);
		world.setDelta(delta);
		physicsSystem.process();
		physicsTransformationSystem.process();
		inputSystem.process();
		spriteUpdateSystem.process();
		cloudSystem.process();
	}

	private void realRender() {
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteRenderSystem.process();
		box2drenderer.render(((PhysicsSystem) physicsSystem).getPhysicsWorld());
		spriteBatch.begin();
		font.draw(spriteBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20);
		spriteBatch.end();

	}

	@Override
	public void resize(int width, int height) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}

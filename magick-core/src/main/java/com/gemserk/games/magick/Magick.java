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
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.gemserk.games.magick.systems.CameraFollowSystem;
import com.gemserk.games.magick.systems.DeadDetectionSystem;
import com.gemserk.games.magick.systems.GroundDetectionSystem;
import com.gemserk.games.magick.systems.InputSystem;
import com.gemserk.games.magick.systems.JumpSystem;
import com.gemserk.games.magick.systems.PhysicsCloudSystem;
import com.gemserk.games.magick.systems.PhysicsSystem;
import com.gemserk.games.magick.systems.PhysicsTransformationSystem;
import com.gemserk.games.magick.systems.RunningSystem;
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
	OrthographicCamera camera = new OrthographicCamera(8.00f, 4.80f);
	OrthographicCamera hudCamera = new OrthographicCamera(800, 400);
	private EntitySystem runningSystem;
	private EntitySystem cameraFollowSystem;
	private EntitySystem jumpSystem;
	private EntitySystem groundDetectionSystem;
	private EntitySystem deadDetectionSystem;

	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("data/circle.png"));
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		physicsSystem = systemManager.setSystem(new PhysicsSystem());
		inputSystem = systemManager.setSystem(new InputSystem(camera));
		spriteUpdateSystem = systemManager.setSystem(new SpriteUpdateSystem());
		cloudSystem = systemManager.setSystem(new PhysicsCloudSystem());
		spriteRenderSystem = systemManager.setSystem(new SpriteRenderSystem(spriteBatch));
		physicsTransformationSystem = systemManager.setSystem(new PhysicsTransformationSystem());
		runningSystem = systemManager.setSystem(new RunningSystem());
		cameraFollowSystem = systemManager.setSystem(new CameraFollowSystem(camera));
		groundDetectionSystem = systemManager.setSystem(new GroundDetectionSystem());
		deadDetectionSystem = systemManager.setSystem(new DeadDetectionSystem());

		jumpSystem = systemManager.setSystem(new JumpSystem());
		
		
		ImmutableBag<EntitySystem> systems = systemManager.getSystems();
		for (int i = 0; i < systems.size(); i++) {
			EntitySystem system = systems.get(i);
			system.initialize();
		}
//		systemManager.initializeAll();

		entities = new Entities(world);

		float width = Gdx.graphics.getWidth() *100/ 100f;
		float height = Gdx.graphics.getHeight() / 100f;

		entities.player(1,3);
		for (int i = 0; i < 300; i++) {
			Vector2 pos = RandomVector.randomVector(0, 0, width, height);
			entities.cloud(pos);
		}
		
		entities.floor();

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
//		physicsSystem.process();
////		groundDetectionSystem.process();
//		runningSystem.process();
//		physicsTransformationSystem.process();
////		inputSystem.process();
//		spriteUpdateSystem.process();
////		cloudSystem.process();
//		jumpSystem.process();
		cameraFollowSystem.process();
//		deadDetectionSystem.process();
		
	}
	
	private void realRender() {
		GL10 gl10 = Gdx.graphics.getGL10();
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.projection);
		spriteBatch.setTransformMatrix(camera.view);

		
		spriteRenderSystem.process();
//		camera.apply(gl10);
//		box2drenderer.render(((PhysicsSystem) physicsSystem).getPhysicsWorld());
		
		spriteBatch.setProjectionMatrix(hudCamera.projection);
		spriteBatch.setTransformMatrix(hudCamera.view);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20f);
		}
		spriteBatch.end();

	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("Magick", "Screen size: (" + width + ", " + height + ")");
		camera.viewportWidth = 8f;
		camera.viewportHeight = 4.8f;
		camera.position.set(4, 2.4f, 0);
		camera.update();

		hudCamera.viewportWidth = 800;
		hudCamera.viewportHeight = 480;
		hudCamera.position.set(400, 240f, 0);
		hudCamera.update();

		// spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
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

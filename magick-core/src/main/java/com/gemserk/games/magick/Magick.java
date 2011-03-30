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

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;
import com.gemserk.games.magick.systems.InputSystem;
import com.gemserk.games.magick.systems.SpriteRenderSystem;
import com.gemserk.games.magick.systems.SpriteUpdateSystem;

public class Magick implements ApplicationListener {
	SpriteBatch spriteBatch;
	Texture texture;
	BitmapFont font;
	Vector2 textPosition = new Vector2(100, 100);
	Vector2 textDirection = new Vector2(1, 1);
	private World world;
	private EntitySystem inputSystem;
	private EntitySystem spriteUpdateSystem;
	private EntitySystem spriteRenderSystem;

	@Override
	public void create() {
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		spriteBatch = new SpriteBatch();

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		inputSystem = systemManager.setSystem(new InputSystem());
		spriteUpdateSystem = systemManager.setSystem(new SpriteUpdateSystem());
		spriteRenderSystem = systemManager.setSystem(new SpriteRenderSystem(spriteBatch));

		systemManager.initializeAll();

		Entity entity = world.createEntity();
		entity.addComponent(new PositionComponent(10, 10));
		entity.addComponent(new SpriteComponent(new Sprite(texture)));
		entity.refresh();

		System.out.println("Arranco");
	}

	@Override
	public void render() {

		update(Gdx.graphics.getDeltaTime());

		realRender();
	}

	private void update(float deltaTime) {
		world.loopStart();
		world.setDelta((int)(deltaTime * 1000));
		inputSystem.process();
		spriteUpdateSystem.process();

	}

	private void realRender() {
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteRenderSystem.process();
	}

	@Override
	public void resize(int width, int height) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		textPosition.set(0, 0);
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

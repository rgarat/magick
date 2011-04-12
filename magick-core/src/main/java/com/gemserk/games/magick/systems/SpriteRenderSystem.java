package com.gemserk.games.magick.systems;

import java.util.Comparator;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.gemserk.artemis.components.ComponentMapperInitHelper;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.SpriteComponent;

public class SpriteRenderSystem extends EntitySystem {

	private static final LayeredComparator COMPARATOR = new LayeredComparator();
	ComponentMapper<SpriteComponent> spriteMapper;
	ComponentMapper<LayerComponent> layerMapper;

	private static final class LayeredComparator implements Comparator<SpriteRenderSystem.LayeredSprite> {
		@Override
		public int compare(LayeredSprite o1, LayeredSprite o2) {
			return o1.layer - o2.layer;
		}
	}

	static class LayeredSprite {
		public int layer;
		public Sprite sprite;

		public LayeredSprite() {
		}
	}

	class LayeredSpritePool extends Pool<LayeredSprite> {

		@Override
		protected LayeredSprite newObject() {
			return new LayeredSprite();
		}

		@Override
		public void free(LayeredSprite object) {
			object.sprite = null;
			object.layer = 0;
			super.free(object);
		}
	}

	LayeredSpritePool pool = new LayeredSpritePool();
	Array<LayeredSprite> layeredSprites = new Array<SpriteRenderSystem.LayeredSprite>(false,10,LayeredSprite.class);

	private final SpriteBatch spriteBatch;

	public SpriteRenderSystem(SpriteBatch spriteBatch) {
		super(SpriteComponent.class);
		this.spriteBatch = spriteBatch;
	}

	@Override
	protected void begin() {
		super.begin();
		spriteBatch.begin();
		
	}

	@Override
	protected void end() {
		super.end();
		spriteBatch.end();
		for (int i = 0; i < layeredSprites.size; i++) {
			pool.free(layeredSprites.items[i]);
		}
		layeredSprites.clear();
	}

	@Override
	public void initialize() {
		ComponentMapperInitHelper.config(this, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			SpriteComponent spriteComponent = spriteMapper.get(entity);
			Sprite sprite = spriteComponent.sprite;
			LayerComponent layerComponent = layerMapper.get(entity);
			LayeredSprite layeredSprite = pool.obtain();
			layeredSprite.sprite = sprite;
			int layer = 0;
			if (layerComponent != null)
				layer = layerComponent.layer;

			layeredSprite.layer = layer;
			layeredSprites.add(layeredSprite);
		}

		layeredSprites.sort(COMPARATOR);

		
		
		for (int i = 0; i < layeredSprites.size; i++) {
			LayeredSprite layeredSprite = layeredSprites.items[i];
			layeredSprite.sprite.draw(spriteBatch);
		}

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}

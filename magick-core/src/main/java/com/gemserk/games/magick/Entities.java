package com.gemserk.games.magick;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;

public class Entities {
	
	public static final String GROUP_CLOUDS = "CLOUDS";
	public static final String TAG_PLAYER = "PLAYER";
	private World world;
	private Texture texture;

	public Entities(World world){
		this.world = world;
		texture = new Texture(Gdx.files.internal("data/circle.png"));
	}
	
	public Entity player(){
		Entity entity = world.createEntity();
		entity.addComponent(new PositionComponent(10, 10));
		
		entity.addComponent(new SpriteComponent(new Sprite(texture)));
		entity.addComponent(new LayerComponent(1));
		world.getTagManager().register(TAG_PLAYER, entity);
		entity.refresh();
		return entity;
	}

	public Entity cloud(int x, int y){
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(texture);
		sprite.setColor(1,0,0,1);
		sprite.setOrigin(16,16);
		sprite.setScale(0.5f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x, y));
		entity.addComponent(new LayerComponent(2));
		world.getGroupManager().set(GROUP_CLOUDS, entity);
		entity.refresh();
		return entity;
	}
	
}

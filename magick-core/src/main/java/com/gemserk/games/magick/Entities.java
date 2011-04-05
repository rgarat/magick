package com.gemserk.games.magick;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;
import com.gemserk.games.magick.systems.PhysicsSystem;

public class Entities {
	
	public static final String GROUP_CLOUDS = "CLOUDS";
	public static final String TAG_PLAYER = "PLAYER";
	private World world;
	private Texture texture;
	private PhysicsSystem physicsSystem;

	public Entities(World world){
		this.world = world;
		texture = new Texture(Gdx.files.internal("data/circle.png"));
		physicsSystem = world.getSystemManager().getSystem(PhysicsSystem.class);
	}
	
	public Entity player(){
		Entity entity = world.createEntity();
		entity.addComponent(new PositionComponent(10, 10));
		
		entity.addComponent(new SpriteComponent(new Sprite(texture)));
		entity.addComponent(new LayerComponent(1));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(10, 10);
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(0,0));
		shape.setRadius(16);
		body.createFixture(shape, 10);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));
		
		
		world.getTagManager().register(TAG_PLAYER, entity);
		entity.refresh();
		return entity;
	}

	public Entity cloud(Vector2 vec){
		return cloud(vec.x, vec.y);
	}
	public Entity cloud(float x, float y){
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(texture);
		sprite.setColor(1,0,0,1);
		sprite.setOrigin(16,16);
		sprite.setScale(0.5f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x, y));
		entity.addComponent(new LayerComponent(2));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(0,0));
		shape.setRadius(16);
		body.createFixture(shape, 10);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));
		world.getGroupManager().set(GROUP_CLOUDS, entity);
		entity.refresh();
		return entity;
	}
	
}

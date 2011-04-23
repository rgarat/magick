package com.gemserk.games.magick;

import java.util.Random;

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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;
import com.gemserk.games.magick.systems.PhysicsSystem;

public class Entities {
	
	public static final String GROUP_CLOUDS = "CLOUDS";
	public static final String GROUP_GROUND = "GROUND";
	public static final String TAG_PLAYER = "PLAYER";
	private World world;
	private Texture circleTexture;
	private PhysicsSystem physicsSystem;
	private Texture squareTexture;
	public static  Vector2 playerStartPosition = new Vector2();
	private Random random = new Random();
	private Texture colorSquareTexture;

	public Entities(World world){
		this.world = world;
		circleTexture = new Texture(Gdx.files.internal("data/circle.png"));
		
		squareTexture = new Texture(Gdx.files.internal("data/square.png"));
		colorSquareTexture = new Texture(Gdx.files.internal("data/colorSquare.png"));
		physicsSystem = world.getSystemManager().getSystem(PhysicsSystem.class);
	}
	
	public Entity player(float x, float y){
		playerStartPosition.set(x, y);
		Entity entity = world.createEntity();
		entity.addComponent(new PositionComponent(x, y));
		Sprite sprite = new Sprite(circleTexture);
		sprite.setColor(1,1,1,1);
		sprite.setBounds(0, 0, 0.32f, 0.32f);
		sprite.setOrigin(0.16f, 0.16f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new LayerComponent(1));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x,y);
		bodyDef.fixedRotation = true;
		
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		FixtureDef fixtureDef = new FixtureDef();
		
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(0,0));
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		shape.setRadius(0.16f);
		body.createFixture(fixtureDef);
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
		Sprite sprite = new Sprite(circleTexture);
		
		sprite.setColor(1,0,0,1);
		sprite.setSize(0.32f, 0.32f);
		sprite.setPosition(x, y);
		sprite.setScale(0.5f);
		sprite.setOrigin(0.16f, 0.16f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x, y));
		entity.addComponent(new LayerComponent(2));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, y);
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(0,0));
		shape.setRadius(0.16f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.isSensor= true;
		body.createFixture(fixtureDef);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));
		world.getGroupManager().set(GROUP_CLOUDS, entity);
		entity.refresh();
		return entity;
	}
	
	public void floor(){
		float x = 0;
		float y = 1;
		float width = 5;
		float heightDiff = 1f;
		for(int i = 0; i < 60; i++){
			float currentY = y + heightDiff * random.nextFloat() - heightDiff/2f;
			floor(x,currentY,width);
			x+=width;
			x+=0.4f + random.nextFloat()*2;
		}
	}
	
	public Entity floor(float topLeftX, float topLeftY, float width){
		float height = 1;
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(colorSquareTexture);
		
//		sprite.setColor(1,0,0,1);
		sprite.setSize(width, 1f);
		sprite.setPosition(topLeftX + width/2f, topLeftY - height/2f);
		sprite.setOrigin(width/2f, height/2f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(topLeftX + width/2f, topLeftY - height/2f));
		entity.addComponent(new LayerComponent(-1));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(topLeftX + width/2f, topLeftY - height/2f);
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2f,height/2f );
		body.createFixture(shape, 1);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));
		world.getGroupManager().set(GROUP_GROUND, entity);
		entity.refresh();
		return entity;
	}
	
}

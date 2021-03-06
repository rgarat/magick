package com.gemserk.games.magick;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gemserk.games.magick.components.BodyComponent;
import com.gemserk.games.magick.components.LayerComponent;
import com.gemserk.games.magick.components.PlayerStatus;
import com.gemserk.games.magick.components.PositionComponent;
import com.gemserk.games.magick.components.SpriteComponent;
import com.gemserk.games.magick.systems.PhysicsSystem;

public class Entities {

	public static final String GROUP_CLOUDS = "CLOUDS";
	public static final String GROUP_GROUND = "GROUND";
	public static final String TAG_PLAYER = "PLAYER";
	public static final String TAG_BACKGROUND = "BACKGROUND";
	private World world;
	private PhysicsSystem physicsSystem;
	public static Vector2 playerStartPosition = new Vector2(1, 3);
	private Random random = new Random();

	static private boolean loaded = false;
	static private Texture circleTexture;
	static private Texture squareTexture;
	static private Texture colorSquareTexture;
	static private Texture cloudTexture;
	static private Texture backgroundTexture;

	public Entities(World world) {
		this.world = world;
		if (!loaded) {
			loadTextures();
			loaded = true;
		}
		physicsSystem = world.getSystemManager().getSystem(PhysicsSystem.class);
	}

	private void loadTextures() {
		circleTexture = new Texture(Gdx.files.internal("data/circle.png"));

		squareTexture = new Texture(Gdx.files.internal("data/square.png"));
		colorSquareTexture = new Texture(Gdx.files.internal("data/colorSquare.png"));
		cloudTexture = new Texture(Gdx.files.internal("data/cloud-256x256.png"));
		cloudTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		backgroundTexture = new Texture(Gdx.files.internal("data/background-512x512.jpg"));

		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public Entity player() {
		Entity entity = world.createEntity();
		entity.addComponent(new PositionComponent(playerStartPosition.x, playerStartPosition.y));
		Sprite sprite = new Sprite(squareTexture);
		sprite.setColor(1, 1, 1, 1);
		sprite.setBounds(0, 0, 0.2513f, 0.32f);
		sprite.setOrigin(0.2513f/2f, 0.32f/2f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new LayerComponent(1));
		entity.addComponent(new PlayerStatus());
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(playerStartPosition);
		bodyDef.fixedRotation = true;
		
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		FixtureDef fixtureDef = new FixtureDef();

//		CircleShape shape = new CircleShape();
//		shape.setPosition(new Vector2(0, 0));
//		shape.setRadius(0.16f);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2513f/2f, 0.32f/2f);
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		body.createFixture(fixtureDef);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));

		world.getTagManager().register(TAG_PLAYER, entity);
		entity.refresh();
		return entity;
	}

	public Entity cloud(Vector2 vec) {
		return cloud(vec.x, vec.y);
	}

	public Entity cloud(float x, float y) {
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(cloudTexture);

		sprite.setSize(1f, 1f);
		sprite.setPosition(x, y);
		sprite.setOrigin(0.16f, 0.16f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x, y));
		entity.addComponent(new LayerComponent(-5));
		world.getGroupManager().set(GROUP_CLOUDS, entity);
		entity.refresh();
		return entity;
	}

	public Entity background(float x, float y) {
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(backgroundTexture, 0, 0, backgroundTexture.getWidth(), backgroundTexture.getHeight() / 2);

		sprite.setSize(8f, 4.8f);
		sprite.setPosition(x, y);
		sprite.setOrigin(4f, 2.4f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x, y));
		entity.addComponent(new LayerComponent(-10));
		world.getGroupManager().set(GROUP_CLOUDS, entity);
		world.getTagManager().register(TAG_BACKGROUND, entity);
		entity.refresh();
		return entity;
	}

	public void floor() {
		float x = 0;
		float y = 1.5f;
		float width = 5;
		float heightDiff = 2f;
		for (int i = 0; i < 500; i++) {
			float currentY = y + heightDiff * random.nextFloat() - heightDiff / 2f;
			float currentWidth = width + (8 * random.nextFloat() - 4f);
			floor(x, currentY, currentWidth);
			x += currentWidth;
			x += 4f + random.nextFloat() * 8 - 4;
		}
	}

	public Entity floor(float topLeftX, float topLeftY, float width) {
		float height = 1;
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(colorSquareTexture);

		// sprite.setColor(1,0,0,1);
		sprite.setSize(width, 1f);
		sprite.setPosition(topLeftX + width / 2f, topLeftY - height / 2f);
		sprite.setOrigin(width / 2f, height / 2f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(topLeftX + width / 2f, topLeftY - height / 2f));
		entity.addComponent(new LayerComponent(-1));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(topLeftX + width / 2f, topLeftY - height / 2f);
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2f, height / 2f);
		body.createFixture(shape, 1);
		shape.dispose();
		entity.addComponent(new BodyComponent(body));
		world.getGroupManager().set(GROUP_GROUND, entity);
		entity.refresh();
		return entity;
	}
	
	
	public Entity explosionBalls(float x, float y, float velX, float velY) {
		Entity entity = world.createEntity();
		Sprite sprite = new Sprite(circleTexture);

		// sprite.setColor(1,0,0,1);
		sprite.setColor(1, 1, 1, 1);
		sprite.setBounds(0, 0, 0.2513f/2f, 0.32f/2f);
		sprite.setOrigin(0.2513f/4f, 0.32f/4f);
		entity.addComponent(new SpriteComponent(sprite));
		entity.addComponent(new PositionComponent(x,y));
		entity.addComponent(new LayerComponent(100));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x,y);
		
		Body body = physicsSystem.getPhysicsWorld().createBody(bodyDef);
		body.setUserData(entity);
		
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.2513f/4f);
	
		FixtureDef fixtureDef = new FixtureDef();

		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0.25f;
		body.createFixture(fixtureDef);
		shape.dispose();
		body.setLinearVelocity(new Vector2(velX,velY));
//		body.applyForce(new Vector2(velX,velY), body.getPosition());
		entity.addComponent(new BodyComponent(body));
		entity.refresh();
		return entity;
	}
}

package com.gemserk.games.magick;

import java.lang.reflect.Field;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.games.magick.libgdx.Game;
import com.gemserk.games.magick.libgdx.Screen;
import com.gemserk.games.magick.systems.CameraFollowSystem;
import com.gemserk.games.magick.systems.CleanupSystem;
import com.gemserk.games.magick.systems.DashSystem;
import com.gemserk.games.magick.systems.DeadDetectionSystem;
import com.gemserk.games.magick.systems.GenerateLevelSystem;
import com.gemserk.games.magick.systems.InputSystem;
import com.gemserk.games.magick.systems.JumpSystem;
import com.gemserk.games.magick.systems.PhysicsCloudSystem;
import com.gemserk.games.magick.systems.PhysicsSystem;
import com.gemserk.games.magick.systems.PhysicsTransformationSystem;
import com.gemserk.games.magick.systems.RunningSystem;
import com.gemserk.games.magick.systems.ScoreRenderSystem;
import com.gemserk.games.magick.systems.ScoreSystem;
import com.gemserk.games.magick.systems.SpriteRenderSystem;
import com.gemserk.games.magick.systems.SpriteUpdateSystem;

public class MagickGameScreen implements Screen {

	static SpriteBatch spriteBatch;
	static BitmapFont font;
	static Box2DDebugRenderer box2drenderer;
	static boolean initialized = false;

	private World world;
	private EntitySystem spriteUpdateSystem;
	private EntitySystem spriteRenderSystem;
	private Entities entities;
	private EntitySystem physicsSystem;
	private EntitySystem physicsTransformationSystem;
	OrthographicCamera camera;
	OrthographicCamera hudCamera;
	private EntitySystem runningSystem;
	private EntitySystem cameraFollowSystem;
	private EntitySystem jumpSystem;
	private EntitySystem deadDetectionSystem;
	private EntitySystem scoreSystem;
	private EntitySystem scoreRenderSystem;
	private EntitySystem dashSystem;
	private EntitySystem generateLevelSystem;
	private EntitySystem cleanupSystem;
	private Game game;

	public MagickGameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		if (!initialized) {
			spriteBatch = new SpriteBatch();
			font = new BitmapFont();
			box2drenderer = new Box2DDebugRenderer();
			initialized = true;
		}
		
		camera = new OrthographicCamera(8.00f, 4.80f);
		hudCamera = new OrthographicCamera(800, 400);

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		physicsSystem = systemManager.setSystem(new PhysicsSystem());
		spriteUpdateSystem = systemManager.setSystem(new SpriteUpdateSystem());
		spriteRenderSystem = systemManager.setSystem(new SpriteRenderSystem(spriteBatch));
		physicsTransformationSystem = systemManager.setSystem(new PhysicsTransformationSystem());
		runningSystem = systemManager.setSystem(new RunningSystem());
		deadDetectionSystem = systemManager.setSystem(new DeadDetectionSystem(game));
		scoreSystem = systemManager.setSystem(new ScoreSystem());
		scoreRenderSystem = systemManager.setSystem(new ScoreRenderSystem(spriteBatch, font));

		jumpSystem = systemManager.setSystem(new JumpSystem(GameActionsFactory.getGameActions()));
		dashSystem = systemManager.setSystem(new DashSystem(GameActionsFactory.getGameActions()));
		cameraFollowSystem = systemManager.setSystem(new CameraFollowSystem(camera));

		entities = new Entities(world);
		generateLevelSystem = systemManager.setSystem(new GenerateLevelSystem(new LevelGenerator(entities)));
		cleanupSystem = systemManager.setSystem(new CleanupSystem());

		systemManager.initializeAll();

		// float width = Gdx.graphics.getWidth() * 100 / 100f;
		// float height = Gdx.graphics.getHeight() / 100f;
		//
		entities.player();
		// for (int i = 0; i < 300; i++) {
		// Vector2 pos = RandomVector.randomVector(0, 0, width, height);
		// entities.cloud(pos);
		// }

		// entities.floor();
		entities.background(0, 0);

		System.out.println("Arranco: " + this);
	}

	int frames = 0;
	int[] times = new int[1000];

	@Override
	public void update(float deltaTime) {
		int delta = (int) (deltaTime * 1000);
		// System.out.println(deltaTime);
		times[delta] = times[delta] + 1;
		frames++;
		if (frames % 1000 == 0) {
			System.out.println("New deltaTime measure");
			for (int i = 0; i < times.length; i++) {
				int time = times[i];
				if (time != 0)
					System.out.println(i + ":= " + time);
			}
		}

		world.loopStart();
		// int delta = (int) (deltaTime * 1000);
		world.setDelta(delta);
		cleanupSystem.process();
		physicsSystem.process();
		// groundDetectionSystem.process();
		runningSystem.process();
		physicsTransformationSystem.process();
		// inputSystem.process();
		// cloudSystem.process();
		jumpSystem.process();
		dashSystem.process();
		scoreSystem.process();
		generateLevelSystem.process();
		cameraFollowSystem.process();
		spriteUpdateSystem.process();
		// Gdx.app.log("Magick", "Entities: " + world.getEntityManager().getEntityCount());
		deadDetectionSystem.process();
	}

	@Override
	public void render() {
		GL10 gl10 = Gdx.graphics.getGL10();
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.projection);
		spriteBatch.setTransformMatrix(camera.view);

		spriteRenderSystem.process();
		 camera.apply(gl10);
		 box2drenderer.render(((PhysicsSystem) physicsSystem).getPhysicsWorld());

		spriteBatch.setProjectionMatrix(hudCamera.projection);
		spriteBatch.setTransformMatrix(hudCamera.view);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20f);
		}
		spriteBatch.end();
		scoreRenderSystem.process();

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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		System.out.println("Dispose: " + this);
		disposeEntitySystems();
	}

	private void disposeEntitySystems() {
		Bag<EntitySystem> bagged = world.getSystemManager().getSystems();
		for (int i = 0; i < bagged.size(); i++) {
			EntitySystem entitySystem = bagged.get(i);
			if (entitySystem instanceof Disposable) {
				Disposable disposable = (Disposable) entitySystem;
				disposable.dispose();
				System.out.println("Disposing of: " + disposable);
			}
		}
	}
}

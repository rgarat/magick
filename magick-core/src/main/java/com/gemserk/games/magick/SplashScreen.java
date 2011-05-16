package com.gemserk.games.magick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gemserk.games.magick.libgdx.Game;
import com.gemserk.games.magick.libgdx.Screen;

public class SplashScreen implements Screen {

	private SpriteBatch spriteBatch;
	private Sprite sprite;
	private final String texturePath;
	private Texture texture;
	private final Game game;
	private final Screen nextScreen;
	
	int timeout =1500;

	public SplashScreen(String texturePath, Screen nextScreen, Game game) {
		this.texturePath = texturePath;
		this.nextScreen = nextScreen;
		this.game = game;
	}
	
	
	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal(texturePath));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		
		resize(sprite, Gdx.graphics.getWidth() - 30);
		centerOn(sprite, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
	
	
	@Override
	public void update(float deltaTime) {
		int delta = (int) (deltaTime*1000);
		timeout-=delta;
		if(timeout < 0){
			game.setScreen(nextScreen,true);
		}
	}

	@Override
	public void render() {
		GL10 gl10 = Gdx.graphics.getGL10();
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		sprite.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {

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
		spriteBatch.dispose();
		texture.dispose();
	}
	
	public static void resize(Sprite sprite, float width) {
		float aspect = (float) sprite.getHeight() / (float) sprite.getWidth();
		float height = width * aspect;
		sprite.setSize(width, height);
	}

	/**
	 * Centers the sprite on the given position.
	 */
	public static void centerOn(Sprite sprite, float x, float y) {
		sprite.setOrigin(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
	}

}

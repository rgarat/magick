package com.gemserk.games.magick;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TearingTestApp implements ApplicationListener {

	private Texture texture;
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Random random = new Random();
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("data/colorSquare.png"));
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		
		float x = 0;
		float y = 1;
		float width = 5;
		float heightDiff = 1f;
		for(int i = 0; i < 60; i++){
			float currentY = y + heightDiff * random.nextFloat() - heightDiff/2f;
			sprites.add(createSprite(x,currentY,width));
			x+=width;
			x+=0.4f + random.nextFloat()*2;
		}
	}
	
	public Sprite createSprite(float topLeftX, float topLeftY, float width){
		float height = 1;
		Sprite sprite = new Sprite(texture);
		sprite.setSize(width, 1f);
		sprite.setPosition(topLeftX + width/2f, topLeftY - height/2f);
		sprite.setOrigin(width/2f, height/2f);
		return sprite;
	}

	@Override
	public void resume() {

	}

	@Override
	public void render() {
		GL10 gl10 = Gdx.graphics.getGL10();
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.position.add(8.15f * Gdx.graphics.getDeltaTime(), 0, 0);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.projection);
		spriteBatch.setTransformMatrix(camera.view);
		spriteBatch.begin();
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.get(i);
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
		

	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(8.00f, 4.80f);
		camera.position.set(4, 2.4f, 0);
		camera.update();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

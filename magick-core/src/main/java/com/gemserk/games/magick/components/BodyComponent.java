package com.gemserk.games.magick.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent extends Component {
	public Body body;

	public BodyComponent(Body body) {
		this.body = body;
	}
	
	
}

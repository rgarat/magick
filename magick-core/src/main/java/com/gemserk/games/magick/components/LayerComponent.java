package com.gemserk.games.magick.components;

import com.artemis.Component;

public class LayerComponent extends Component{
	public int layer = 0;
	
	public LayerComponent() {
		this(0);
	}

	public LayerComponent(int layer) {
		this.layer = layer;
	}
}
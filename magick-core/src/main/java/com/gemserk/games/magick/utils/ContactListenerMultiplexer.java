package com.gemserk.games.magick.utils;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

public class ContactListenerMultiplexer implements ContactListener {
	private ArrayList<ContactListener> listeners = new ArrayList<ContactListener>(4);

	public void addListener (ContactListener contactListener) {
		listeners.add(contactListener);
	}

	public void removeListener (ContactListener contactListener) {
		listeners.remove(contactListener);
	}

	@Override
	public void beginContact(Contact contact) {
		for (int i = 0, n = listeners.size(); i < n; i++)
			listeners.get(i).beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		for (int i = 0, n = listeners.size(); i < n; i++)
			listeners.get(i).endContact(contact);
	}
}

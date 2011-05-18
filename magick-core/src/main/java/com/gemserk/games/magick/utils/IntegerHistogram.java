package com.gemserk.games.magick.utils;

import com.badlogic.gdx.math.MathUtils;

public class IntegerHistogram {
	int[] values;

	public IntegerHistogram() {
		this(100);
	}

	public IntegerHistogram(int initialValue) {
		values = new int[initialValue];
	}

	public void add(int value) {
		if (value >= values.length) {
			int[] newValues = new int[MathUtils.nextPowerOfTwo(value + 1)];
			
			System.arraycopy(values, 0, newValues, 0, values.length);
			values = newValues;
			System.out.println("Creciendo: " + values.length);
		}
		values[value] = values[value] + 1;
	}

	public void print() {
		for (int i = 0; i < values.length; i++) {
			int value = values[i];
			if (value != 0)
				System.out.println(i + ":= " + value);
		}
	}
}
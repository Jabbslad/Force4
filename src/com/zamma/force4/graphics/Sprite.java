package com.zamma.force4.graphics;

public class Sprite {
	public final int SIZE;
	
	private SpriteSheet sheet;
	public int[] pixels;
	
	public int x, y;
	
	public static Sprite testSprite = new Sprite(32, 0, 0, SpriteSheet.outdoorGrassy);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		
		loadSprite();
	}

	private void loadSprite() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + (y * SIZE)] = sheet.pixels[(x+this.x) + ((y+this.y) * sheet.SIZE)];
			}
		}
	}
}

package com.zamma.force4.graphics;

import java.util.Arrays;
import java.util.Random;

import com.zamma.force4.Game;

public class Screen extends Bitmap {
	private static final int GAME_PANEL_WIDTH = 80;
	
	private Bitmap viewport;
	private Bitmap gamePanel;
	
	private Bitmap testImage;
	private Bitmap testImage1;
	private Bitmap testImage2;
	
	public Screen(int width, int height) {
		super(width, height);
		
		gamePanel = new Bitmap(GAME_PANEL_WIDTH, height);
		viewport = new Bitmap(width - GAME_PANEL_WIDTH, height);
		
		testImage = new Bitmap(32, 32);
		testImage1 = new Bitmap(64, 64);
		testImage2 = new Bitmap(64, 64);
		
		Random random = new Random();
		for (int i = 0; i < testImage.pixels.length; i++) {
			testImage.pixels[i] = random.nextInt(0xffffff);
		}
		for (int i = 0; i < testImage1.pixels.length; i++) {
			testImage1.pixels[i] = random.nextInt(0xffffff);
		}
		for (int i = 0; i < testImage2.pixels.length; i++) {
			testImage2.pixels[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		Arrays.fill(pixels, 0);
		Arrays.fill(gamePanel.pixels, 0);
		Arrays.fill(viewport.pixels, 0);
	}
	
	public void clear(int color) {
		if (color < 0) color = 0;
		if (color > 0xffffff) color = 0xffffff;
		
		Arrays.fill(pixels, color);
		Arrays.fill(gamePanel.pixels, color);
		Arrays.fill(viewport.pixels, color);
	}

	public void render(Game game) {
		for (int i = 0; i < testImage.pixels.length; i++) {
			testImage.pixels[i] = Sprite.testSprite.pixels[i];
		}
		
		viewport.draw(testImage1, ((viewport.width - 64) >> 1) + 40, ((viewport.height - 64) >> 1));
		viewport.draw(testImage2, ((viewport.width - 64) >> 1) - 40, ((viewport.height - 64) >> 1));
		
		for (int i = 0; i < 1; i++) {
			int xOff = (int)(Math.sin((System.currentTimeMillis() + (i << 4)) % 5000 / 5000.0 * Math.PI * 2) * 100);
			int yOff = (int)(Math.cos((System.currentTimeMillis() + (i << 4)) % 2000 / 2000.0 * Math.PI * 2) * 60);
			viewport.draw(testImage, ((viewport.width - 16) >> 1) + xOff, ((viewport.height - 16) >> 1) + yOff);
		}
		
		draw(viewport, 0, 0);
		draw(gamePanel, width - GAME_PANEL_WIDTH, 0);
	}
}

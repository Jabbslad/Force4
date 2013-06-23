package com.zamma.force4.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public final String FILENAME;
	public final int SIZE;
	
	public int[] pixels;
	
	public static SpriteSheet outdoorGrassy = new SpriteSheet("/tex/outdoor_grassy.png", 512);
	
	public SpriteSheet(String fileName, int size) {
		FILENAME = fileName;
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		
		loadSheet(FILENAME);
	}
	
	public void loadSheet(String fileName) {
		try {
			BufferedImage img = ImageIO.read(SpriteSheet.class.getResource(FILENAME));
			
			int w = img.getWidth();
			int h = img.getHeight();	
			
			img.getRGB(0, 0, w, h, pixels, 0, w);		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

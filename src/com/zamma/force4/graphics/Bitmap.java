package com.zamma.force4.graphics;

public class Bitmap {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	public void draw(Bitmap bitmap, int xOff, int yOff) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPx = y + yOff;
			if (yPx < 0 || yPx >= height) {
				continue;
			}
			
			for (int x = 0; x < bitmap.width; x++) {
				int xPx = x + xOff;
				if (xPx < 0 || xPx >= width) {
					continue;
				}
				
				int src = bitmap.pixels[x + (y * bitmap.width)];
				if (src != 0xffff00ff) {
					pixels[xPx + (yPx * width)] = src;
				}
			}
		}
	}
}

package com.zamma.force4;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.zamma.force4.graphics.Screen;

public class Force4 extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Force4";
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
	
	private boolean running = false;
	private Thread thread;
	
	private JFrame frame;
	private Game game;
	private Screen screen;
	
	private BufferedImage img;
	private int[] pixels;
	
	public Force4() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);	
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		frame = new JFrame(TITLE);
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		
		running = true;
		thread = new Thread(this, "Force4");
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running) {
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int frames = 0;
		int tickCount = 0;
		double unprocessedSeconds = 0;
		double secondsPerTick = 1 / 60.0;
		long lastTime = System.nanoTime();
		
		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;
			
			unprocessedSeconds += passedTime / 1000000000.0;
			
			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				
				tickCount++;
				if (tickCount % 60 == 0) {
					String perfData = "FPS: " + frames + ", Ticks: " + tickCount;
					//System.out.println(perfData);
					if (game.capFrameRate) {
						frame.setTitle(TITLE + "  |  " + perfData + ", FPS Cap: Yes");
					} else {
						frame.setTitle(TITLE + "  |  " + perfData + ", FPS Cap: Yes");
					}
					lastTime += 1000;
					frames = 0;
					tickCount = 0;
				}
			}
			
			if (game.capFrameRate) {
				if (ticked) {
					render();
					frames++;
				}
			} else {
				render();
				frames++;
			}
			
		}
	}
	
	private void tick() {
		game.tick();
	}
	
	private void render() {	
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render(game);
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);	
		g.dispose();
		bs.show();
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Force4 game = new Force4();
	
		game.frame.add(game);
		game.frame.pack();
		game.frame.setTitle(game.TITLE);
		game.frame.setResizable(false);
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setVisible(true);	
		
		game.start();
	}
}

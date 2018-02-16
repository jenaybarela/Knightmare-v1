package main;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameState.GameStateManager;

import java.awt.event.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	// Dimensions
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int SCALE = 1;

	// game thread
	private Thread thread;
	private boolean running;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	private void init() {
		 image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		 g = (Graphics2D) image.getGraphics();
		 running = true;
		 
		 gsm = new GameStateManager();
	}
	
	public void run() {
		 init();
		 
		 //game loop
		 
		 long lastTime = System.nanoTime();
			double nanoSecondConversion = 1000000000.0 / 60;
			double changeInSeconds = 0;
			
			while(running) {
				long now = System.nanoTime();
				
				changeInSeconds += (now -lastTime) / nanoSecondConversion;
				
				while(changeInSeconds >= 1) {
					update();
					changeInSeconds = 0;
				}
				
				draw();
				drawToScreen();
				lastTime = now;
			}
		
	}
	private void update() {
		gsm.update();
	}
	private void draw() {
		gsm.draw(g);
	}
	private void drawToScreen() {
		Graphics g2 =  getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {
		
	}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
}

package entity;

import tileMap.*;
import audio.AudioPlayer;

import javax.imageio.ImageIO;
import javax.management.timer.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Teleport extends MapObject {
	
	private BufferedImage[] sprites;
	
	public Teleport(TileMap tm) {
		super(tm);
		
		width = 60;
		height = 60;
		cwidth = 50;
		cheight = 50;
		
		fallSpeed = 1;
		maxFallSpeed = 4;
		
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
						"/sprites/Portal.gif"
				)
			);
			
			sprites = new BufferedImage[5];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	animation = new Animation();
	animation.setFrames(sprites);
	animation.setDelay(40);
	
	right = true;
	facingRight = true;
	}
	
	private void getNextPosition() {
		
		
		// falling
		if(falling) {
			if(dy > 0) dy += fallSpeed * 0.1;
			else dy += fallSpeed;
					
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
					
			if(dy > maxFallSpeed) dy = maxFallSpeed;
					
	}
}
	
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
	
}

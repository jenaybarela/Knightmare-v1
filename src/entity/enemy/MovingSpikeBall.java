package entity.enemy;

import entity.*;
import tileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MovingSpikeBall extends Enemy{
	
	private BufferedImage[] sprites;
			
	public MovingSpikeBall(TileMap tm) {
		
		super (tm);
		
		moveSpeed = 3;
		maxSpeed = 2.6;
		stopSpeed = 0.4;
		fallSpeed = 0.45;
		maxFallSpeed = 4;
		
		width = 60;
		height = 60;
		cwidth = 50;
		cheight = 50;
	
		health = maxHealth = 2;
		damage = 1;
		
		//load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
						"/sprites/spike ball.gif"
				)
			);
			
			sprites = new BufferedImage[1];
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
		animation.setDelay(100);
		
		right = true;
		facingRight = true;
	
	}

	private void getNextPosition() {
	
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}

}
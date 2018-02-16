package entity.enemy;

import entity.*;
import tileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpikeBall extends Enemy{
	
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1
		};
	
	private static final int IDLE = 0;
			
	public SpikeBall(TileMap tm) {
		super (tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0;
		fallSpeed = 0.2;
		maxFallSpeed = 10;
		
		width = 60;
		height = 60;
		cwidth = 20;
		cheight = 20;
	
		damage = 1;
		
		//load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
						"/sprites/spike ball.gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 1; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i < 1) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
				}
				sprites.add(bi);
				
			}
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	
	}

	private void getNextPosition() {
	
		// falling
			if(falling) {
					
				if(dy > 0) dy += fallSpeed * 0.1;
				else dy += fallSpeed;
				
				if(dy < 0 && !jumping) dy += stopJumpSpeed;
				if(dy > maxFallSpeed) dy = maxFallSpeed;
					
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}

}

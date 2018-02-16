package entity;

import tileMap.*;  //Imports the whole tileMap package
import audio.AudioPlayer; //Imports the audio player class
import entity.enemy.Platform;  //Imports the moving platform calls

import java.util.ArrayList; //Imports the array list for animations
import javax.imageio.ImageIO; //

import java.awt.*; //Imports the whole java.awt
import java.awt.image.BufferedImage; //Imports the Buffered Image allowing the reading of the animations
import java.util.HashMap; //Imports the Hashmap for the sound effects

public class Player extends MapObject {
	
	private ArrayList<Enemy> enemies; //Calls an array list of enemies
	
	// player stuff
	private boolean flinching; //Calls a boolean flinching 
	private long flinchTimer; //Calls a long flinch timer
	private boolean teleporting; //Calls a boolean teleporting
	private boolean checkpointing; //Calls a boolean checkpointing
	

	
	// gliding
	private boolean gliding; //Calls a boolean gliding
	
	// animations
	private ArrayList<BufferedImage[]> sprites; //
	private final int[] numFrames = { //Sets up an buffered image int that reads the amount of frames for each animation action
		2, 9, 1, 1, 1 ,
	};
	
	// animation actions
	private static final int IDLE = 0; //Sets an static int for the idle animation
	private static final int WALKING = 1; //Sets an static int for the walking animation
	private static final int JUMPING = 2; //Sets an static int for the jumping animation
	private static final int FALLING = 3; //Sets an static int for the falling animation
	private static final int GLIDING = 4; //Sets an static int for the gliding animation
	private static final int SLASHING = 5; ////Sets an static int for the slashing animation

	private Platform p; //Calls a 
	
	private HashMap<String, AudioPlayer> sfx; //Calls a
	
	public Player(TileMap tm) { //
		
		super(tm); //
		
		width = 60; //Calls the width of the knight sprite on the sprite sheet
		height = 60; //Calls the height of the knight sprite on the sprite sheet
		cwidth = 20; //Calls the collision width of the knight sprite used to interact with map objects
		cheight = 20; //Calls the collision height of the knight sprite used to interact with map objects
		
		moveSpeed = 3; //Sets the move speed of the knight sprite 
		maxSpeed = 2.6; //Sets the max speed of the knight sprite
		stopSpeed = 0.4; //Sets the stop speed of the knight sprite
		fallSpeed = 0.45; //Sets the fall speed of the knight sprite
		maxFallSpeed = 4; //Sets the max fall speed of the knight sprite
		jumpStart = -12.8; //Sets the starting jump position of the knight sprite
		stopJumpSpeed = 0.3; //Sets the stopping jump speed of the knight
				
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(    //Buffered image reader that reads the sprite sheet
				getClass().getResourceAsStream(           
					"/sprites/knightspritesheet.gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>(); //Sets the array list of buffered images allowing for the sprites
			for(int i = 0; i < 5; i++) {
				
				BufferedImage[] bi =     //Reads in the sprites as a buffered image and sets the number of frames 
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i != SLASHING) {      //Used to read in any animations that do not exceed the set height and width
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {                               //Used for animations that exceed the set height and width of the sprite
						bi[j] = spritesheet.getSubimage(
								j * width * 3,
								i * height,
								width * 3,
								height
						);
					}
					
				}
				
				sprites.add(bi); // 
				
			}
			
		}
		catch(Exception e) {				//Used to catch any errors while reading in the animations
			e.printStackTrace();
		}
		
		animation = new Animation();              //
		currentAction = IDLE;                          //Sets the current action to the idle animation
		animation.setFrames(sprites.get(IDLE));           //Set the number of frames in the idle animation
		animation.setDelay(400);                         //Sets the delay between the frames of the animation
		
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("dead", new AudioPlayer("/SFX/hit sound.mp3"));  
		//sfx.put("walk", new AudioPlayer("/SFX/"));
		
	}
	
	public void init(ArrayList<Enemy> enemies) {  //
			this.enemies = enemies;
	}
	public void setGliding(boolean b) {    //function that sets gliding as a boolean and
		gliding = b;
	}
		public void checkAttack(ArrayList<Enemy> enemies) {
			
			// loop through enemies
			for(int i = 0; i < enemies.size(); i++) {  //Checks all the enemies
				
				Enemy e = enemies.get(i); //Sets number of enemies 
	
				// check enemy collision
				if(intersects(e)) {        //Checks if knight sprite intersects with the enemy
					hit(e.getDamage());     //If enemy and knight intersect enemy hits the knight
				}
				
			}
			
		}
	
	public void setDead() {        //This function sets the players death
		sfx.get("dead").play();
		stop();
	}
	public void setTeleporting(boolean b) { teleporting = b; }  //Function that sets teleporting as a boolean 
	
	public void setCheckpointing(boolean b) { checkpointing = b; } //Function that sets checkingpoint as a boolean 
	
	public void reset() {      //Function used to reset the player and the knight animations
		facingRight = true;
		currentAction = -1;
		stop();
	}
	public void stop() {        //Stops all the knight animations
		left = right = up = down = flinching =
				gliding = jumping = false;
	}
	
	public void slow() {          //Slows the player, increases the fall speed, and decreased the height 
		if(flinching) {				//that the player can jump, if they get hit by a enemy
			maxSpeed = 1;
			moveSpeed = 1;
			jumpStart = -10.8;
			stopJumpSpeed = 0.3;
			fallSpeed = 0.65;
			maxFallSpeed = 4;
		} 
		else {                      //if the player is not flinching sets the movement to normal
			maxSpeed = 3;
			moveSpeed = 2.6;
			jumpStart = -12.8;
			stopJumpSpeed = 0.3;
			fallSpeed = 0.45;
			maxFallSpeed = 4;
		}
	}
	
	public void hit(int damage) {         //Function used when the player gets hit by an enemy
		if(flinching) return;						
		slow();
		flinching = true;
		flinchTimer = System.nanoTime() + 100000000; //Inceases the length of the flinch timer
	}
	private void getNextPosition() {  //Function used to detect the next position the player is heading on the map
		
		// movement
		if(left) {               //Checks if the player is moving left
			dx -= moveSpeed;		//If player is moving left sets the proper move speed
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {				//Checks if player is moving right
			dx += moveSpeed;			//If player is moving left sets the proper move speed
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {							//Checks if player has stopped moving after moving left
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {				//Checks if player has stopped moving after moving right
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// jumping
		if(jumping && !falling) {       //Checks if the player is jumping and not falling
			//sfx.get("jump").play();		//If player is allows player to jump
			dy = jumpStart;					
			falling = true;	
		}
		
		// falling
		if(falling) {		//Checks if player if falling
			
			if(dy > 0 && gliding) dy += fallSpeed * 0.1; //Checks if player is gliding, 
			else dy += fallSpeed;					//if player is gliding slows down movement speed
			
			if(dy > 0) jumping = false;						//If player his a blocked block stops falling
			if(dy < 0 && !jumping) dy += stopJumpSpeed; 	//if player hits block and is not jumping
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;  //Makes sure the fall speed does not exceed the max speed set 
			
		}
		
	}
	
	public void update() {   //Function used to update the player 
		
		// update position
		getNextPosition();    //Used to get the players next position
		checkTileMapCollision();  //Checks if player is colliding with the tile map
		setPosition(xtemp, ytemp);  //Sets the players position, relative to the map
			
		// check done flinching
				if(flinching) { 		//checks if the player is flinching
					long elapsed =
						(System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed > 1000) {
						flinching = false;
					}
				}
		// set animation
		
		if(dy > 0) {		//Checks if the dy is greater than zero
			if(gliding) {		//Checks if the player is gliding
				if(currentAction != GLIDING) {    //Checks if the player current action is not gliding
					currentAction = GLIDING;		//Sets the players animation to gliding
					animation.setFrames(sprites.get(GLIDING));  //Gets the number of frames in the animation
					animation.setDelay(500);	 //Sets the delay between animation frames
					width = 60; 	//Sets the width of the animation
				}
			}
			else if(currentAction != FALLING) {  //Checks if the player current action is not falling
				currentAction = FALLING;       //Sets the players animation to falling
				animation.setFrames(sprites.get(FALLING)); //Gets the number of frames in the animation
				animation.setDelay(200);  			//Sets the delay between animation frames
				width = 60;					//Sets the width of the animation
			}
		}
		else if(dy < 0) { //Checks if the dy is less the zero
			if(currentAction != JUMPING) {	//Checks if the player current action is not jumping
				currentAction = JUMPING; 	//Sets the players animation to jumping
				animation.setFrames(sprites.get(JUMPING)); //Gets the number of frames in the animation
				animation.setDelay(-1);		//Sets the delay between animation frames
				width = 60;				//Sets the width of the animation
			}
		}
		else if(left || right) {          //Checks if the player is facing left or right
			if(currentAction != WALKING) {		//Checks if the player current action is not walking
				currentAction = WALKING;			//Sets the players animation to walking
				animation.setFrames(sprites.get(WALKING)); //Gets the number of frames in the animation
				animation.setDelay(100);			//Sets the delay between animation frames
				width = 60;				//Sets the width of the animation
			}
		}
		else { 								//If the player is not using any of the other animation sets the animation to idle
			if(currentAction != IDLE) {			//Checks if the player current action is not idle
				currentAction = IDLE;			//Sets the players animation to gliding
				animation.setFrames(sprites.get(IDLE)); //Gets the number of frames in the animation
				animation.setDelay(400);  			//Sets the delay between animation frames
				width = 60;				//Sets the width of the animation
			}
		}
		
		animation.update();  //Updates the animations
		
		slow();  //Updates he slow function
		
		// set direction
		if(currentAction != SLASHING) {   //Sets the direction the player is facing
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {  //This function draws the graphics
		
		setMapPosition();		//Sets the map position of the graphics that are being drawn
		
		// draw player
		if(flinching) {   //Draws the player flinching
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(g); //Calls on the draw function in the MapObject class
		
	}
	
}


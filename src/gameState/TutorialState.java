package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.swing.Timer;

import audio.AudioPlayer;
import entity.*;
import tileMap.*;
import entity.enemy.SpikeBall;
import main.GamePanel;


public class TutorialState extends GameState {
	
	private TileMap tileMap; //Calls on the tileMap class to allow the drawing of the tile map
	//Allows the use of the tilemap class in the tutorial state class
	
	private Background bg; //Calls on the background class to allow the drawing of the background

	
	private Player player; //Calls on the player class to allow the the player 
	//to be called on in the tutorial class
	
	private int spawnY; //Makes an int called spawnX
	private int spawnX;	//Makes an int called spawnX
	
	private ArrayList<Enemy> enemies;  //Calls on an array list of the enemy class used to draw the enemies on the map
	
	private HUD hud; //Calls on the HUD class so we can control the HUD in the tutorial state
	
	private AudioPlayer tutMusic;     //Calls on the audio player class to set the music for the level

	
	private Teleport teleport;  //Calls on the teleport class allowing to use the teleport class in this level
	private Checkpoint checkpoint;  //Calls on the checkpoint class allowing to use the checkpoint class in this level
	
	private Color color;  //Calls on java.awt.color to allow us to set the color of the font
	private Font font;  //Calls on java.awt.font to allow for us to set the size and type of font

	private int eventCount = 0;         //Creates an int called eventCount and sets it to sets
	private boolean eventStart;			//Declares a boolean called eventStart 
	private boolean eventFinish;		//Declares a boolean called eventFinish 
	private boolean eventDead;			//Declares a boolean called eventDead 
	
	private HashMap<String, AudioPlayer> sfx; //Uses the java Hashmap and the audio player class for the sound effects of the level
	
	public TutorialState(GameStateManager gsm) {
		this.gsm = gsm; 
		
		init();  //Runs the init function
		
		color = new Color(225, 0, 0);  //Sets the color to red
		font = new Font("Haettenschweiler", Font.PLAIN, 40);  //Sets the font
		
		sfx = new HashMap<String, AudioPlayer>(); //
		sfx.put("checkpoint", new AudioPlayer("/SFX/hit sound.mp3")); //Sets a sound effect for when 
												//the checkpoint has been obtained
	}
	
	public void init() {
		
		tileMap = new TileMap(60);        //Sets the size of the tiles for loading in the tileMap
		tileMap.loadTiles("/tilesets/lava tileset.gif");     //Loads in the tileset used to make the level map
		tileMap.loadMap("/maps/tutorial map.map");       //Loads in the map for level
		
		tileMap.setPosition(0, 0);  //sets the position of the tileMap
		tileMap.setTween(1);
		
		bg = new Background("/background/lava background.jpg", 0.5);     //Sets the background for the map
		
		populateEnemies();     //Runs the populate Enemies function
		
		spawnX = 300;           //Sets the x spawn point for the player
		spawnY = 500;			//Sets the y spawn point for the player
		
		player = new Player(tileMap);         
		player.setPosition(spawnX, spawnY);   //Sets spawn position for the player
		
		hud = new HUD(player);
		
		tutMusic = new AudioPlayer("/music/menu music.mp3");    //Declares the music for the level
		tutMusic.play(); //Plays the music
		tutMusic.loop();  //Loops the music
		

		
		teleport = new Teleport(tileMap);       //Creates the portal on the tile map
		teleport.setPosition(6445, 775);    //Sets the position of the portal
		
		checkpoint = new Checkpoint(tileMap);
		checkpoint.setPosition(3700, 280);    //Sets the checkpoint position
		
		eventStart = true;        //Sets event start to true
		eventStart();       //Runs event start function
		
	}
	
	private void populateEnemies() {         //This function is used to spawn enemy sprites on the level
		enemies = new ArrayList<Enemy>();
		
		SpikeBall s;
		Point[] points = new Point[] {          //Creates a list of enemies, and where to spawn them on the map
			
				new Point(1700, 750),
				new Point(2000, 750),
				new Point(2300, 750),
				
			};
			for(int i = 0; i < points.length; i++) {           //Adds the number of enemies to the maps to the map
				s = new SpikeBall(tileMap);
				s.setPosition(points[i].x, points[i].y); 	//Sets the enemies position 
				enemies.add(s);
			}
		
	}

	public void update() {
		
		//Checks if end of level event should start
		if(teleport.contains(player)) {         //Checks if the portal contains the player
			
			new Score(0);
			eventFinish = true;           //If the portal contains the player sets event finish to true
			
		}
		
		//check if at checkpoint
		if(checkpoint.contains(player)) {        //Checks if the checkpoint contains the player
			
			sfx.get("checkpoint").play();			 //If the checkpoint contains the player plays a sound effect and changes the players spawn 
			spawnY = player.gety();										
			spawnX = player.getx();
		}
		
		
		//player Dead
		if(player.gety() > tileMap.getHeight()) {  //Checks if the players has fallen off of the map
			eventDead = true;     //If player has sets event dead to true
		}
		
		//events
		if(eventStart) eventStart();     //Runs the event start function if eventStart has been set to true
		if(eventDead) eventDead(); 		//Runs the event dead function if event dead has been set to true
		if(eventFinish) eventFinish();   //Runs the event finish function if eventFinish has been set to true
		
		// update player
		player.update();   //Updates the player
		tileMap.setPosition(                            //Sets the tile map position relative to the player
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
				
			);
		//set background
		bg.setPositon(tileMap.getx(), tileMap.gety()); //Sets the background position relative to the tile map
		
		player.checkAttack(enemies);          //Checks player 
		
		for(int i = 0; i < enemies.size(); i++) {  //Updates the amount of enemies
			Enemy e = enemies.get(i);
			e.update();
		}
				
		teleport.update();   //Updates the portal via the teleport class
		
		checkpoint.update();    //Updates the checkpoint via the checkpoint class
			
	}
	
	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);    
		
		//draw on screen text if player x is lower or higher than set x points on the map
		if(player.getx() < 1000)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Welcome to Your Knightmare", 770, 200);
		g.drawString("Use A and D to move", 820, 300);
		}
		if(player.getx() < 2400 && player.getx() > 1000)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("If you get hit by obstacles you will be slowed", 600, 200);
		}
		if(player.getx() < 3400 && player.getx() > 2400)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Press W to jump", 825, 200);
		g.drawString("Hold W to jump higher", 800, 300);
		}
		if(player.getx() < 3925 && player.getx() > 3400)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("The flag means a checkpoint", 790, 200);
		g.drawString("To obtain the checkpoint run through it", 700, 600);
		}
		if(player.getx() < 4800 && player.getx() > 3925)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Watch out for fake platforms", 780, 200);
		g.drawString("Use space to glide through the air", 750, 600);
		}
		if(player.getx() < 5900 && player.getx() > 4800)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Watch out for invisible blocks", 800, 200);
		g.drawString("Try to find the patterns for difficult levels", 700, 300);
		}
		if(player.getx() < 6500 && player.getx() > 5900)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Stand in the teleporter to end the level", 680, 200);
		g.drawString("Try to get the fastest time", 740, 300);
		}
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++) { //Draws the enemies
			enemies.get(i).draw(g);
		}
		
		//draws the portal
		teleport.draw(g);
		
		//draw the Heads Up Display
		hud.draw(g);
		
		//draw checkpoint
		checkpoint.draw(g);
	}
	
	// level started
			private void eventStart() {    //This function is used to start the level
				eventCount++;			//Adds to the event count as the level continues
				if(eventCount == 10) {        //Resets the event count when it becomes 10
					eventStart = false;  //Sets event start to false
					eventCount = 0; 
				
				}
			}
			
	//player death  
	private void eventDead() {  //Function is used to control the players death 

		eventCount++; //Adds to the event count
		if(eventCount >= 0) {  //If the event count is greater than or equal to 0 
				eventDead = false;   //while running the event dead resets the level and the event count
				eventCount = 0;
				reset();
			}
	}
	
	// reset level
	private void reset() {   //Function is used to reset the level after player death
		player.reset();  //Resets the player
		player.setPosition(spawnX, spawnY); //Sets the players position to latest checkpoint
		populateEnemies(); //Runs the populate enemies function
		//blockInput = true;          //Resets the event count
		eventCount = 0;              //and starts the event start function
		eventStart = true;
		eventStart();
	}

	// finish level
		private void eventFinish() { //This function is used to finish the level
			eventCount++;                               
			if(eventCount == 1) {                     //If the event count is equal to one 
				player.setTeleporting(true);		//Sets the player teleporting to true
				player.stop();						//Stops all player animation
			}
			if(eventCount == 10) {                //if event count is equal to 10 
				tutMusic.stop();				//Stop the tutorial level music
				gsm.setState(GameStateManager.LEVEL1STATE);      //Sets the gamestate to level 1 state
				
			}
			
		}
	
	public void keyPressed(int k) {                              //Function used to detect if a key is pressed
		if( k == KeyEvent.VK_A) player.setLeft(true);     //If player pressing A moves left
		if( k == KeyEvent.VK_D) player.setRight(true);			//If player pressing D moves right 
		if( k == KeyEvent.VK_W) player.setJumping(true);		//If player pressing W jumps
		if( k == KeyEvent.VK_SPACE) player.setGliding(true);
		if( k == KeyEvent.VK_ESCAPE) {      //If player presses escape pauses timer
			gsm.setPaused(true);			//Sets the set paused state to true
			hud.pauseTim();	
		};
		if (k == KeyEvent.VK_F1) {				//If player pressing F1 returns to game
			gsm.setPaused(false);
		}
		
		if (k == KeyEvent.VK_F2) {					//If player pressing F2 sets gamestate to the menu
			gsm.setPaused(false);
			tutMusic.stop();						//Stops the tutorial music
			gsm.setState(GameStateManager.MENUSTATE); 
		}
		if (k == KeyEvent.VK_F3) {
			System.exit(0);  //If player pressing F3 exits the game
		}
	
	}
	
	public void keyReleased(int k) {
		if( k == KeyEvent.VK_A) player.setLeft(false);  //If player releases A stops moving left
		if( k == KeyEvent.VK_D) player.setRight(false);  //If player releases D stops moving right
		if( k == KeyEvent.VK_W) player.setJumping(false);  //If player releases W stops jumping
		if( k == KeyEvent.VK_SPACE) player.setGliding(false);  //If player releases space stops gliding

	}

}

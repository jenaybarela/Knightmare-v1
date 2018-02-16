package gameState;

import tileMap.*;
import entity.*;
import entity.enemy.Platform;
import entity.enemy.SpikeBall;
import main.GamePanel;
import audio.AudioPlayer;
import gameState.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

public class Level1State extends GameState {
		
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private int spawnY;
	private int spawnX;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private long start;
	
	private AudioPlayer lvl1Music;
	private Timer timer;
	
	private Teleport teleport;
	private Checkpoint checkpoint;
	
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventFinish;
	private boolean eventDead;
	
	private HashMap<String, AudioPlayer> sfx;
	
	public Level1State(GameStateManager gsm) {
		start = 0;
		this.gsm = gsm;
		init(); //Runs init
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("checkpoint", new AudioPlayer("/SFX/hit sound.mp3")); //Sets a sound effect for when 
											//the checkpoint has been obtained
	}
	
	public void init() {
		
		tileMap = new TileMap(60);	//Sets the size of the tiles for loading in the tileMap
		tileMap.loadTiles("/tilesets/mountain tileset.gif");	//Loads in the tileset used to make the level map
		tileMap.loadMap("/maps/level1 map.map");	//Loads in the map for level
		
		tileMap.setPosition(0, 0);  //sets the position of the tileMap
		tileMap.setTween(1);
		
		bg = new Background("/background/mountain background.jpg", 0.5);	//Sets the background for the map
		
		populateEnemies();     //Runs the populate Enemies function
		
		spawnX = 100;           //Sets the x spawn point for the player
		spawnY = 100;			//Sets the y spawn point for the player
		
		player = new Player(tileMap);
		player.setPosition(spawnX, spawnY); 	//Sets spawn position for the player
		
		hud = new HUD(player);
		
		lvl1Music = new AudioPlayer("/music/menu music.mp3");
		lvl1Music.play(); //Plays music
		lvl1Music.loop(); //Loops music
		
		//start();
		//timer.start();
		
		teleport = new Teleport(tileMap);	//Creates the portal on the tile map
		teleport.setPosition(4210, 100);	//Sets the position of the portal
		
		checkpoint = new Checkpoint(tileMap);
		checkpoint.setPosition(3510, 929);	//Sets the checkpoint position
		
		eventStart = true;	 //Sets event start to true
		eventStart();	//Runs event start function
		
	}
	
	public long start() {
		return start;
		//timer = new Timer(2000, this);
	}
	public Timer time() {
		return timer;
	}
	
	private void populateEnemies() {	//This function is used to spawn enemy sprites on the level
		enemies = new ArrayList<Enemy>();
		
		SpikeBall s;
		Point[] points = new Point[] {	//Creates a list of enemies, and where to spawn them on the map
			
				new Point(2845, 669),
				new Point(2870, 669),
				new Point(2665, 762),
				new Point(2690, 762),
				new Point(2775, 1021),
				new Point(2810, 1021)
				
			};
			for(int i = 0; i < points.length; i++) {	//Adds the number of enemies to the maps to the map
				s = new SpikeBall(tileMap);
				s.setPosition(points[i].x, points[i].y);	//Sets the enemies position
				enemies.add(s);
			}
		
		Platform p;
		
		Point[] points1 = new Point[] {		//Creates a list of enemies, and where to spawn them on the map
			
			new Point(1740, 750),

		};
		for(int i = 0; i < points1.length; i++) {			//Adds the number of enemies to the maps to the map
			p = new Platform(tileMap);
			p.setPosition(points1[i].x, points1[i].y);		//Sets the enemies position
			enemies.add(p);
		}
		
	}

	public void update() {
		
		///Checks if end of level event should start
		if(teleport.contains(player)) {         //Checks if the portal contains the player
			
			new Score(1);
			eventFinish = blockInput = true;           //If the portal contains the player sets event finish to true
			
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
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++) { //Draws enemies
			enemies.get(i).draw(g);
		}
		
		//teleport
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
			eventCount = 0;            //Resets the event count  
			eventStart = true;	//and starts the event start function
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
					lvl1Music.stop();				//Stop the tutorial level music
					gsm.setState(GameStateManager.LEVEL1STATE);      //Sets the gamestate to level 1 state
					
				}
				
			}
		
		public void keyPressed(int k) {                     //Function used to detect if a key is pressed
		if( k == KeyEvent.VK_A) player.setLeft(true);     //If player pressing A moves left
		if( k == KeyEvent.VK_D) player.setRight(true);			//If player pressing D moves right 
		if( k == KeyEvent.VK_W) player.setJumping(true);		//If player pressing W jumps
		if( k == KeyEvent.VK_SPACE) player.setGliding(true);
		if( k == KeyEvent.VK_ESCAPE) {      //If player presses escape pauses timer
			gsm.setPaused(true);		//Sets the set paused state to true
			hud.pauseTim();	
		};
		if (k == KeyEvent.VK_F1) {		//If player pressing F1 returns to game
			gsm.setPaused(false);
		}
		
		if (k == KeyEvent.VK_F2) {			//If player pressing F2 sets gamestate to the menu
			gsm.setPaused(false);
			lvl1Music.stop();				//Stops the tutorial music
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

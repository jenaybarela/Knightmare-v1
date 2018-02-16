package gameState;
import tileMap.*;
import entity.*;
import main.GamePanel;
import audio.AudioPlayer;
import gameState.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

public class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private int spawnY;
	private int spawnX;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private long start;
	
	private AudioPlayer lvl2Music;
	private Timer timer;
	
	private Teleport teleport;
	private Checkpoint checkpoint;
	private Checkpoint checkpoint1;
	
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventFinish;
	private boolean eventDead;
	
	private HashMap<String, AudioPlayer> sfx;
	
	public Level2State(GameStateManager gsm) {
		start = 0;
		this.gsm = gsm;
		init();
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("checkpoint", new AudioPlayer("/SFX/hit sound.mp3"));
	}
	
	public void init() {
		
		tileMap = new TileMap(60);
		tileMap.loadTiles("/tilesets/mountain tileset.gif");
		tileMap.loadMap("/maps/level2 map.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/background/mountain background.jpg", 0.5);
		
		populateEnemies();
		
		spawnX = 100;
		spawnY = 100;
		
		player = new Player(tileMap);
		player.setPosition(spawnX, spawnY);
		
		hud = new HUD(player);
		
		lvl2Music = new AudioPlayer("/music/menu music.mp3");
		lvl2Music.play();
		lvl2Music.loop();
		
		//start();
		//timer.start();
		
		teleport = new Teleport(tileMap);
		teleport.setPosition(3270, 927);
		
		checkpoint = new Checkpoint(tileMap);
		checkpoint.setPosition(1763, 453);
		checkpoint1 = new Checkpoint(tileMap);
		checkpoint1.setPosition(2980, 70);
		
		eventStart = true;
		eventStart();
		
	}
	
	public long start() {
		return start;
		//timer = new Timer(2000, this);
	}
	public Timer time() {
		return timer;
	}
	
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
				
	}
	
	public void update() {
		
		//Checks if end of level event should start
		if(teleport.contains(player)) {
			eventFinish = blockInput = true;
		}
		
		//check if at checkpoint
		if(checkpoint.contains(player)) {
			sfx.get("checkpoint").play();
			spawnY = player.gety();
			spawnX = player.getx();
		}
		
		if(checkpoint1.contains(player)) {
			sfx.get("checkpoint").play();
			spawnY = player.gety();
			spawnX = player.getx();
		}
		
		//player Dead
		if(player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		//events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		// update player
		player.update();
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
				
			);
		//set background
		bg.setPositon(tileMap.getx(), tileMap.gety());
		
		player.checkAttack(enemies);
		//player.checkplat(enemies);
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
		start =+ 1;
		
		teleport.update();
		
		checkpoint.update();
		checkpoint1.update();
			
	}
	
	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//teleport
		teleport.draw(g);
		
		//draw the Heads Up Display
		hud.draw(g);
		
		//draw checkpoint
		checkpoint.draw(g);
		checkpoint1.draw(g);
		
	}
	
	// level started
			private void eventStart() {
				eventCount++;
				
				if(eventCount == 10) {
					eventStart = blockInput = false;
					eventCount = 0;
				}
			}
			
	//player death
	private void eventDead() {

		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount >= 0) {
				eventDead = blockInput = false;
				eventCount = 0;
				reset();
			}
	}
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(spawnX, spawnY);
		blockInput = true;
		eventCount = 0;
		eventStart = true;
		eventStart();
	}

	// finish level
		private void eventFinish() {
			eventCount++;
			if(eventCount == 1) {
				player.setTeleporting(true);
				player.stop();
			}
			if(eventCount == 10) {
				lvl2Music.stop();
				gsm.setState(GameStateManager.LEVEL3STATE);
				
			}
			
		}
	
	public void keyPressed(int k) {
		if( k == KeyEvent.VK_A) player.setLeft(true);
		if( k == KeyEvent.VK_D) player.setRight(true);
		if( k == KeyEvent.VK_W) player.setJumping(true);
		if( k == KeyEvent.VK_SPACE) player.setGliding(true);
		if( k == KeyEvent.VK_ESCAPE) {
			gsm.setPaused(true);
			hud.pauseTim();	
		};
		if (k == KeyEvent.VK_F1) {
			gsm.setPaused(false);
		}
		if (k == KeyEvent.VK_F2) {
			gsm.setPaused(false);
			lvl2Music.stop();
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if (k == KeyEvent.VK_F3) {
			System.exit(0);
		}
	
	}
	
	public void keyReleased(int k) {
		if( k == KeyEvent.VK_A) player.setLeft(false);
		if( k == KeyEvent.VK_D) player.setRight(false);
		if( k == KeyEvent.VK_W) player.setJumping(false);
		if( k == KeyEvent.VK_SPACE) player.setGliding(false);

	}
	
}
package gameState;

import tileMap.*;
import entity.*;
import entity.Teleport;
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

public class Level3State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private int spawnY;
	private int spawnX;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private long start;
	
	private AudioPlayer lvl3Music;
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
	
	public Level3State(GameStateManager gsm) {
		start = 0;
		this.gsm = gsm;
		init();
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("checkpoint", new AudioPlayer("/SFX/hit sound.mp3"));
	}
	
	public void init() {
		
		tileMap = new TileMap(60);
		tileMap.loadTiles("/tilesets/lava tileset.gif");
		tileMap.loadMap("/maps/level3 map.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/background/lava background.jpg", 0.5);
		
		populateEnemies();
		
		spawnX = 100;
		spawnY = 500;
		
		player = new Player(tileMap);
		player.setPosition(spawnX, spawnY);
		
		hud = new HUD(player);
		
		lvl3Music = new AudioPlayer("/music/menu music.mp3");
		lvl3Music.play();
		lvl3Music.loop();
		
		//start();
		//timer.start();
		
		teleport = new Teleport(tileMap);
		teleport.setPosition(8000, 830);
		
		checkpoint = new Checkpoint(tileMap);
		checkpoint.setPosition(6027, 213);
		checkpoint1 = new Checkpoint(tileMap);
		checkpoint1.setPosition(4256, 880);
		
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
		
		SpikeBall s;
		Point[] points = new Point[] {
			
				new Point(620, 498),
				new Point(2400, 292),
				new Point(4300, 362),
				new Point(5550, 523),
				new Point(6092, 205),
				new Point(6870, 101),
				new Point(7863, 820)
				
			};
			for(int i = 0; i < points.length; i++) {
				s = new SpikeBall(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);
			}
		
		Platform p;
		
		Point[] points1 = new Point[] {
			
			//new Point(1060, 200),
			new Point(1100, 510),
			new Point(2400, 510),
			new Point(2300, 750),
			new Point(2100, 630)
		};
		for(int i = 0; i < points1.length; i++) {
			p = new Platform(tileMap);
			p.setPosition(points1[i].x, points1[i].y);
			enemies.add(p);
		}
		
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
					//System.out.println("xDDD");
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
		populateEnemies();
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
				lvl3Music.stop();
				gsm.setState(GameStateManager.MENUSTATE);
				
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
			lvl3Music.stop();
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

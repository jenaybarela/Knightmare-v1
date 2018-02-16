package gameState;

import tileMap.*;
import entity.*;
import entity.enemy.*;
import main.GamePanel;
import audio.AudioPlayer;
import gameState.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

public class FinalLevelState extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private int spawnY;
	private int spawnX;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private long start;
	
	private AudioPlayer flvlMusic;
	private Timer timer;
	
	private Color color;
	private Font font;
	private Font finalFont;
	
	private Teleport teleport;
	private Checkpoint checkpoint;
	private Checkpoint checkpoint1;
	private Checkpoint checkpoint2;
	
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventFinish;
	private boolean eventDead;
	
	private HashMap<String, AudioPlayer> sfx;
	
	public FinalLevelState(GameStateManager gsm) {
		start = 0;
		this.gsm = gsm;
		init();
		
		color = new Color(225, 0, 0);
		font = new Font("Haettenschweiler", Font.PLAIN, 40);
		finalFont = new Font("Haettenschweiler", Font.PLAIN, 140);
		
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("checkpoint", new AudioPlayer("/SFX/hit sound.mp3"));
		
	}
	
	public void init() {
		
		tileMap = new TileMap(60);
		tileMap.loadTiles("/tilesets/final level tileset.gif");
		tileMap.loadMap("/maps/finallevel map.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/background/final level.png", 0.5);
		bg.setPositon(1800, 12000);
		
		populateEnemies();
	
		spawnX = 1890;
		spawnY = 3443;
		
		player = new Player(tileMap);
		player.setPosition(spawnX, spawnY);
		
		hud = new HUD(player);
		
		flvlMusic = new AudioPlayer("/music/menu music.mp3");
		flvlMusic.play();
		flvlMusic.loop();
		
		//start();
		//timer.start();
		
		teleport = new Teleport(tileMap);
		teleport.setPosition(1860, 57);
		
		checkpoint = new Checkpoint(tileMap);
		checkpoint.setPosition(65, 9850);
		checkpoint1 = new Checkpoint(tileMap);
		checkpoint1.setPosition(57, 7085);
		checkpoint2 = new Checkpoint(tileMap);
		checkpoint2.setPosition(1890, 3443);
		
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
				
				new Point(1230, 9436),
				new Point(1465, 9314),
				new Point(1603, 9198),
				new Point(1820, 9191),
				new Point(992, 8824),
				new Point(625, 8634),
				new Point(272, 8472),
				new Point(56, 8409),
				
			};
			for(int i = 0; i < points.length; i++) {
				s = new SpikeBall(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);
			}	
	}
	
//	private void populateMovingEnemies() {
//		
//		enemies = new ArrayList<Enemy>();
//		
//		if(player.getx() > 800 && player.gety() > 9200) {
//			MovingSpikeBall m;
//			Point[] points1 = new Point[] {
//				
//					new Point(1230, 9436),
//				};
//				for(int i = 0; i < points1.length; i++) {
//					m = new MovingSpikeBall(tileMap);
//					m.setPosition(points1[i].x, points1[i].y);
//					enemies.add(m);
//				}	
//		}
//	}

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
		
		if(checkpoint2.contains(player)) {
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
		//populateMovingEnemies();
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
		
		//Updates the checkpoints
		checkpoint.update();
		checkpoint1.update();
		checkpoint2.update();
			
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
		checkpoint2.draw(g);
		
		if(player.getx() > 1500 && player.gety() > 11500)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Press T to respawn at lastest checkpoint", 900, 200);
		g.drawString("You can fall out of the sides of the map be careful", 100, 900);
		}
		if(player.gety() < 140 && player.getx() < 1800)
		{
		g.setColor(color);
		g.setFont(font);
		g.drawString("Congratulations", 900, 100);;
		}
		if(player.gety() < 140 && player.getx() > 1830)
		{
		g.setColor(Color.BLACK);
		g.fillRect(0, 140, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(color);
		g.setFont(finalFont);
		g.drawString("The Knightmare Continues", 125, 600);
		}
		
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
				flvlMusic.stop();
				gsm.setState(GameStateManager.TUTORIALSTATE);
				
			}
			
		}
	
	public void keyPressed(int k) {
		if( k == KeyEvent.VK_A) player.setLeft(true);
		if( k == KeyEvent.VK_D) player.setRight(true);
		if( k == KeyEvent.VK_W) player.setJumping(true);
		if( k == KeyEvent.VK_SPACE) player.setGliding(true);
		if( k == KeyEvent.VK_T) {
			eventDead = true;
		}
		if( k == KeyEvent.VK_ESCAPE) {
			gsm.setPaused(true);
			hud.pauseTim();	
		};
		if (k == KeyEvent.VK_F1) {
			gsm.setPaused(false);
		}
		if (k == KeyEvent.VK_F2) {
			gsm.setPaused(false);
			flvlMusic.stop();
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
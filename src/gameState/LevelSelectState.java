package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import main.GamePanel;
import tileMap.Background;
import entity.*;

public class LevelSelectState extends GameState {
	
private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
			"Level 1",
			"Level 2",
			"Level 3",
			"Level 4",
			"Level 5",
			"Level 6 "
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	private AudioPlayer selectMusic;
	
	public LevelSelectState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/background/menubackground.png", .5);
			//bg.setVector(-1, 0);
			
			selectMusic = new AudioPlayer("/music/level 1 music.mp3");
			selectMusic.play();
			selectMusic.loop();
			
			titleColor = new Color(225, 0, 0);
			titleFont = new Font ("Impact", Font.PLAIN, 82);
			
			font = new Font("Haettenschweiler", Font.PLAIN, 52);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	public void draw(Graphics2D g) {
		bg.draw(g);
		//int centerHorT = (int) (GamePanel.WIDTH	/ 2);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Level Select", 790, 300);
		g.setFont(font);
		
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 900, 400 + i * 65);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			selectMusic.stop();
			gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		if(currentChoice == 1) {
			selectMusic.stop();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 2) {
			
			selectMusic.stop();
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		if(currentChoice == 3) {
			selectMusic.stop();
			gsm.setState(GameStateManager.LEVEL3STATE);
		}
		if(currentChoice == 4) {
			selectMusic.stop();
			gsm.setState(GameStateManager.LEVEL4STATE);
		}
		if(currentChoice == 5) {
			selectMusic.stop();
			gsm.setState(GameStateManager.FINALLEVELSTATE);
		}
	}
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			selectMusic.stop();
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}

}

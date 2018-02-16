package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import audio.AudioPlayer;
import main.GamePanel;
import tileMap.Background;
public class MenuState extends GameState {

	private AudioPlayer menuMusic;
	private Background bg;
	
	
	private int currentChoice = 0;
	private String[] options = {
			"New Game",
			"Level Select",
			"High Score",
			"Help",
			"Credits",
			"Quit"
	};
	private Color titleColor;
	private Font titleFont;
	
	private Font font;

	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/background/menubackground.png", .5);
			//bg.setVector(-1, 0);
			
			menuMusic = new AudioPlayer("/music/level 1 music.mp3");
			menuMusic.play();
			menuMusic.loop();
			
			titleColor =(Color.LIGHT_GRAY);
			titleFont = new Font ("Haettenschweiler", Font.PLAIN, 90);
			
			font = new Font("Haettenschweiler", Font.PLAIN, 46);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		
	}
	public void update() {
		
		bg.update();
		
	}
	public void draw(Graphics2D g) {
		
			bg.draw(g);
			//int centerHorT = (int) (GamePanel.WIDTH	/ 2);
			
			g.setColor(titleColor);
			g.setFont(titleFont);
			g.drawString("Knightmare", 825, 300);
			
			g.setFont(font);
			for(int i = 0; i < options.length; i++) {
				if(i == currentChoice) {
					g.setColor(Color.RED);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.drawString(options[i], 900, 400 + i * 65);
			}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			menuMusic.stop();
			gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		if(currentChoice == 1) {
			
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
			menuMusic.stop();
	
		}
		if(currentChoice == 2) {
			gsm.setHighScore(true);
		}
		if(currentChoice == 3) {
			gsm.setHelped(true);
		}
		if(currentChoice == 4) {
			gsm.setState(GameStateManager.CREDITSTATE);
		}
		if(currentChoice == 5) {
			menuMusic.stop();
			System.exit(0);
		}
	}
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setHelped(false);
			gsm.setHighScore(false);
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

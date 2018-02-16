package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import tileMap.Background;

public class HighScoreState {
	
	private Background bg;
	
	private String file;
	private String line;
	private long score; 
	
	BufferedReader br;
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public HighScoreState(GameStateManager gsm) {
		try {
			bg = new Background("/background/menubackground.png", .5);
			
			
			titleColor =(Color.LIGHT_GRAY);
			titleFont = new Font ("Monotype Corsiva", Font.PLAIN, 80);
			
			font = new Font("Haettenschweiler", Font.PLAIN, 40);
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
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("High Scores", 810, 300);
		
		file = "HighScore.txt";
		
		try {
			
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			
		} 
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		for(int i = 1; i < 7; i++) {
			
			try {
				
				line = br.readLine();
				score = Long.parseLong(line);
			} 
			catch (IOException e) {
				
				e.printStackTrace();
			}
			
			g.drawString("level "+i+ ":"+ score, 900, 400 + i * 65);
			
			
		}
		
	}
	public void keyPressed(int k) {}
	
	public void keyReleased(int k) {}
	
	
}

package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;
import audio.AudioPlayer;
import tileMap.Background;
import gameState.GameState;
import gameState.Level3State;
import entity.MapObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PauseState extends GameState {

	//Fonts
	private Font OptionFont;
	private Font TitleFont;
	
	//Background
	private Background bg;
	
	public PauseState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		bg = new Background("/background/pause background.png", 0.5);
		
		//Fonts
		OptionFont = new Font("Haettenschweiler", Font.PLAIN, 40);
		TitleFont = new Font("Impact", Font.PLAIN, 80);
				
	}
	
	
	public void init() {}
	
	public void update() {
		
		//Update Background
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		//Draw Background
		bg.draw(g);
		
		//Draw title
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(TitleFont);
		g.drawString("Game Paused", 800, 375);
		
		//Draw Options
		g.setFont(OptionFont);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Return (F1)", 925, 450);
		g.drawString("Exit to Title Screen (F2)", 825, 525);
		g.drawString("Exit to Game (F3)", 875, 600);
		
	}
	
	public void keyPressed(int k) {}
	
	public void keyReleased(int k) {}

}

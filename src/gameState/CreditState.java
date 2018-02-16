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
public class CreditState extends GameState {
	
	private Color color;
	private Font titleFont;
	private Font font;

	public CreditState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {			
			
			color =(Color.WHITE);
			titleFont = new Font("Haettenschweiler", Font.PLAIN, 90);
			font = new Font("Haettenschweiler", Font.PLAIN, 46);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		
	}
	public void update() {
		
	}
	public void draw(Graphics2D g) {
		
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(color);
			g.setFont(titleFont);
			
			g.drawString("Publisher: Cognitive Thought Media", 250, 200);	
			g.setFont(font);
			
			g.drawString("Programmers", 125, 400);	
			g.drawString("Zakary Willis", 125, 475);	
			g.drawString("Jenay Barela", 125, 550);	
			
			g.drawString("Graphics", 700, 400);	
			g.drawString("Eben Bellas", 680, 475);	
			
			g.drawString("Special Thanks", 1300, 400);
			g.drawString("Johannes Sjolund: Knight sprite", 1100, 475);
			g.drawString("ForeignGuyMike: TileMap Editor", 1100, 550);
			
			g.drawString("Press Enter to Continue", 1400, 1000);
		
	}
	
	public void keyPressed(int k) {
	
	if (k == KeyEvent.VK_ENTER) { 
		gsm.setState(GameStateManager.MENUSTATE); 
		}
	}
			
	public void keyReleased(int k) {}
}
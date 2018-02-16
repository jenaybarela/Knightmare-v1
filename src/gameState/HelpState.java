package gameState;

import java.awt.*;

import tileMap.Background;

public class HelpState extends GameState {
	
	private Background bg;
	
	public HelpState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		bg = new Background("/background/help screen.png", 0.5);
		
	}
	
	public void init() {}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
	}
	
	public void update() {
		bg.update();
	}
	public void keyPressed(int k) {}
	
	public void keyReleased(int k) {}

}

package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {

	private BufferedImage loadImage;
	private BufferedImage formatImage;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, double ms) {
		try {
			loadImage = ImageIO.read(getClass().getResource(s));
			formatImage = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formatImage.getGraphics().drawImage(loadImage, 0, 0, null);
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setPositon(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public void update() {
		x += dx;
		y += dy;
	}
	public void draw(Graphics2D g) {
		g.drawImage(formatImage, (int)x, (int)y, null);
		if(x < 0) {
			g.drawImage(formatImage, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0) {
			g.drawImage(formatImage, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}

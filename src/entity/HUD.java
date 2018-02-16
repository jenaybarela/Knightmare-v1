package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;

	private BufferedImage image;
	private Font font;
	private long baseTime = System.nanoTime();
	public static long tim;
	private long ptim;
	
	public HUD(Player p) {
		player = p;
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					""
				)
			);
			font = new Font("Arial", Font.PLAIN, 20);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public long time() {
		
		tim = ((System.nanoTime() - baseTime) + ptim) / 1000000000;
		return tim;
		
	}
	public void pauseTim() {
		
			ptim = (System.nanoTime() - baseTime) / 1000000000; 
			baseTime = System.nanoTime();
			System.out.println(ptim + "     ");
			return;
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 20, null);
		g.setFont(font);
		g.setColor(Color.RED);
//		g.drawString(
//			player.getPoints() +"",
//			30,
//			20
//		);

		g.drawString(
			time() +"",
			30,
			45
		);
	}
	
}

package main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Way to Cathea");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.pack();
		window.setVisible(true);
		
	}
}

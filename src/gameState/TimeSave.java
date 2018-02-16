package gameState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entity.Enemy;

public class TimeSave extends GameStateManager{
	
	private GameStateManager gsm;
	
	private BufferedReader br;
	private FileReader fr;
	
	
	private int level;
	private String line;
	
	
//	public Save() {
//		
//		 
//		String line = null;
//
//    try {
//        // FileReader reads text files in the default encoding.
//    	for(int i = 0; i < saves.length; i++) {
//    		
//            FileReader fileReader = 
//                new FileReader(saves[i]);
//
//            // Always wrap FileReader in BufferedReader.
//            BufferedReader bufferedReader = 
//                new BufferedReader(fileReader);
//
//            while((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }   
//
//        // Always close files.
//    	}        
//    }
//    catch(FileNotFoundException ex) {
//        System.out.println(
//            "Unable to open file '" + 
//            file + "'");  
//        ex.printStackTrace();
//        
//    }
//    catch(IOException ex) {
//        System.out.println(
//            "Error reading file '" 
//            + file + "'");                  
//        // Or we could just do this: 
//        // ex.printStackTrace();
//    }
//		
//	}
	public TimeSave(String file) {

		
		line = null;

		
		try {
			
			FileReader fileReader = 
				new FileReader(file);
	
        	BufferedReader br = 
	                new BufferedReader(fileReader);

			line = br.readLine();
			level = Integer.parseInt(line);
			
			
		} 
		catch (IOException e) {
			
			System.out.println(
	                "Unable to open file '" + 
	                file + "'");  
			e.printStackTrace();
		}
		
		if(level == 0) {
			gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 1) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		else if(level == 2) {
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
		}
		else if(level == 3) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 4) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 5) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		
	}

//        try {
//
//        	for(int i = 0; i < saves.length; i++) {
//        		
//	            FileReader fileReader = 
//	                new FileReader(file);
//	
//	            BufferedReader br = 
//	                new BufferedReader(fileReader);
//
//        	}        
//        }
//        catch(FileNotFoundException ex) {
//        	
//            System.out.println(
//                "Unable to open file '" + 
//                file + "'");  
//            
//        }
//        catch(IOException ex) {
//        	
//            System.out.println(
//                "Error reading file '" 
//                + file + "'");                  
//
//        }
//    }
	
	public void getSave(String file) {
		
		String line = null;
		int level = Integer.parseInt(line);
		
		try {
			
			FileReader fileReader = 
				new FileReader(file);
	
        	BufferedReader br = 
	                new BufferedReader(fileReader);

			line = br.readLine();
			
			
		} 
		catch (IOException e) {
			
			System.out.println(
	                "Unable to open file '" + 
	                file + "'");  
			e.printStackTrace();
		}
		
		if(level == 0) {
			gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 1) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		else if(level == 2) {
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
		}
		else if(level == 3) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 4) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		else if(level == 5) {
			//gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		
	}


		
}
	
	


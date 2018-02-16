package gameState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entity.HUD;

public class Score {
	private BufferedReader br;
	private FileReader fr;
	
	private HUD hud;
	
	private String file;
	private String line;
	private String gup;
	private long sco;
	private long gh;


	public Score(int level) {
//		gh = gettime();
//		gup = Long.toString(gh);
//		
//		try
//        {
//                BufferedReader file = new BufferedReader(new FileReader("HighScore.txt"));
//                String line;
//                String input = "";
//                while ((line = file.readLine()) != null) 
//                {
//                	sco = Long.parseLong(line);
//                    //System.out.println(line);
//                    if (sco > gh)
//                    {
//                        line = "";
//                        System.out.println("Line deleted.");
//                    }
//                    input += line + '\n';
//                }
//                FileOutputStream File = new FileOutputStream("HighScore.txt");
//                File.write(input.getBytes());
//                File.write((int)gh);
//                file.close();
//                File.close();
//        }
//        catch (Exception e)
//        {
//                System.out.println("Problem reading file.");
//        }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		file = "HighScore.txt";
		gh = gettime();
		gup = Long.toString(gh);
	    try {
			fr = new FileReader(file);
		
		    br = new BufferedReader(fr);
	
		    for(int i = 0; i < 6; i++) {
		    	if(level == i) {
		    		
		    		line = br.readLine();
		    		sco = Long.parseLong(line);
		    		if(gh < sco) {
		    			ChangeLineInFile changeFile = new ChangeLineInFile();
		    		    changeFile.changeALineInATextFile(file, gup, level);
		    		    System.out.print("j");
		    		    return;
		    		}
		    		else {
		    			break;
		    		}
		    		
		    	}
		    	
		    }
		    return;
	    } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
            		
            	
	public long gettime() {
		return HUD.tim;
		
	}


		
}
	
	


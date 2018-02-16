package gameState;


public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	public boolean paused;
	
	private HelpState helpState;
	public boolean helped;
	
	private HighScoreState highscoreState;
	public boolean highscore;
	
	public static final int NUMGAMESTATES = 10;
	public static final int CREDITSTATE = 0;
	public static final int MENUSTATE = 1;
	public static final int LEVELSELECTSTATE = 2;
	public static final int LOADSTATE = 3;
	public static final int TUTORIALSTATE = 4;
	public static final int LEVEL1STATE = 5;
	public static final int LEVEL2STATE = 6;
	public static final int LEVEL3STATE = 7;
	public static final int LEVEL4STATE = 8;
	public static final int FINALLEVELSTATE = 9;
	
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		helpState = new HelpState(this);
		helped = false;
		
		highscoreState = new HighScoreState(this);
		highscore = false;
		
		currentState = CREDITSTATE;
		loadState(currentState);
	}
	
	private void loadState (int state) {
		if(state == CREDITSTATE)
			gameStates[state] = new CreditState(this);
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVELSELECTSTATE)
			gameStates[state] = new LevelSelectState(this);
		if(state == TUTORIALSTATE)
			gameStates[state] = new TutorialState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		if(state == LEVEL4STATE)
			gameStates[state] = new Level4State(this);
		if(state == FINALLEVELSTATE)
			gameStates[state] = new FinalLevelState(this);

	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(helped) {
			helpState.update();
			return;
		}
		if(highscore) {
			highscoreState.update();
			return;
		}
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
		
	}
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(helped) {
			helpState.draw(g);
			return;
		}
		if(highscore) {
			highscoreState.draw(g);
			return;
		}
		try {
			
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);

	}
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);

	}
	
	public void setPaused(boolean b) { paused = b; }
	public void setHelped(boolean b) { helped = b; }
	public void setHighScore(boolean b) { highscore = b; }
	

}

package game;

/**
 * Driver class which sets up the game map, adds actor to map, then run the game once.
 */
public class Application {

	/**
	 * Entry point into the application. It sets up the game map, adds actor to map, 
	 * then run the game once.
	 */
	public static void main(String[] args) {
		
		GameManagement gameManagement = new GameManagement();
		GameWorld world = gameManagement.setUpGame();
		world.run();
		
	}
}


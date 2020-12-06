package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the game world, including the locations of all Actors, the 
 * player, and the playing grid. This game word can have a safety system. Player can
 * either lose, win or quit game.
 */
public class GameWorld extends World {

	private boolean quit;
	private boolean win;
	private SafetySystem safetySystem;

	/**
	 * Constructor that initialises display to display object given, and quit
	 * and quit to false to indicate player has not quit or win this game.
	 *
	 * @param display the I/O object to which messages may be written
	 */
	public GameWorld(Display display) {
		super(display);
		this.quit = false;
		this.win = false;
	}

	/**
	 * Sets the safety system of this game world to the safety system given.
	 *
	 * @param safetySystem safety system to be set to this game world
	 */
	public void setSafetySystem(SafetySystem safetySystem) {
		this.safetySystem = safetySystem;
	}


	/**
	 * Returns true if the game is still running.
	 * The game is considered to still be running if the player is still around
	 * and player has not quit and has not win the game.
	 *
	 * @return true if the player is still on the map.
	 */
	@Override
	protected boolean stillRunning() {
		return super.stillRunning() && !quit && !win;
	}

	/**
	 * Gives an Actor its turn if the game is still running, also runs safety 
	 * system on Actor if Actor is player.
	 *
	 * The Actions an Actor can take include:
	 * <ul>
	 *  <li> those conferred by items it is carrying </li>
	 *  <li> movement actions for the current location and terrain </li>
	 *  <li> actions that can be done to Actors in adjacent squares </li>
	 *  <li> actions that can be done using items in the current location </li>
	 *  <li> skipping a turn</li>
	 * </ul>
	 *
	 * @param actor the Actor whose turn it is.
	 */
	@Override
	protected void processActorTurn(Actor actor) {
		if (stillRunning()) {
			super.processActorTurn(actor);
			if (safetySystem != null && actor == player) {
				String result = safetySystem.run(actor);
				//run safety system if there is a safety system and actor being
				//processed is the target player
				if (result != null) {
					display.println(result);
				}
			}
		}
	}

	/**
	 * Return a appropriate string that can be displayed when the game ends when
	 * player quits, wins or loses.
	 *
	 * @return the string "Bye!" if player quits, "Player wins!" if player wins,
	 * "Player loses!" otherwise
	 */
	@Override
	protected String endGameMessage() {
		if (quit) {
			return "Bye!";
		}
		else if (win) {
			return "Player wins!";
		}
		else {
			return "Player loses!";
		}
	}

	/**
	 * Sets the quit sentinel to true to indicate player has quit the game.
	 *
	 */
	public void quitGame() {
		quit = true;
	}

	/**
	 * Sets the win sentinel to true to indicate player has won the game.
	 *
	 */
	public void winGame() {
		win = true;
	}
}
package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that quits the game.
 */
public class QuitGameAction extends Action {

	private GameWorld world;

	/**
	 * Constructor that initialises world to the world object given.
	 *
	 * @param world World that is currently being run
	 */
	public QuitGameAction(GameWorld world) {
		this.world = world;
	}

	/**
	 * Quits the game.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		world.quitGame();
		return actor + " quits game";
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i.e. Quit game 
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Quit game";
	}

	/**
	 * Returns an empty string as quit game does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}

}
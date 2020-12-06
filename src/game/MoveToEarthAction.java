package game;

import edu.monash.fit2099.engine.*;

/**
 * An action that allows player to move to earth and wins the game if the player
 * has the target boss's unconscious body in its inventory.
 */
public class MoveToEarthAction extends Action {

	private Location earthRocketLocation;
	private Item bossUnconsciousBody;
	private GameWorld world;

	/**
	 * Constructor, also initialises earthRocketLocation earth rocket's location, 
	 * bossUnconsiousBody to YugoMaxx's unconscious body, and world to the game 
	 * world.
	 *
	 * @param earthRocketLocation rocket's location on earth which actor that
	 * performs this action will be moved to 
	 * @param boss YugoMaxx which its unconscious body must be brought to the 
	 * earth to win the game
	 * @param world the game world all actors are in
	 */
	public MoveToEarthAction(Location earthRocketLocation, YugoMaxx boss, GameWorld world) {
		this.earthRocketLocation = earthRocketLocation;
		this.bossUnconsciousBody = boss.getUnconsciousBody();
		this.world = world;
	}

	/**
	 * Moves actor to the rocket's location on earth, also checks if actor has 
	 * YugoMaxx's unconscious body in its inventory, then the actor wins the game.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (actor.getInventory().contains(bossUnconsciousBody)) {
			world.winGame(); 
			//win game if actor is carrying boss's unconscious body when go to earth
		}
		MoveActorAction moveToEarth = new MoveActorAction(earthRocketLocation, "to Earth! ");
		return moveToEarth.execute(actor, map);
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i.e. Player moves to earth!
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " moves to Earth! ";
	}

	/**
	 * Returns an empty string as this action does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}
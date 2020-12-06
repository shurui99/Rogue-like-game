package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that uses a rocket body and a rocket engine on a rocket pad to build 
 * a rocket. After rocket is built at the location of rocket pad, the rocket body
 * and rocket engine will disappear.
 */
public class BuildRocketAction extends Action {

	private final Location ROCKET_PAD_LOCATION;
	private Item rocketEngine;
	private Item rocketBody;
	private Item rocket;

	/**
	 * Constructor, also initialises rocketPadLocation, rocketEngine, rocketBody,
	 * rocket to the location of rocket, rocket engine, rocket body and rocket 
	 * given respectively.
	 *
	 * @param rocketPadLocation The location of this rocketPad
	 * @param rocketEngine Rocket engine required to build rocket
	 * @param rocketBody Rocket body required to build rocket
	 * @param rocket Rocket to be built on this rocketPad
	 */
	public BuildRocketAction(Location rocketPadLocation, Item rocketEngine, 
											Item rocketBody, Item rocket) {
		this.ROCKET_PAD_LOCATION = rocketPadLocation;
		this.rocketEngine = rocketEngine;
		this.rocketBody = rocketBody;
		this.rocket = rocket;
	}

	/**
	 * Removes rocket body and rocket engine on the rocket pad and drops a rocket 
	 * at the location.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		ROCKET_PAD_LOCATION.removeItem(rocketEngine);
		ROCKET_PAD_LOCATION.removeItem(rocketBody);
		//remove rocket parts from location
		map.addItem(rocket, ROCKET_PAD_LOCATION.x(), ROCKET_PAD_LOCATION.y()); 
		//add rocket at location of rocket pad
        return menuDescription(actor);
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i.e. "Player builds a rocket"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " builds a rocket";
	}

	/**
	 * Returns an empty string as rocket building does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}

}
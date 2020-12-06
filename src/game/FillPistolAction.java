package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that fills a water pistol with water.
 */
public class FillPistolAction extends Action {

	private WaterPistol waterPistol;

	/**
	 * Constructor that initialises water pistol to the water pistol object given.
	 *
	 * @param waterPistol water pistol to be filled with water
	 */
	public FillPistolAction(WaterPistol waterPistol) {
		this.waterPistol = waterPistol;
	}

	/**
	 * Fill water pistol with water and return appropriate string
	 * 
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		waterPistol.fillWater();
		return actor + "'s water pistol is filled with water. ";
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i. e. "Player fills the empty water pistol with water"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " fills the empty water pistol with water";
	}

	/**
	 * Returns an empty string as filling a pistol does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}
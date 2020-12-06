package game;

import edu.monash.fit2099.engine.*;

/**
 * An action that ha 70% of squirting water onto YugoMaxx which can destroy its 
 * exoskeleton to make it vulnerable to damage.
 *
 */
public class SquirtAction extends Action {

	private YugoMaxx subject;
	private WaterPistol waterPistol;

	/**
	 * Constructor, also initialises subject to the YugoMaxx given and waterPistol 
	 * to the water pistol given
	 * 
	 * @param subject Yugo Maxx that may be squirted with water
	 * @param waterPistol water pistol used to squirt water
	 */
	public SquirtAction(YugoMaxx subject, WaterPistol waterPistol) {
		this.subject = subject;
		this.waterPistol = waterPistol;
	}

	/**
	 * Squirts water onto the subject at 70% chance from the full water pistol.
	 * This empties the water in the water pistol no matter it hits or misses.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		waterPistol.emptyWater();
		if (Math.random() <= 0.3) {
			return actor + " misses " + subject + ".";
		}
		else {
			subject.destroyExoskeleton();
			return String.format("%s squirts water onto %s. %s's exoskeleton has been destroyed. ", actor, subject, subject);
		}
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i.e. "Player squirts water onto Yugo Maxx"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " squirts water onto " + subject;
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
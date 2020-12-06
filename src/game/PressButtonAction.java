package game;

import edu.monash.fit2099.engine.*;

/**
 * An action that press the button of the oxygen dispenser to cause it to summon
 * an oxygen dispenser manager to produce an oxygen tank in its location on the 
 * next turn. The button will only work when there is no oxygen tank that has not 
 * been retrieved and also no oxygen dispenser manager that is producing the tank 
 * in the location.
 *
 */
public class PressButtonAction extends Action {

	private OxygenDispenser target;
	private Location targetLocation;
	
	/**
	 * Constructor, also initialises the target to the oxygen dispenser given and
	 * targetLocation to the location of oxygen dispenser given.
	 *
	 * @param target The oxygen dispenser whose button will be pressed
	 * @param targetLocation The location of the target oxygen dispenser
	 */
	public PressButtonAction(OxygenDispenser target, Location targetLocation) {
		this.target = target;
		this.targetLocation = targetLocation;
	}
	
	/**
	 * If there is an oxygen tank that has not been collected by an actor, including
	 * oxygen dispenser manager, on the oxygen dispenser, the button does not work;
	 * otherwise, summon a newly created OxygenDispenserManager on the same location
	 * so that the manager will produce an oxygen tank next round.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		if (target.hasTank() || map.isAnActorAt(targetLocation)) {
			return menuDescription(actor) + " but the button does not work";
		}
		else {
			OxygenDispenserManager manager = new OxygenDispenserManager(target.toString(), target);
			map.addActor(manager, targetLocation.x(), targetLocation.y());
			return menuDescription(actor) + ", oxygen tank will be produced next round";
		} 
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i. e. "Player press the oxygen dispenser button"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " press the oxygen dispenser button";
	}

	/**
	 * Returns an empty string as button pressing does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}

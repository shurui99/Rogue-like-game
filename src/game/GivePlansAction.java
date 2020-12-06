package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that gives rocket plans to a target and get rocket body in return. This
 * action causes target to disappear from map.
 */
public class GivePlansAction extends Action {

	private Actor target;
	private Item rocketBody;
	private Item rocketPlans;

	/**
	 * Constructor that initialises target to the subject given, rocketBody to
	 * rocket body given and rocketPlans to rocket plans given.
	 *
	 * @param subject The actor to give rocket plans to
	 * @param rocketBody The rocket body that will be given in return
	 * @param rocketPlans The rocket plans to be given
	 */
	public GivePlansAction(Actor subject, Item rocketBody, Item rocketPlans) {
		this.target = subject;
		this.rocketBody = rocketBody;
		this.rocketPlans = rocketPlans;
	}

	/**
	 * If actor has the rocket plans, gives them to target and get rocket body in 
	 * return, then target disappears; otherwise, nothing happens.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		if (actor.getInventory().contains(rocketPlans)) {//indicates rocket plans found in inventory
			actor.removeItemFromInventory(rocketPlans); 
			//remove rocket plans from actor's inventory
			actor.addItemToInventory(rocketBody); 
			//add rocket body to actor's inventory
			map.removeActor(target); //target disappears
			return menuDescription(actor) + ", gets rocket body from Q and Q disappears";
		}
		return actor + " doesn't have plans to give ";
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i. e. "Player gives Q rocket plans"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " gives " + target + " rocket plans";
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
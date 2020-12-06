package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that talks to the target actor accordingly depending on whether the target 
 * has rocket plans. 
 */
public class TalkAction extends Action {

	private Actor target;
	private Item rocketPlans;

	/**
	 * Constructor, also initialises target to the subject given and rocketPlans 
	 * to the rocketPlans given.
	 *
	 * @param subject The actor to talk to
	 * @param rocketPlans Rocket plans that will be searched in player's inventory
	 */
	public TalkAction(Actor subject, Item rocketPlans) {
		this.target = subject;
		this.rocketPlans = rocketPlans;
	}

	/**
	 * Checks whether target has rocket plans, if the target has it, returns a
	 * string that instructs target to hand them over to this Q; otherwise, returns
	 * a string that tells target that target can trade the plans for something
	 * useful.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		if (target.getInventory().contains(rocketPlans)) { 
		//Checks if target's inventory contains rocket plans
			return actor + " talked to player " + "- Hand them over, I don't have all day! ";
		}
		return actor + " talked to player " + "- I can give you something that will help, but I'm going to need the plans. ";
	}

	/**
	 * Returns an empty string as this action does not have a suitable string to 
	 * be displayed in the UI menu.
	 *
	 * @param actor The actor performing the action
	 * @return An empty string
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "";
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
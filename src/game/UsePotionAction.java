package game;

import edu.monash.fit2099.engine.*;

/**
 * An action that uses potion to heal the hitpoint of the actor performing this 
 * action based on the heal point of the potion used. The actor's hitpoints will
 * not exceeds its maximum hitpoints after use.
 */
public class UsePotionAction extends Action {
	
	private Item potion;
	private int healPoint;
	
	/**
	 * Constructor, also initialises potion and healPoint to the potion and
	 * healPoint given.
	 * 
	 * @param potion the potion this action uses.
	 * @param healPoint the heal point provided by the potion.
	 */
	public UsePotionAction(Item potion,int healPoint) {
		this.potion = potion;
		this.healPoint = healPoint;
	}
	
	/**
	 * Restores the actor's hitpoint by the potion's healpoint. After restoring 
	 * player's hitpoint, the potion will be removed from player's inventory.
	 * 
	 * 
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		actor.heal(healPoint);
		actor.removeItemFromInventory(potion);
		return menuDescription(actor);
		
	}

	/**
	 * Returns a string describing the action suitable for displaying in the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a string, e.g. "Player use the HP potion 30 of healpoint 30"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " use the " + potion + " of healpoint " + healPoint;
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

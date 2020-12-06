package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that produces an oxygen tank on an oxygen dispenser, then causes the
 * actor performing this action to disappear.
 *
 */
public class ProduceOxygenTankAction extends Action {

	private OxygenDispenser target;

	/**
	 * Constructor that creates an ProduceOxygenTankAction object and sets target
	 * to the target oxygen dispenser given.
	 *
	 * @param target the oxygen dispenser to produce oxygen tank on
	 */
	public ProduceOxygenTankAction(OxygenDispenser target) {
		this.target = target;
	}

	/**
	 * Sets target's hasTank to true to indicate that a tank is produced, then
	 * remove actor from map.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		target.setHasTank(true); //to indicate a tank is produced
		map.removeActor(actor);
		return "An oxygen tank is produced";
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
	 * Returns an empty string as producing oxygen tank does not have a dedicated 
	 * hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}
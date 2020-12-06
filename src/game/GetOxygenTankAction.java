package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that gets oxygen tank from oxygen dispenser.
 */
public class GetOxygenTankAction extends Action{
	private OxygenTank oxygenTank;
	private static OxygenTanks oxygenTanks;
	private OxygenDispenser oxygenDispenser;
	
	/**
	 * Constructor, also initialises oxygenTank, oxygenTanks and oxygenDispenser 
	 * to oxygen tank, list of oxygen tanks and oxygen dispenser given respectively.
	 * 
	 * @param oxygenTank the oxygen tank to be retrieved from oxygen dispenser
	 * @param oxygenTanks the list of non-empty oxygen tanks produced in game, to 
	 * be added with the oxygen tank retrieved
	 * @param oxygenDispenser the oxygen dispenser to get oxygen tank from
	 */
	public GetOxygenTankAction(OxygenTank oxygenTank, OxygenTanks oxygenTanks, 
											OxygenDispenser oxygenDispenser) {
		this.oxygenTank = oxygenTank;
		GetOxygenTankAction.oxygenTanks = oxygenTanks;
		this.oxygenDispenser = oxygenDispenser;
	}
	
	/**
	 * Set the oxygen dispenser's hasTank status to be false, then add the oxygen 
	 * tank retrieved to actor's inventory as well as the oxygenTanks list.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		oxygenDispenser.setHasTank(false);
		//to indicate there is no tank on dispenser
		actor.addItemToInventory(oxygenTank);
		oxygenTanks.add(oxygenTank); 
		//add the new tank into list that contains all non-empty oxygen tank produced in game
		return menuDescription(actor);
	}
	
	/**
	 * Returns a string describing the action suitable for displaying in the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a string, e.g. "Player gets the oxygen tank (oxygen point: 10)"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " gets the " + oxygenTank;
	}

	/**
	 * Returns an empty string as getting oxygen tank does not have a dedicated 
	 * hotkey.
	 * 
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}


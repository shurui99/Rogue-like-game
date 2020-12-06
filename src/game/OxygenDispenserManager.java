package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing an oxygen dispenser manager.
 * Oxygen dispenser produces an oxygen tank, then disappear from map during its 
 * turn.
 * Oxygen dispenser manager allows other actor to press the button on oxygen 
 * dispenser, but the button will never work when oxygen dispenser manager is 
 * present because it is producing an oxygen tank.
 */

public class OxygenDispenserManager extends Actor {

	private OxygenDispenser target;
	
	/**
	 * Constructor that creates an OxygenDispenserManager object with name given,
	 * display char 'D', prority 2 and 50 HP, also sets target to oxygen dispenser
	 * given.
	 *
	 * @param name Name to call the OxygenDispenserManager in the UI
	 * @param target The oxygen dispenser that the OxygenDispenserManager will
	 * produce oxygen tank on
	 */
	public OxygenDispenserManager(String name, OxygenDispenser target) {
		super(name, 'D', 2, 50);
		this.target = target;
	}
	
	/**
	 * Selects and return an action to perform on the current turn. It always 
	 * performs ProduceOxygenTankAction.
     *
     * @see Actor#playTurn(Actions, GameMap, Display)
     * @param actions Collection of possible Actions for OxygenDispenserManager
	 * @param map The map containing the OxygenDispenserManager
	 * @param display The I/O object to which messages may be written
	 * @return the Action to be performed by OxygenDispenserManager
	 */
	public Action playTurn(Actions actions, GameMap map, Display display) {
		return new ProduceOxygenTankAction(target); 
	}

	/**
     * Returns a collection of the Actions containing actions that the otherActor 
     * can do to the OxygenDispenserManager. The collection includes PressButtonAction.
     *
     * @param otherActor The Actor that might be performing the actions on this
     * OxygenDispenserManager
	 * @param direction String representing the direction of the other Actor
	 * @param map current GameMap containing this OxygenDispenserManager and 
	 * otherActor
	 * @return a collection of Actions that can be performed on OxygenDispenserManager
	 */
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new PressButtonAction(target, map.locationOf(this)));
	}
}

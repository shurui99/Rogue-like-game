package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing an oxygen dispenser of terrain type.
 * Oxygen dispenser has a button that allow other actor to press to cause it to
 * produce an oxygen tank in its location on the next turn. The button will only
 * work when dispenser is not producing the tank and there is no oxygen tank in
 * the location that has not been retrieved.
 */

public class OxygenDispenser extends Ground {

	private static OxygenTanks oxygenTanks;
	private Location here;
	private boolean hasTank;
	
	/**
	 * Constructor, also initialises displayChar to 'D'.
	 */
	public OxygenDispenser() {
		super('D');
	}

	/**
	 * Constructor, also initialises displayChar to 'D' and initialises oxygenTanks
	 * to the list of non-empty oxygen tanks produced in game given, here to the 
	 * location of oxygen dispenser given and hasTank to false to indicate there
	 * is no oxygen tank produced on it.
	 *
	 * @param oxygenTanks List of non-empty oxygen tanks produced in game
	 * @param oxygenDispenserLocation Location of the OxygenDispenser
	 */
	public OxygenDispenser(OxygenTanks oxygenTanks, Location oxygenDispenserLocation) {
		super('D');
		OxygenDispenser.oxygenTanks = oxygenTanks;
		this.here = oxygenDispenserLocation;
		hasTank = false;
	}

	/**
	 * Returns true if there is an oxygen tank produced that has not been collected
	 * on the OxygenDispenser, false otherwise.
	 *
	 * @return true if there is an oxygen tank produced that has not been collected
	 * on the OxygenDispenser, false otherwise.
	 */
	public boolean hasTank() {
		return hasTank;
	}

	/**
	 * Sets hasTank to true or false depending on the status of type boolean given
	 * to indicate the presence of oxygen tank on the OxygenDispenser.
	 *
	 * @param status A boolean to set hasTank to
	 */
	public void setHasTank(boolean status) {
		hasTank = status;
	}

	/**
	 * Implements impassable oxygen dispenser.
	 *
	 * @param a The Actor that checks whether this water is passable.
	 * @return false
	 */
	@Override
	public boolean canActorEnter(Actor a) {
		return false;
	}

	/**
	 * Returns an Action list containing PressButtonAction only if there is no
	 * oxygen tank on the OxygenDispenser, the list will also be added with 
	 * GetOxygenTankAction otherwise.
	 *
	 * @see Ground#allowableActions(Actor, Location, String)
	 * @param actor The Actor acting
	 * @param location The current Location
	 * @param direction The direction of the OxygenDispenser from the Actor
	 * @return Actions containing PressButtonAction only if there is no oxygen 
	 * tank on OxygenDispenser, Actions also contains GetOxygenTankAction otherwise.
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		Actions actions = new Actions(new PressButtonAction(this, here));
		if (hasTank) {
			actions.add(new GetOxygenTankAction(new OxygenTank(), oxygenTanks, this));
		}
		return actions;
	}
}

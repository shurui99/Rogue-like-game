package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing water of water type. If the adjacent player's inventory contains
 * an empty water pistol, it allows the player to perform FillPistolAction to 
 * fill the water pistol with water.
 */

public class Water extends Ground {

	private WaterPistol waterPistol;

	/**
	 * Constructor, also initialises displayChar to '~'.
	 */
	public Water() {
		super('~');
	}

	/**
	 * Constructor, also initialises displayChar to '~' and sets waterPistol
	 * that can be filled with this water.
	 *
	 * @param waterPistol water pistol that can be filled with water
	 */
	public Water(WaterPistol waterPistol) {
		super('~');
		this.waterPistol = waterPistol;
	}

	/**
	 * Implements impassable water.
	 *
	 * @param actor The Actor that checks whether this water is passable.
	 * @return false
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * If player has an empty water pistol in its inventory, returns an Action list
	 * containing FillPistolAction, returns empty Actions otherwise.
	 *
	 * @see Ground#allowableActions(Actor, Location, String)
	 * @param actor The Actor performing this action
	 * @param location The current Location
	 * @param direction The direction of the water from the Actor
	 * @return Actions containing FillPistolAction if an empty waterPistol is present in 
	 * player's inventory, empty Actions otherwise.
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		if (actor.getInventory().contains(waterPistol) && waterPistol.isEmpty()) {
			return new Actions(new FillPistolAction(waterPistol));
			//allow to fill water if actor has an empty water pistol			
		}	
			
		return new Actions();
	}

}
package game;

import java.util.*;

import edu.monash.fit2099.engine.*;

/**
 * Action that opens and go to location of a door if the actor has the correct
 * key.
 */
public class OpenDoorAction extends Action {

	private Door door;
	private Location doorLocation;
	
	/**
	 * Constructor that initialises door and door location to the door object and 
	 * the location of the door given respectively.
	 *
	 * @param doorLocation The location of door to be opened
	 * @param door Door to be opened.
	 */
	public OpenDoorAction(Door door, Location doorLocation) {
		this.door = door;
		this.doorLocation = doorLocation;
	}

	/**
	 * Check if the actor's inventory contains the matching key. Returns true if
	 * key is found, false otherwise.
	 * 
	 * @param actor The actor who performs this OpenDoorAction
	 * @return true if the actor's inventory contains matching key, false otherwise
	 */
	private boolean containsKey(Actor actor) {
		List<Item> keys = door.getKeys();
		boolean keyFound = false;
		for (Item item: actor.getInventory()) { //search for key in actor's inventory
			if (keys.contains(item)) {
				keyFound = true;
			}
		}
		return keyFound; 
	}

	/**
	 * Opens and goes to the location of door if actor has the correct key and 
	 * there is no other actor blocking the door, nothing happens otherwise.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String retVal;
		if (containsKey(actor)) {
			if (!doorLocation.containsActor()) { //ensure there is no other actor blocking the door
				(new MoveActorAction(doorLocation, "")).execute(actor, map); 
				//move player to location of door
				retVal = menuDescription(actor);
			}
			else {
				retVal = actor +" fails to open the door, it is blocked. ";
			}
		}
		else {
			retVal = actor +" fails to open the door, "+ actor + " has no correct key. ";
		}
		return retVal;
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i. e. "Player opens the door"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " opens the door";
	}

	/**
	 * Returns an empty string as door opening does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}
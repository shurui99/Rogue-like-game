package game;

import java.util.*;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a locked door of terrain type. It has a list of keys that 
 * can open it, and allows adjacent actor to perform OpenDoorAction on it.
 */
public class Door extends Ground {

	private List<Item> keys = new ArrayList<>();

	/**
	 * Constructor, also initialises displayChar to '+'.
	 */
	public Door() {
		super('+');
	}

	/**
	 * Returns an unmodifiable list of keys that can open the door.
	 * @return an unmodifiable list of keys
	 */
	public List<Item> getKeys() {
		return Collections.unmodifiableList(keys);
	}

	/**
	 * Adds the key given into the list of keys that can open the door.
	 * @param key The key Item to be added into keys list
	 */
	public void addKey(Item key) {
		keys.add(key);
	}

	/**
	 * Returns an Action list containing OpenDoorAction.
	 *
	 * @param actor The Actor acting
	 * @param location The current Location
	 * @param direction The direction of the Door from the Actor
	 * @return an Action list containing OpenDoorAction
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction){
		return new Actions(new OpenDoorAction(this, location));
	}

	/**
	 * Implements impassable terrain.
	 *
	 * @param actor The Actor that checks whether this door is passable.
	 * @return false
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * Implements terrain that blocks thrown objects.
	 *
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}

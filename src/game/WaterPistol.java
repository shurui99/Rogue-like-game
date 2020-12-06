package game;

import edu.monash.fit2099.engine.*;

/**
 * A class representing a water pistol item, it can be filled with water and it 
 * can be used to squirt water on YugoMaxx to destroy its exoskeleton.
 */
public class WaterPistol extends Item {

	private boolean empty;

	/**
	 * A constructor, it creates an empty WaterPistol object with name given and 
	 * displayChar given.
	 * 
	 * @param name Name to call the oxygen dispenser in the UI
	 */
	public WaterPistol(String name) {
		super(name, '>');
		this.empty = true;
	}

	/**
	 * Returns true if this water pistol is empty, false otherwise.
	 * 
	 * @return true if this water pistol is empty, false otherwise
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Sets empty to false to indicate that this water pistol is filled with water.
	 * 
	 */
	public void fillWater() {
		empty = false;
	}

	/**
	 * Sets empty to true to indicate that this water pistol is emptied.
	 * 
	 */
	public void emptyWater() {
		empty = true;
	}
}
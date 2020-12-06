package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing Doctor Maybe which is an enemy. 
 * Dr Maybe's hitpoints is half the default hitpoints (25), and damage of intrinsic 
 * weapon is half the default damage (3).
 * It cannot moves around, can attack adjacent actor and has rocket engine in its 
 * inventory.
 */
public class DrMaybe extends Enemy {
	/**
	 * Constructor that creates a DrMaybe object with name given, displayChar 'M'
	 * and priority 5, its HP is half the HP of Enemy. This constructor also adds 
	 * a rocketEngine to its inventory.
	 * 
	 * @param name Name to call the DrMaybe in the UI
	 * @param rocketEngine Rocket engine to be added to DrMaybe's inventory
	 */
	public DrMaybe(String name, Item rocketEngine) {
		super(name, 'M', 5);
		this.hitPoints = this.hitPoints / 2;
		this.hitPoints = this.maxHitPoints / 2;
		addItemToInventory(rocketEngine);
	}

	/**
	 * Creates and returns an intrinsic weapon that allows DrMaybe 'strangles' for 
	 * half the Enemy's damage.
	 *
	 * @return a freshly-instantiated IntrinsicWeapon of half the Enemy's damage
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(super.getIntrinsicWeapon().damage() / 2, "strangles");
	}
}

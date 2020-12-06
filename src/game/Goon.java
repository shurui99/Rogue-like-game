package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a Goon which is an enemy. 
 * Goon's hitpoints is same as the default hitpoints (50), and damage of intrinsic 
 * weapon is twice the default damage (12).
 * It always tries to move closer to a target player, has a behaviour of insulting 
 * the target player at 10% chance and can attack adjacent actor.
 */
public class Goon extends Enemy {
	/**
	 * Constructor that creates a Goon object with name given, displayChar 'O'
	 * and priority 3. This also adds FollowBehaviour, InsultBehaviour to its 
	 * actionFactories
	 *
	 * @param name Name to call the Goon in the UI
	 * @param player The actor that Goon performs action on
	 */
	public Goon(String name, Actor player) {
		super(name, 'O', 3);
		addBehaviour(new FollowBehaviour(player));
		addBehaviour(new InsultBehaviour(player));
	}
	
	/**
	 * Creates and returns an intrinsic weapon that allows Goon 'kicks' for 
	 * twice the Enemy's damage.
	 * 
	 * @return a freshly-instantiated IntrinsicWeapon of twice the Enemy's damage
	 */
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(super.getIntrinsicWeapon().damage() * 2, "kicks");
	}
}

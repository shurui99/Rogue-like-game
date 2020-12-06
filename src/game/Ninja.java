package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a Ninja which is an enemy.
 * Ninja's hitpoints is same as the default hitpoints (50), and damage of intrinsic 
 * weapon is also same as the default damage (6).
 * It can throw stun powder to stun a target player that is within 5 squares and 
 * has 50% chance of hitting it.
 */
public class Ninja extends Enemy {
	/**
	 * Constructor, that creates a Ninja object with name given, displayChar 'N' and
	 * priority 8, also adds StunAction to its actionFactories.
	 *
	 * @param name Name to call the Ninja in the UI
	 * @param player The actor that Ninja performs action on
	 * @param world The game world all the actors are in
	 */
	public Ninja(String name, Actor player, GameWorld world) {
		super(name, 'N', 8);
		addBehaviour(new StunAction(player, world));
	}
}
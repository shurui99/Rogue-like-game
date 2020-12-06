package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a Grunt which is an enemy.
 * Grunt's hitpoints is same as the default hitpoints (50), and damage of intrinsic 
 * is also same as the default damage (6).
 * It always tries to move closer to a target player and can attack adjacent actor.
 */
public class Grunt extends Enemy {

	/**
	 * Constructor, that creates a Grunt object with name given, displayChar 'G'
	 * and priority 4. This also adds FollowBehaviour to its actionFactories.
	 * @param name Name to call the Grunt in the UI
	 * @param player The actor that Grunt performs action on
	 */
	public Grunt(String name, Actor player) {
		super(name, 'G', 4);
		addBehaviour(new FollowBehaviour(player));
	}
}

package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * Class representing a wall of terrain type.
 */
public class Wall extends Ground {

	/**
	 * Constructor, also initialises displayChar to '#'.
	 */
	public Wall() {
		super('#');
	}
	
	/**
	 * Implements impassable terrain.
	 *
	 * @param actor the Actor that checks whether this door is passable.
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

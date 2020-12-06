package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A behaviour that allows Actor to wander around the map, allowing it to perform 
 * MoveActorAction to a random exit if there is no target beside it.
 * 
 */
public class WanderBehaviour implements ActionFactory {

	private Random rand = new Random();
	private Actor target;

	/**
	 * Constructor that initialises target to the subject given.
	 *
	 * @param subject the target that will stop the actor having this behaviour from wandering 
	 * 		          if the target is beside the actor
	 */
	public WanderBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * Returns an Action that moves the actor to one of the random exit if the target 
	 * is not beside the actor having this behaviour, else return null
	 *
	 * @see ActionFactory#getAction(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return MoveActionAction if there is no actor beside the actor having this behaviour,
	 * 		   null otherwise
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);
		if (there == null) {
			return null;
		}
		int currentDistance = distance(here, there);
		if (currentDistance > 1) {
			Actions moveActions = new Actions();
			for (Exit exit: here.getExits()) {
				Location destination = exit.getDestination();
				if (destination.canActorEnter(actor)) {
					moveActions.add(new MoveActorAction(destination, exit.getName()));
				}
			}
			return moveActions.get(rand.nextInt(moveActions.size()));
		}
		return null;
	}

	/**
	 * Calculates and returns the distance between 2 locations is they are on the
	 * same map, return Integer.MAX_VALUE otherwise.
	 * 
	 * @param a A location
	 * @param b Another location
	 * @return distance between location a and b if there are on the same map, 
	 * Integer.MAX_VAUE otherwise
	 */
	private int distance(Location a, Location b) {
		if (a.map() != b.map()) {
			return Integer.MAX_VALUE;
		}
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}
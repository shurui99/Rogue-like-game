package game;

import edu.monash.fit2099.engine.*;

/**
 * A behaviour that allows Actor to follow another Actor, thus allowing it to perform 
 * MoveActorAction to location nearer to the other Actor if any.
 */
public class FollowBehaviour implements ActionFactory {

	private Actor target;
	private Location previousTargetLocation;

	/**
	 * Constructor that initialises target to the subject given.
	 *
	 * @param subject The actor to be followed
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * Returns an Action that moves the actor one space such that it gets closer 
	 * to the target. If target is not on the map, moves actor closer to the
	 * location of target last appear.
	 *
	 * @see ActionFactory#getAction(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return MoveActionAction if there exist a location closer to the other actor,
	 * null otherwise
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location there = map.locationOf(target);
		if (there == null) { //indicates target actor is not on map
			there = this.previousTargetLocation;
			//set target location to location the target actor last appears.
		}		
		else {
			this.previousTargetLocation = there; 
			//set target location to location of target actor on map
		}
		Location here = map.locationOf(actor);
		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) { 
				//indicates a location closer to target is found
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		return null;
	}

	/**
	 * Calculates and returns the distance between 2 locations
	 *
	 * @param a A location
	 * @param b Another location
	 * @return distance between location a and b
	 */
	private int distance(Location a, Location b) {
		//manhattan distance
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}
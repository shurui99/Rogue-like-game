package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a Q.
 * Q wanders around the map unless the target player is on an adjacent ground,
 * then it talks to the target player.
 * Q allows other actor to perform GivePlansAction on it and cannot not be attacked 
 * by any actor.
 */
public class Q extends Actor {

	private Actor target;
	private Item rocketBody;
	private Item rocketPlans;
	private ActionFactory actionFactory;

	/**
	 * Constructor that creates a Q object with name given, displayChar 'Q', 
	 * priority 7 and 50 HP, also sets target to actor given and actionFactory to
	 * WanderBehaviour.
	 *
	 * @param name Name to call the Q in the UI
	 * @param player The actor that this Q talks to
	 * @param rocketBody rocket body to be given to actor by Q
	 * @param rocketPlans rocket plan that Q has to receive from other actor
	 */
	public Q(String name, Actor player, Item rocketBody, Item rocketPlans) {
		super(name, 'Q', 7, 50);
		this.target = player;
		this.rocketBody = rocketBody;
		this.rocketPlans = rocketPlans;
		actionFactory = new WanderBehaviour(player);
	}

	/**
     * Selects and return an action to perform on the current turn. Q wanders around
     * if target is not next to Q. If target is next to Q, it performs TalkAction.
     * It skips turn if all actions previously mentioned are not available.
     *
     * @see Actor#playTurn(Actions, GameMap, Display)
     * @param actions Collection of possible Actions for Q
	 * @param map The map containing the Q
	 * @param display The I/O object to which messages may be written
	 * @return the Action to be performed by Q
	 */
	@Override
	public Action playTurn(Actions actions, GameMap map, Display display) {
		Action action = actionFactory.getAction(this, map);
		if (action != null) {
			return action;
		}
		Location here = map.locationOf(this);
		for (Exit exit: here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.getActor() == target) { //indicates target on adjacent ground
				return new TalkAction(target, rocketPlans); //talks to target
			}
		}
		return new SkipTurnAction();
	}

    /**
     * Returns a collection of the Actions that the otherActor can do to Q.
     * The collection contains GivePlansAction.
     *
     * @see Actor#getAllowableActions(Actor, String, GameMap)
     * @param otherActor The Actor that might be performing action on this Q
	 * @param direction String representing the direction of the other Actor
	 * @param map The current game map containing Q and otherActor
	 * @return a collection of Actions that can be performed on Q
	 */
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new GivePlansAction(this, rocketBody, rocketPlans));
	}
}
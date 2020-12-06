package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a rocket pad of terrain type. If the rocket body and rocket
 * engine is present on it, it allows adjacent actor to perform BuildRocketAction.
 */
public class RocketPad extends Ground {

	private Item rocketBody;
	private Item rocketEngine;
	private Item rocket;
	private Actor player;

	/**
	 * Constructor, also initialises displayChar to '*'.
	 */
	public RocketPad() {
		super('*');
	}

	/**
	 * Constructor, also initialises displayChar to '*' and sets the rocket body,
	 * rocket engine, rocket and player.
	 *
	 * @param rocketBody Rocket body required on this rocket pad to build rocket
	 * @param rocketEngine Rocket engine required on this rocket pad to build rocket
	 * @param rocket Rocket to be built on this rocket pad
	 * @param player Player that will be using the rocket build on the rocket pad
	 */
	public RocketPad(Item rocketBody, Item rocketEngine, Item rocket, Actor player) {
		super('*');
		this.rocketBody = rocketBody;
		this.rocketEngine = rocketEngine;
		this.rocket = rocket;
		this.player = player;
	}

	/**
	 * Constructor, also initialises displayChar to '*' and sets the player.
	 *
	 * @param player Player that will be using the rocket build on the rocket pad
	 */
	public RocketPad(Actor player) {
		super('*');
		this.player = player;
	}

	/**
	 * Implements impassable terrain for all actors except player. This is to
	 * only allow player to take the rocket built.
	 *
	 * @param actor The Actor that checks whether this door is passable.
	 * @return true if actor is player, false otherwise
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if (actor == player) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns an Action list containing BuildRocketAction if both rocket body and
	 * rocket engine is present on this RocketPad, returns an empty Action list
	 * otherwise.
	 *
	 * @see Ground#allowableActions(Actor, Location, String)
	 * @param actor The Actor acting
	 * @param location The current Location
	 * @param direction The direction of the RocketPad from the Actor
	 * @return Actions containing BuildRocketAction if both rocket body and rocket 
	 * engine are present on the RocketPad, empty Actions otherwise.
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction){
		Actions allowableActions = new Actions();
		boolean bodyAndEngineFound = false;
		//Check if rocket pad contains rocket body and rocket engine
		if (location.getItems().contains(rocketBody) && location.getItems().contains(rocketEngine)) {
			bodyAndEngineFound = true;
		}
		if (bodyAndEngineFound) {
			allowableActions.add(new BuildRocketAction(location, rocketEngine, rocketBody, rocket)); 
			//allow actor to build rocket if both rocket body and engine exists
		}
		return allowableActions;
	}
}

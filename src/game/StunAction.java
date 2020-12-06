package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * An action and also an behaviour that throws stun powder towards the subject if 
 * subject is within 5 squares from the actor acting. The stun powder may be blocked
 * by Ground object that blocks thrown objects and has only 50% chance of hitting
 * the target. If the target is hit, it will be stunned for 2 rounds of the game
 * and the actor acting will moves one space away from the actor.
 */
public class StunAction extends Action implements ActionFactory {
	private Actor subject;
	private StunnedPlayer stunnedPlayer;
	private GameWorld world;
	private Random rand = new Random();
	private int stunStatus;

	/**
	 * Constructor, also initialises subject and world with the subject and world
	 * given. This also creates a new StunnedPlayer object using subject's name
	 * and displayChar, then initialises stunnedPlayer with it.
	 * 
	 * @param subject The actor to be stunned
	 * @param world The game world all the actors are in
	 */
	public StunAction(Actor subject, GameWorld world) {
		this.subject = subject;
		this.world = world;
		this.stunnedPlayer = new StunnedPlayer(subject.toString(), subject.getDisplayChar());
		this.stunStatus = 0;
	}
	
	/**
	 * Returns true if stun powder thrown towards actor is blocked by an Ground 
	 * object that can block thrown objects, returns false otherwise.
	 *
	 * @param actor The actor to be thrown at
	 * @param map The map the actor is on
	 * @return true if there is a Ground object blocking, false otherwise
	 */
	private boolean blockedByGround(Actor actor, GameMap map) {
		Location there = map.locationOf(subject);
		Location here = map.locationOf(actor);
		Range xs, ys;
		xs = new Range(Math.min(here.x(), there.x()), Math.abs(here.x() - there.x()) + 1);
		ys = new Range(Math.min(here.y(), there.y()), Math.abs(here.y() - there.y()) + 1);
		for (int x : xs) {
			for (int y : ys) {
				if(map.at(x, y).getGround().blocksThrownObjects()) { //indicates there is ground blocking
					return true;
				}
			}				
		}
		return false;
	}

	/**
	 * Replaces an actor in map and world with another actor. The actor's location
	 * is the same before and after being replaced.
	 *
	 * @param currentPlayer The actor going to be replaced
	 * @param newPlayer The actor going to replace the current actor
	 * @param map The map the current actor is on 
	 */
	private void swapWorldPlayer(Actor currentPlayer, Actor newPlayer, GameMap map) {
		Location here = map.locationOf(currentPlayer);
		map = here.map();
		map.removeActor(currentPlayer);
		world.addPlayer(newPlayer, map, here.y(), here.x());
		for (Item item : currentPlayer.getInventory()) {
			newPlayer.addItemToInventory(item);
		}
		for (Item item : newPlayer.getInventory()) {
			currentPlayer.removeItemFromInventory(item);
		}
	}

	/**
	 * Returns a MoveActorAction that moves actor one space away from subject. If
	 * no such MoveActionAction is possible, returns SkipTurnAction
	 *
	 * @param actor The actor performing the action
	 * @param map The map actor is on
	 */
	private Action getMoveAwayAction(Actor actor, GameMap map) {
		Location here = map.locationOf(actor);
		Location there = map.locationOf(subject);
		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
			//checks if actor can enter the exit
				int newDistance = distance(destination, there);
				if (newDistance > currentDistance) {
				//checks if the exit is further away from subject
					return new MoveActorAction(destination, "", "");
				}
			}
		}
		return new SkipTurnAction();
	}

	/**
	 * Hurts subject with damage stunnedPlayer's taken in place of it when it is
	 * stunned, then returns a string indicating subject's health condition.
	 *
	 * @param map The map actor is on
	 */
	private String updateSubjectHp(GameMap map) {
		int damage = stunnedPlayer.getDeductedHp();
		subject.hurt(damage);
		if (!subject.isConscious()) {
			map.removeActor(subject);
			return subject + " is knocked out after gaining back consciousness due to damage dealt by other enemy when it is stunned. ";
		}
		return subject + " gain back consciousness. ";
	}

	/**
	 * Returns this action (StunAction) if subject is currently stunned, or, subject
	 * not stunned but is within 5 squares of actor. Returns SkipTurnAction otherwise.
	 * 
	 * @see ActionFactory#getAction(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return this action if subject is within 5 squares of actor, SkipTurnAction otherwise
	 */
	public Action getAction(Actor actor, GameMap map) {
		Actor target;
		if (map.locationOf(subject) != null) { //indicates player is not stunned
			target = subject;
		}
		else if (map.locationOf(stunnedPlayer) != null) { //indicates player is stunned by actor acting
			target = stunnedPlayer;
		}
		else {
			return new SkipTurnAction();
		}
		Location there = map.locationOf(target);
		Location here = map.locationOf(actor);
		if ((distance(here, there) <= 5) || stunStatus > 0) {
			return this;
		}
		return new SkipTurnAction();
	}
	
	/**
	 * Checks the stunStatus and acts accordingly.
	 * If stunStatus is 0, stuns subject and update stunStatus to 2 at 50% chance
	 * when there is no Ground object blocking, also moves actor away from subject.
	 * If stunStatus is 1, updates stunStatus by decrementing it and free subject
	 * from stun, also updates subject's hp.
	 * If stunStatus is 2, updates stunStatus by decrementing it.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String retVal;
		if (stunStatus == 0) { //indicates ninja can try to stun player
			retVal = actor + " throws stun powder at " + subject;
			if (!blockedByGround(actor, map)) {
				if(rand.nextBoolean()) {
					stunStatus = 2;
					getMoveAwayAction(actor, map).execute(actor, map); 
					//move actor away from player
					swapWorldPlayer(subject, stunnedPlayer, map); 
					//replace Player with a StunnedPlayer in world and map	
					retVal = String.format("%s successfully, then moves one space away from %s, %s is stunned for two rounds. ", retVal, subject, subject);	
				}
				else {
					retVal += " but missed. ";
				}
			}
			else {
				retVal += " but blocked. ";
			}
		}

		else if (stunStatus == 1) { //indicates that it's time to free player
			stunStatus -= 1;
			swapWorldPlayer(stunnedPlayer, subject, map); 
			//replace StunnedPlayer with original Player in world and map
			String hpCondition = updateSubjectHp(map);
			//hurt player with damage stunned player has taken in place of player when player is stunned
			stunnedPlayer.heal(); 
			//Restore stunnedPlayer's hitpoints to max hitpoints to get ready for next stun
			retVal = actor + " free " + subject + " from stun as the stun powder loses all its effect. ";
			retVal += System.lineSeparator() + hpCondition;
		}

		else {
			stunStatus -= 1;
			retVal = actor + " does nothing, its stun powder on " + subject + " is slowly losing its effect.";
		}
		return retVal;
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

	/**
	 * Returns an empty string as this action does not have a suitable string to 
	 * be displayed in the UI menu.
	 *
	 * @param actor The actor performing the action
	 * 
	 * @return an empty string
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "";
	}

	/**
	 * Returns an empty string as this action does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}
}
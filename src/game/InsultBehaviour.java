package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * A behaviour and also an action that has 10% chance of insulting another Actor 
 * no matter where the other Actor is. 
 */
public class InsultBehaviour extends Action implements ActionFactory{

	private Actor target;
	private Random rand = new Random();
	private List<String> insults = new ArrayList<String>();

	/**
	 * Constructor that initialises target to the subject given, also adds insults 
	 * of type String into insults List.
	 *
	 * @param subject the actor to be insulted
	 */
	public InsultBehaviour(Actor subject) {
		this.target = subject;
		insults.add("Go away! No one wants to be your friend!");
		insults.add("Ewwww! You are so smelly!");
		insults.add("You are such a loser!");
		insults.add("You look so ugly!");
		insults.add("Hahaha! You can't defeat us!");
	}

	/**
	 * Randomly selects one of the insults in the insults List and return it.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor the actor performing the action
	 * @param map the map the actor is on
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + " insulted " + target + " - " + insults.get(rand.nextInt(insults.size()));
	}
	
	/**
	 * Has 10% chance of returning this action (InsultBahaviour) that shouts an 
	 * insult and 90% chance of returning null.
	 * 
	 * @see ActionFactory#getAction(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return this action (InsultBehaviour) at 10% chance, null otherwise
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if (Math.random() <= 0.1) {
			return this;
		}
		return null;
	}

	/**
	 * Returns an empty string as this action does not have a suitable string to 
	 * be displayed in the UI menu.
	 *
	 * @param actor The actor performing the action
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
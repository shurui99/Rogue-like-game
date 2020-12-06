package game;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * Base class for enemy classes including Grunt, Goon, Ninja and DrMaybe. This 
 * represents an enemy. 
 * The default hitpoints is 50, and default damage of intrinsic weapon is 6
 * An enemy has a list of ActionFactory object that can be
 * proccessed to get Action. During its turn, it will perform Action that matches
 * its behaviour if any, or attack a random adjacent actor if any, or skips turn.
 */
public abstract class Enemy extends Actor {

	private List<ActionFactory> actionFactories; 
	
	/**
	 * Constructor that creates an enemy with name, displayChar and priority 
	 * given, by default, all enemies has 50 hitpoints, also creates a new List 
	 * of actionFactories containing elements of type ActionFactory.
	 * 
	 * @param name Name to call the Enemy in the UI
	 * @param displayChar Character to represent the Enemy in the UI
	 * @param priority How early in the turn the Enemy can act
	 * 
	 */
	public Enemy(String name,char displayChar, int priority) {
		super(name, displayChar, priority, 50);
		actionFactories = new ArrayList<ActionFactory>();
	}

	/**
	 * Adds behaviour which is an ActionFactory object to this enemy's actionFactories
	 *
	 * @param behaviour The enemy's behaviour to be added to actionFactories
	 */
	protected void addBehaviour(ActionFactory behaviour) {
		actionFactories.add(behaviour);
	}
	
	/**
	 * Creates and returns an intrinsic weapon that allows Enemy 'punches' for 
	 * 6 damage.
	 *
	 * @return a freshly-instantiated Intrinsic Weapon of damage 6
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(6, "punches");
	}

	/**
	 * Proccesses actionFactories to get an Action to return. If there is no
	 * Action that matches the Enemy's behaviour, returns a random action allowed 
	 * acting on an adjacent actor, or returns SkipTurnAction if no actions
	 * allowed.
     *
     * @see Actor#playTurn(Actions, GameMap, Display)
     * @param actions Collection of possible Actions for this enemy
	 * @param map The map containing this enemy
	 * @param display The I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, GameMap map, Display display) {
		for (ActionFactory factory : actionFactories) { //process action factories to get an action
			Action action = factory.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		actions = new Actions();
		Location here = map.locationOf(this);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			Actor adjacentActor = destination.getActor();
			if (adjacentActor != null) {
				actions.add(adjacentActor.getAllowableActions(this, exit.getName(), map));
				//add all possible action allowed into actions
			}
		}
		if (actions.size() > 0) { 
			return super.playTurn(actions, map, display); //randomly return an AttackAction
		}
		else { //indicates no adjacent actor to attack
			return new SkipTurnAction();
		}
	}

}

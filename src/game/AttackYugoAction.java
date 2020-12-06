package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * Special Action for attacking YugoMaxx.
 */
public class AttackYugoAction extends AttackAction {

	private YugoMaxx subject;
	private Random rand = new Random();

	/**
	 * Constructor, also initialises actor to actor given and subject to subject 
	 * given.
	 * 
	 * @param actor the actor that attack YugoMaxx
	 * @param subject the YugoMaxx that will be attacked by actor
	 */
	public AttackYugoAction(Actor actor, YugoMaxx subject) {
		super(actor, subject); 
		this.subject = subject;
	}

	/**
	 * Hurts the YugoMaxx at 50% chance, if Yugo becomes unconscious, adds its 
	 * unconscious body to its location on the map and removes Yugo from the map.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) { 
			return actor + " misses " + subject + "."; //50% chance of missing
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + subject + " for " + damage + " damage.";

		subject.hurt(damage);
		if (!subject.isConscious()) {
			Item unconsciousBody = subject.getUnconsciousBody();
			map.locationOf(subject).addItem(unconsciousBody); //drop unconscious body
			map.removeActor(subject); //remove Yugo
			result += System.lineSeparator() + subject + " is knocked out.";
		}

		return result;

	}	
}
package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a YugoMaxx which is an enemy.
 * Yugo's hitpoints is same as the default hitpoints (50), and damage of intrinsic 
 * weapon is twice the default damage (12).
 * It is at least 10 squares away from the rocket on moon and it has an exoskeleton
 * which can only be destroyed with water at 70% chance.
 * It can only be attacked by other actors if its exoskeleton is destroyed.
 * While not standing beside the player, it wanders around the map at random.
 * 
 */
public class YugoMaxx extends Enemy {

	private boolean hasExoskeleton;
	private WaterPistol waterPistol;
	private Item unconsciousBody;

	/**
	 * Constructor, that creates a YugoMaxx object with name given, displayChar 
	 * 'Y' and priority 6, also adds WanderBehaviour to its actionFactories.
	 *
	 * @param name Name to call the YugoMaxx in the UI
	 * @param player The actor that Grunt performs action on
	 * @param waterPistol Water pistol that can be used to destroy Yugo's exoskeleton
	 */
	public YugoMaxx(String name, Actor player, WaterPistol waterPistol) {
		super(name, 'Y', 6);
		this.hasExoskeleton = true;
		this.waterPistol = waterPistol;
		addBehaviour(new WanderBehaviour(player));
		Item unconsciousBody = new Item("unconscious " + this + "'s body", '%');
		this.unconsciousBody = unconsciousBody;
	}

	/**
	 * Sets hasExoskeleton to false to indicate exoskeleton has been destroyed.
	 *
	 */
	public void destroyExoskeleton() {
		hasExoskeleton = false;
	}

	/**
	 * Returns YugoMaxx's unconscious body.
	 *
	 * @return an Item object representing YugoMaxx's unconscious body
	 */
	public Item getUnconsciousBody() {
		return unconsciousBody;
	}

	/**
	 * Creates and returns an intrinsic weapon that allows YogoMaxx 'punches' for 
	 * twice the Enemy's damage.
	 * 
	 * @return a freshly-instantiated IntrinsicWeapon of twice the Enemy's damage
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(super.getIntrinsicWeapon().damage() * 2, "punches");
	}

    /**
     * Returns a collection of the Actions that the otherActor can do to YogoMaxx.
     * If it does not have exoskeleton, the collection contains AttackAction.
     * If it has exoskeleton and the other actor has a filled water pistol, the 
     * collection contains SquirtAction. The collection is empty otherwise.
     *
     * @see Actor#getAllowableActions(Actor, String, GameMap)
     * @param otherActor The Actor that might be performing action on this YugoMaxx
	 * @param direction String representing the direction of the other Actor
	 * @param map The current game map containing YugoMaxx and otherActor
	 * @return a collection of Actions that can be performed on YugoMaxx
	 */
    @Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		if (!hasExoskeleton) {
			return new Actions(new AttackYugoAction(otherActor, this));
			//can only be attacked when yugo has no exeskeleton
		}
		else if (otherActor.getInventory().contains(waterPistol) && !waterPistol.isEmpty()) {
			return new Actions(new SquirtAction(this, waterPistol));
			//actor that has filled water pistol can squirt water on yugo
		}
		return new Actions();
	}
}

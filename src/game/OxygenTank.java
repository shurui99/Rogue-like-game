package game;

import edu.monash.fit2099.engine.*;

/**
 * A class representing an oxygen tank item.
 */
public class OxygenTank extends Item {

	private int oxygenPoint;

	/**
	 * Constructor, it clears its allowable actions then add DropItemAction 
	 * to the actions allowed, also initialise the oxygen point to be 10.
	 */
	public OxygenTank() {
		super("oxygen tank", 't');
		this.allowableActions.clear();
		this.allowableActions.add(new DropItemAction(this));
		//make oxygen tank an inventory item
		this.oxygenPoint = 10;
	}

	/**
	 * Returns the current oxygen point of this oxygen tank.
	 *
	 * @return the oxygen point left for this tank
	 */
	public int getOxygenPoint() {
		return oxygenPoint;
	}

	/**
	 * Decrements the oxygen point of this tank by 1.
	 */
	public void decrementOxygenPoint() {
		oxygenPoint -= 1;
	}

	/**
	 * Returns a string containing the name of oxygen tank and oxygen points left.
	 * 
	 * @return a string with oxygen tank's name and its current oxygen point
	 */
	@Override
	public String toString() {
		return super.toString() + " (oxygen point:" + oxygenPoint + ")";
	}

}
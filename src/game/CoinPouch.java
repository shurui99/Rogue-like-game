package game;

import edu.monash.fit2099.engine.*;

/**
 * A class representing a coin pouch item, it is an inventory item and is associated
 * with coin value that represents the amount of coins it contains. Coins can be
 * added to and withdrawn from the coin pouch.
 */
public class CoinPouch extends Item {

	private int coinValue;

	/**
	 * A constructor, it creates an coin pouch object that contains no coins, also 
	 * clears its allowable actions then add DropItemAction to the actions allowed.
	 * 
	 * @param name Name to call the coin pouch in the UI
	 */
	public CoinPouch(String name) {
		super(name, 'c');
		this.allowableActions.clear();
		this.allowableActions.add(new DropItemAction(this));
		this.coinValue = 0;
	}

	/**
	 * Returns the amount of coins in the coin pouch.
	 *
	 * @return amount of coins in the coin pouch
	 */
	public int getCoinValue() {
		return coinValue;
	}

	/**
	 * Increases the amount of coins in the coin pouch by the amount given.
	 *
	 * @param value amount of coins to be added to the coin pouch
	 */
	public void addCoins(int value) {
		coinValue += value;
	}

	/**
	 * Decreases the amount of coins in the coin pouch by the amount given.
	 *
	 * @param value amount of coins to be withdrawn to the coin pouch
	 */
	public void withdrawCoins(int value) {
		coinValue -= value;
	}

	/**
	 * Returns a string containing the name of the coin pouch and the amount of
	 * coins it contains.
	 * 
	 * @return a string with the name of the coin pouch and the amount of coins 
	 * it contains
	 */
	@Override
	public String toString() {
		return super.toString() + " (" + coinValue + " coin(s))";
	}

}
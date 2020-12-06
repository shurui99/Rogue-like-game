package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Action that visits GameShop which enables Actor to buy Items using 
 * coins in coin pouch.
 */
public class VisitShopAction extends Action {

	private CoinPouch coinPouch;
	private Display display;
	private HashMap <Item, Integer> itemToValueMap = new HashMap<Item, Integer>();

	/**
	 * Constructor, also initialises coinPouch and display to the coin pouch and
	 * the I/O object.
	 *
	 * @param coinPouch the coin pouch
	 * @param display the I/O object to which messages may be written
	 */
	public VisitShopAction(CoinPouch coinPouch, Display display) {
		this.coinPouch = coinPouch;
		this.display = display;
		addItemsToListing();
	} 

	/**
	 * Adds Item and Integer pairs, which corresponds to items sold and their
	 * selling price, to the hashmap.
	 *
	 */
	private void addItemsToListing() {
		Item potion1 = Item.newInventoryItem("HP potion 30", ':');
		potion1.getAllowableActions().add(new UsePotionAction(potion1, 30));
		itemToValueMap.put(potion1, 12);

		Item potion2 = Item.newInventoryItem("HP potion 40", ':');
		potion2.getAllowableActions().add(new UsePotionAction(potion2, 40));
		itemToValueMap.put(potion2, 14);

		Item potion3 = Item.newInventoryItem("HP potion 50", ':');
		potion3.getAllowableActions().add(new UsePotionAction(potion3, 50));
		itemToValueMap.put(potion3, 16);

		Item gauntlet1 = Item.newInventoryItem("gauntlet", '&');
		gauntlet1.getAllowableActions().add(new SnapFingerAction(gauntlet1));
		itemToValueMap.put(gauntlet1, 25);

		Item gauntlet2 = Item.newInventoryItem("gauntlet", '&');
		gauntlet2.getAllowableActions().add(new SnapFingerAction(gauntlet2));
		itemToValueMap.put(gauntlet2, 25);
	}

	/**
	 * Returns the amount of coins in actor's coin pouch. If actor does not have
	 * the coin pouch, amount will be 0.
	 *
	 * @param actor the actor such that the amount of coins in its coin pouch will
	 * be returned
	 * @return amount of coins in actor's coin pouch
	 */
	private int getActorCoinValue(Actor actor) {
		int coinValue = 0;
		if (actor.getInventory().contains(coinPouch)) {
			coinValue = coinPouch.getCoinValue();
		}
		return coinValue;		
	}

	/**
	 * Displays a menu to the user and has them select an option. The menu shows
	 * the total amount of coins in actor's coin pouch and the list of items sold 
	 * with the required amount of coins.
	 *
	 * @param actor actor performing this action
	 * @return an Item that actor choose to buy, null if actor does not buy anything
	 */
	private Item showMenu(Actor actor) {

		int coinValue = getActorCoinValue(actor);
		display.println(actor + "'s coin value: " + coinValue);

		ArrayList<Character> freeChars = new ArrayList<Character>();
		HashMap<Character, Item> keyToItemMap = new HashMap<Character, Item>();

		for (char i = 'a'; i <= 'z'; i++)
			freeChars.add(i);

		for (Item item: itemToValueMap.keySet()) {
			char c = freeChars.get(0);
			freeChars.remove(0);
			keyToItemMap.put(c, item);
			display.println(String.format("%s: %s (%d coins)", c, item, itemToValueMap.get(item))); 
		}

		char c = freeChars.get(0);
		keyToItemMap.put(c, null);
		display.println(c + ": leave the shop");

		char key;
		do {
			key = display.readChar();
		} while(!keyToItemMap.containsKey(key));

		return keyToItemMap.get(key);
	}

	/**
	 * Displays a menu to the user and gets the selected option. If actor choose
	 * to buy an Item, gets the amount of coins in the the actor's coin pouch, and
	 * checks if there is enough coins to buy the selected item. If sufficient,
	 * deducts the amount of coins in coin pouch, adds selected Item to actor's
	 * inventory and removes the Item from the listing from the shop. If actor
	 * does not have sufficient coins or does not buy anything, appropriate string
	 * will be returned.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Item item = showMenu(actor);
		if (item != null) {
			if (getActorCoinValue(actor) < itemToValueMap.get(item)) {
				return String.format(actor + " does not have enough coin to buy " + item);
			}
			int value = itemToValueMap.get(item);
			coinPouch.withdrawCoins(value);
			actor.addItemToInventory(item);
			itemToValueMap.remove(item);
			return String.format("%s buys a %s with %d coins", actor, item, value);	
		}
		return String.format(actor + " leaves the shop without buying anything");
	}	

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, i. e. "Player visits GameShop"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " visits GameShop";
	}

	/**
	 * Returns an empty string as shop visiting does not have a dedicated hotkey.
	 *
	 * @return an empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}

}
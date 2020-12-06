package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that transfers the coins in a small pouch to a coin pouch, then causes
 * the empty small pouch to disappear.
 */
public class AddCoinsAction extends Action {

	private Item smallPouchOfCoins;
	private int coinNum;
	private CoinPouch coinPouch;

	/**
	 * Constructor, also initialises smallPouchOfCoins, coinNum and coinPouch to
	 * the small pouch containing the coins to be transferred, amount of coins in
	 * small pouch and the coin pouch where the coins are to be transferred to
	 * respectively.
	 *
	 * @param smallPouchOfCoins the small pouch containing the coins to be 
	 * transferred
	 * @param coinNum amount of coins in smallPouchOfCoins
	 * @param coinPouch the coin pouch to be added with coinNum coins
	 */
	public AddCoinsAction(Item smallPouchOfCoins, int coinNum, CoinPouch coinPouch) {
		this.smallPouchOfCoins = smallPouchOfCoins;
		this.coinNum = coinNum;
		this.coinPouch = coinPouch;
	} 

	/**
	 * If actor has coin pouch in its inventory, add coins to the pouch, then
	 * makes the empty small pouch disappear by removing from the map or actor's
	 * inventory; otherwise, nothing happens.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (actor.getInventory().contains(coinPouch)) {
			coinPouch.addCoins(coinNum);
			smallPouchOfCoins.getAllowableActions().remove(this);
			map.locationOf(actor).removeItem(smallPouchOfCoins);
			actor.removeItemFromInventory(smallPouchOfCoins);
			return menuDescription(actor);
		}
		else {
			return actor + " does not have a pouch to contain coin";
		}
	}	

	/**
	 * Returns a string describing the action suitable for displaying in the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a string, e.g. "Player adds 5 coins from small pouch of coins to the the pouch"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " add " + coinNum + " coins from " + smallPouchOfCoins + " to the pouch";
	}

	/**
	 * Returns the empty string, as no hotkey is permanently assigned to pickup.
	 * @return the empty string
	 */
	@Override
	public String hotKey() {
		return "";
	}

}
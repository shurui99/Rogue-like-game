package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a StunnedPlayer which replaces a Player when the Player is 
 * stunned.
 * StunnedPlayer can still be attacked by other enemies and can only performs 
 * SkipTurnAction.
 */
public class StunnedPlayer extends Player {

	/**
	 * Constructor that creates a StunnedPlayer object with name and displayChar
	 * given, priority 1 and a high hitpoints.
	 *
	 * @param name Name to call the StunnedPlayer in the UI
	 * @param displayChar Character to represent the player in the UI
	 */
	public StunnedPlayer(String name, char displayChar) {
		super(name, displayChar, 1, Integer.MAX_VALUE);
	}
	
	/**
     * Display a menu to the user and have them select an option. The only option
     * on the menu is SkipTurnAction as StunnedPlayer cannot do anything.
     *
     * @param actions Collection of possible Actions for StunnedPlayer
	 * @param map The map containing the StunnedPlayer
	 * @param display the I/O object to which messages may be written
	 * @return the Action to be performed which is always SkipTurnAction
	 */
	public Action playTurn(Actions actions, GameMap map, Display display) {
		return showMenu(new Actions(new SkipTurnAction()), display);
		//show menu with only one option which is skip turn
	}
	
	/**
	 * Calculates and returns the total of StunnedPlayer's HP that has been deducted, 
	 * which is also the total damage caused by other enemies to Player when it
	 * is stunned.
	 *
	 * @return the damage caused by enemies during StunnedPlayer's lifetime
	 */
	public int getDeductedHp() {
		return this.maxHitPoints - this.hitPoints;
	}

	/**
	 * Restores StunnedPlayer's hitpoints to max hitpoints.
	 */
	public void heal() {
		this.hitPoints = maxHitPoints;
	}
}

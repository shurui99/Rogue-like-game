package game;

import edu.monash.fit2099.engine.*;

/**
 * An action that use gauntlet to snap finger to make all the enemies which are within 
 * 5 squares from the actor performing this action to disappear.
 */
public class SnapFingerAction extends Action{
	
	private Item gauntlet;
	
	/**
	 * Constructor, also initialises gauntlet to the gauntlet given.
	 * 
	 * @param gauntlet Gauntlet that allows this action
	 */
	public SnapFingerAction(Item gauntlet) {
		this.gauntlet = gauntlet;
	}
	
	/**
	 * Remove the subject from the map and drop all its item onto the map,
	 * also replace the subject with a sleeping subject.
	 * 
	 * @param subject Actor that to be made disappear
	 * @param map Map that the subject is on
	 */
	public void makeSubjectDisappear(Actor subject, GameMap map) {
		Item sleepingActor = new Item("Sleeping " + subject, '%');
		map.locationOf(subject).addItem(sleepingActor);
		for (Item item : subject.getInventory()) {
			for (Action action : item.getAllowableActions()) {
				if (action instanceof DropItemAction) {
					action.execute(subject, map);	
					break;
				}
			}
		}
		map.removeActor(subject);
	}
	
	/**
	 * Searches all the locations that are within 5 squares from the actor performing 
	 * this action for enemy, then makes all the enemies found to disappear from 
	 * the map. The gauntlet will then be removed from the player's inventory.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	public String execute(Actor actor, GameMap map) {
		String result = "";
		Location actorLocation = map.locationOf(actor);
		Location startRange = new Location (map, actorLocation.x()-5, actorLocation.y()-5);
		Location endRange = new Location (map, actorLocation.x()+5, actorLocation.y()+5);
		Range xs, ys;
		boolean hasActor;
		xs = new Range(startRange.x(), Math.abs(endRange.x() - startRange.x())+1);
		ys = new Range(startRange.y(), Math.abs(endRange.y() - startRange.y())+1);
		for (int x : xs) {
			for (int y : ys) {
				try {
					hasActor = (map.at(x, y).containsActor());
				}
				catch(IndexOutOfBoundsException e) {
					break;
				}
				if (hasActor) { //indicates there is an actor
					Actor subject = map.at(x, y).getActor();
					if (subject instanceof Enemy) {
						makeSubjectDisappear(subject, map);
						result += subject + " is knocked out.\n";//line separator will print empty line
					}	
				}
			}				
		}
		if (result == "") {
			result = "no enemy is knocked out";
		}
		else {
			result = result.substring(0, result.length() - 1);
		}
		actor.removeItemFromInventory(gauntlet);
		return result;
	}

	/**
	 * Returns a string describing the action suitable for displaying in the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a string, e.g. "Player snaps finger with gauntlet"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " snaps finger with " + gauntlet;
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



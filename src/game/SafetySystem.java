package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing a safety system that keep track of player's oxygen point.
 * When player is on the moonbase, the system will deduct one point of oxygen from
 * player. If the player runs out of oxygen on the moon, the system transports 
 * player back to the rocket's location on Earth.
 */
public class SafetySystem {
	private GameMap moonMap;
	private MoveToEarthAction moveToEarth;
	private Item spacesuit;
	private static OxygenTanks oxygenTanks;
	private OxygenTank currentOxygenTank;
	
	/**
	 * Constructor that initialises moon map, move to earth action, spacesuit 
	 * and oxygen tanks to the parameters given accordingly.
	 *
	 * @param moonMap moon's map
	 * @param moveToEarth action that moves player to rocket's location on earth
	 * and will check if player has won the game
	 * @param spacesuit spacesuit required by player to be on moon base
	 * @param oxygenTanks a list of non-empty oxygen tank that has been produced 
	 * in the game
	 */
	public SafetySystem(GameMap moonMap, MoveToEarthAction moveToEarth,  Item spacesuit, OxygenTanks oxygenTanks) {
		this.moonMap = moonMap;
		this.moveToEarth = moveToEarth;
		this.spacesuit = spacesuit;
		SafetySystem.oxygenTanks = oxygenTanks;

	}


	/**
	 * Gets a non-empty oxygen tank that can be found in player's inventory.
	 *
	 * @param player that the safety system checks on
	 * @return an non-empty oxygen tank that can be found in player's inventory, 
	 * null if there is no such oxygen tank
	 */
	private OxygenTank retrieveOxygenTank(Actor player) {
		for (OxygenTank oxygenTank : oxygenTanks) {
			if (player.getInventory().contains(oxygenTank)) { 
			//look for a non-empty oxygen tank in player's inventory
				OxygenTank newOxygenTank = oxygenTank;
				return newOxygenTank;
			}
		}
		return null;
	}

	/**
	 * Checks if player still has any non-empty oxygen tank in its inventory and
	 * manages OxygenTanks.
	 * If the current oxygen tank being used by player is null or empty, or it is 
	 * not in player's inventory anymore, gets a non-empty oxygen tank that can 
	 * be found in player's inventory to use it as the current oxygen tank. The
	 * empty current oxygen tank will also be removed from OxygenTanks as it only
	 * stores non-empty oxygen tanks.
	 * If the currentOxygenTank is still null, it means that player does not have 
	 * any oxygen tank left, thus returns false, returns true otherwise.
	 * 
	 * @param player that the safety system checks on
	 * @return true if player still has any non-empty oxygen tank in its inventory, 
	 * false otherwise
	 */
	private boolean hasOxygen(Actor player) {
		if (currentOxygenTank == null || !player.getInventory().contains(currentOxygenTank)) {
			currentOxygenTank = retrieveOxygenTank(player);
		}
		else if (currentOxygenTank.getOxygenPoint() == 0) {
			oxygenTanks.remove(currentOxygenTank);
			currentOxygenTank = retrieveOxygenTank(player);
		}
		if (currentOxygenTank == null) { //indicates player has no more oxygen left
			return false;
		}
		return true;
	}

	/**
	 * Runs the safety system. The system will only do something if player is on 
	 * moon. 
	 * If player is on moon, it will check if the player's inventory contains 
	 * spacesuit and oxygen tank that has oxygen point more 0.
	 * If either one or both of these 2 conditions are not met, moves the player 
	 * back to the earth, also checks if player has won the game;
	 * else, decrement the oxygen point of the chosen oxygen tank in player's 
	 * inventory by 1.
	 * 
	 * @param player the player that the safety system checks on
	 * @return string indicating player is moved to earth by safety system or null
	 * if safety system does not do anything
	 */
	public String run(Actor player) {
		if (moonMap.locationOf(player).map() == moonMap) {
			if (!hasOxygen(player) || !player.getInventory().contains(spacesuit)) {
				moveToEarth.execute(player, moonMap);
				//move player to earth rocket's location
				return player + " is moved to Earth by the safety system";				
			}
			else {
				currentOxygenTank.decrementOxygenPoint(); 
				//deduct one oxygen point from player
			}
		}
		return null;
	}

}

package game;

import java.util.*;

/**
 * A thin wrapper for OxygenTank to added with oxygen tanks produced by oxygen
 * dispenser(s). When a oxygen tank in OxygenTanks becomes empty, it will be removed
 * from OxygenTanks.
 */
public class OxygenTanks implements Iterable<OxygenTank> {

	private List<OxygenTank> oxygenTanks;

	/**
	 * Constructor, also initialises the oxygenTanks to an empty ArrayList that
	 * can contain elements of type OxygenTank.
	 */
	public OxygenTanks() {
		this.oxygenTanks = new ArrayList<OxygenTank>();
	}

	/**
	 * Adds an oxygen tank given to oxygenTanks.
	 * 
	 * @param oxygenTank An oxygen tank to be added into oxygenTanks
	 */
	public void add(OxygenTank oxygenTank) {
		oxygenTanks.add(oxygenTank);
	}

	/**
	 * Removes an oxygen tank given from the oxygenTanks.
	 * 
	 * @param oxygenTank An oxygen tank to be removed from oxygenTanks
	 */
	public void remove(OxygenTank oxygenTank) {
		oxygenTanks.remove(oxygenTank);
	}

	/**
	 * Returns a read-only Iterator for a copy of the ArrayList containing oxygen 
	 * tanks. The means add() and remove() can be called while iterating without 
	 * modifiying the iterated collection.
	 * 
	 * @return an iterator
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<OxygenTank> iterator() {
		return Collections.unmodifiableList(new ArrayList<OxygenTank>(oxygenTanks)).iterator();
	}
}
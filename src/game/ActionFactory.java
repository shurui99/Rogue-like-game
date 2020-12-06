package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Interface for behaviour classes to be added to Actor to get actions in return.
 */
public interface ActionFactory {
	Action getAction(Actor actor, GameMap map);
}

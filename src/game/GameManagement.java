package game;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * Manages the game by setting up different requirements and settings of the game.
 */
public class GameManagement {

	private GameWorld world;
	private Display display;
	private GameMap earth;
	private GameMap moon;
	private Player player;
	private Location earthRocketLocation;
	private Location moonRocketLocation;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Item key1a;
	private Item key1b;
	private Item key2a;
	private Item key2b;
	private Item rocketPlans;
	private Item rocketBody;
	private Item rocketEngine;
	private Item spacesuit;
	private YugoMaxx yugo;
	private WaterPistol waterPistol;
	private OxygenTanks oxygenTanks;
	
	/**
	 * Constructor, also creates an I/O object, game world, ground factory, earth 
	 * map, moon map and player of this game. The maps and player are then added 
	 * to the game world.
	 */
	public GameManagement() {
		display = new Display();
		world = new GameWorld(display);

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Floor(), 
					new Wall(), new Door(), new RocketPad(), new Water(), 
					new OxygenDispenser());

		List<String> earthMap = Arrays.asList(
				"........................",
				"....######....######....",
				"....#....#....#....#....",
				"....#....#....#....#....",
				"....######....######....",
				"........................",
				"........................",
				"........................",
				"~~......................",
				"~~~~~...................",
				"~~~~~~..................");
		earth = new GameMap(groundFactory, earthMap);
		world.addMap(earth);

		List<String> moonMap = Arrays.asList(
			"...............",
			"...............",
			"...............", 
			"...............", 
			"...............",
			"...............",
			"...............",
			"...............",
			"...............",
			"..............."
			);
		moon = new GameMap(groundFactory, moonMap);
		world.addMap(moon);

		player = new Player("Player", '@', 1, 300);
		world.addPlayer(player, earth, 10, 22);
	}

	/**
	 * Sets up the earth's rocket location and moon's rocket location on respective 
	 * maps, also creates rocket plans, rocket body and rocket engine. 
	 */
	private void prepareRocketRequirement() {
		earthRocketLocation = earth.at(23, 10);
		moonRocketLocation = moon.at(1, 1);

		rocketPlans = new Item("rocket plans", 'p');
		rocketBody = Item.newInventoryItem("rocket body", 'b');
		rocketEngine = Item.newInventoryItem("rocket engine",'e');
	}
	
	/**
	 * Creates 2 different doors and locates them to the earth map, also creates 
	 * keys that matches either one of the doors.
	 */
	private void setUpDoorsAndKeys() {
		key1a = Item.newInventoryItem("key(1)",'k');
		key1b = Item.newInventoryItem("key(1)",'k');	
		key2a = Item.newInventoryItem("key(2)",'k');	
		key2b = Item.newInventoryItem("key(2)",'k');
		
		Door door1 = new Door();
		(earth.at(7, 4)).setGround(door1);
		door1.addKey(key1a);
		door1.addKey(key1b);

		Door door2 = new Door();
		(earth.at(16, 4)).setGround(door2);
		door2.addKey(key2a);
		door2.addKey(key2b);
		
	}
	
	/**
	 * Sets up the items needed before going to the moon as well as the equipments 
	 * that provides the items, i.e. creates a spacesuit and oxygen dispenser and 
	 * adds them to the earth map.
	 */
	private void setUpMoonRequirements() {
		spacesuit = new Item("spacesuit", 's');
		earth.addItem(spacesuit, 22, 10);

		oxygenTanks = new OxygenTanks();
		Location oxygenDispenserLocation = earth.at(21, 10);
		OxygenDispenser dispenser = new OxygenDispenser(oxygenTanks, oxygenDispenserLocation);
		oxygenDispenserLocation.setGround(dispenser);
	}

	/**
	 * Adds a pool of a water to the earth map, also creates a water pistol that
	 * can be filled with water from this pool of water.
	 */
	private void setUpPool() {
		waterPistol = new WaterPistol("water pistol");

        Water water1 = new Water(waterPistol);
        (earth.at(0, 8)).setGround(water1);
        Water water2 = new Water(waterPistol);
        (earth.at(1, 8)).setGround(water2);
        Water water3 = new Water(waterPistol);
        (earth.at(2, 9)).setGround(water3);
        Water water4 = new Water(waterPistol);
        (earth.at(3, 9)).setGround(water4);
        Water water5 = new Water(waterPistol);
        (earth.at(4, 9)).setGround(water5);
        Water water6 = new Water(waterPistol);
        (earth.at(5, 10)).setGround(water6);
	}

	/**
	 * Creates and adds all the enemies and allies(Q) onto the earth map, also 
	 * distributes different keys to some enemies, adds rocket body and rocket 
	 * plans into Q's inventory and adds rocket engine into Dr Maybe's inventory.
	 * 
	 */
	private void setUpEarthAlliesAndEnemies() {
		Q q1 = new Q("Q", player, rocketBody, rocketPlans);
		earth.addActor(q1, 19, 8);

		Grunt grunt1 = new Grunt("Grunt1", player);
		earth.addActor(grunt1, 0, 0);
		grunt1.addItemToInventory(key1a);

		Grunt grunt2 = new Grunt("Grunt2", player);
		earth.addActor(grunt2,  10, 10);
		grunt2.addItemToInventory(key2a);

		Goon goon1 = new Goon("Goon1", player);
		earth.addActor(goon1, 3, 3);
		goon1.addItemToInventory(key1b);

		Ninja ninja1 = new Ninja("Ninja1", player, world);
		earth.addActor(ninja1, 6, 6);
		ninja1.addItemToInventory(key2b);

		DrMaybe maybe = new DrMaybe("Doctor Maybe", rocketEngine);
		earth.addActor(maybe, 5, 2);

		enemies.add(grunt1);
		enemies.add(grunt2);
		enemies.add(goon1);
		enemies.add(ninja1);
		enemies.add(maybe);
	}

	/**
	 * Sets up the game shop on the earth map which allows actors to visit it, also
	 * creates and adds a coinPouch into player's inventory to store coins. This 
	 * method also creates and distributes a small pouch of coins to all the enemies. 
	 * The small pouch of coins allows player to add 5 coins into his coin pouch.
	 */
	private void setUpMerchant() {
		CoinPouch coinPouch = new CoinPouch("coin pouch");
		player.addItemToInventory(coinPouch);
		for (Enemy enemy : enemies) {
			Item coin = Item.newInventoryItem("small pouch of coins", '$');
			coin.getAllowableActions().add(new AddCoinsAction(coin, 5, coinPouch));
			enemy.addItemToInventory(coin);
		}
		Item shop = Item.newFurniture("GameShop", '!');
		shop.getAllowableActions().add(new VisitShopAction(coinPouch, display));
		earth.addItem(shop, 22, 8);
	}

	/**
	 * Creates and adds all the enemies onto the moon map.
	 */
	private void setUpMoonEnemies() {
		Grunt grunt3 = new Grunt("Grunt3", player);
		moon.addActor(grunt3, 0, 3);

		Goon goon2 = new Goon("Goon3", player);
		moon.addActor(goon2, 10, 4);

		Ninja ninja2 = new Ninja("Ninja3", player, world);
		moon.addActor(ninja2, 1, 6);

		yugo = new YugoMaxx("Yugo Maxx", player, waterPistol);
		moon.addActor(yugo, 9, 9);

		enemies.add(grunt3);
		enemies.add(goon2);
		enemies.add(ninja2);
		enemies.add(yugo);
	}

	/**
	 * Sets up a safety system that automatically transports the player back to 
	 * the rocket's location on Earth if player does not have the requirements 
	 * needed to stay on moon. 
	 */
	private void setUpSafetySystem() {
		MoveToEarthAction moveToEarth = new MoveToEarthAction(earthRocketLocation, yugo, world);
		SafetySystem safetySystem = new SafetySystem(moon, moveToEarth, spacesuit, oxygenTanks);
		world.setSafetySystem(safetySystem);
	}

	/**
	 * Sets up the earth environment by calling the methods used to create the 
	 * objects and actors on earth.
	 */
	private void setUpEarthEnvironment() {
		setUpDoorsAndKeys();
		earth.addItem(rocketPlans, 18, 2);
		setUpMoonRequirements();
		setUpPool();
		setUpEarthAlliesAndEnemies();
	}

	/**
	 * Sets up the moon environment by calling the methods used to create the 
	 * system, objects and actors mooh.
	 */
	private void setUpMoonEnvironment() {
		moon.addItem(waterPistol, 5, 6);
		setUpMoonEnemies();
		setUpSafetySystem();
		
	}
	
	/**
	 * Creates rockets on the earth and moon that allow player to move between
	 * the earth map and moon map, also instantiates earth's rocket pad and moon's
	 * rocket pad, then assigns them to the earth's rocket location and moon's 
	 * rocket location respectively.
	 */
	private void enableTransportBetweenMaps() {
		Item earthRocket = Item.newFurniture("rocket", '^');
		earthRocket.getAllowableActions().add(new MoveActorAction(moonRocketLocation, "to Moon! "));
		
		Item moonRocket = Item.newFurniture("rocket", '^');
		moonRocket.getAllowableActions().add(new MoveToEarthAction(earthRocketLocation, yugo, world));
		moon.addItem(moonRocket, moonRocketLocation.x(), moonRocketLocation.y());
		
		RocketPad earthRocketPad = new RocketPad(rocketBody, rocketEngine, earthRocket, player);
		earthRocketLocation.setGround(earthRocketPad);
		RocketPad moonRocketPad = new RocketPad(player);
		moonRocketLocation.setGround(moonRocketPad);
	}
		
	/**
	 * Creates a quitButton item that allows player to perform QuitGameAction to 
	 * quit the game. This button will be added to player's inventory so player 
	 * can choose to quit the game whenever player wants to.
	 */
	private void enableQuitGame() {
		Item quitButton = Item.newFurniture("quit button", 'x');
		quitButton.getAllowableActions().add(new QuitGameAction(world));
		player.addItemToInventory(quitButton);
	}
	
	/**
	 * Sets up the game by calling the methods used to set up different parts of 
	 * the game, then returns the reference to the game world.
	 * 
	 * @return the game world all actors are in
	 */
	public GameWorld setUpGame() {
		prepareRocketRequirement();
		setUpEarthEnvironment();
		setUpMoonEnvironment();
		enableTransportBetweenMaps();
		setUpMerchant();
		enableQuitGame();
		return world;
	}
}

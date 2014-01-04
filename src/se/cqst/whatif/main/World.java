//	TODO: Cohersion
package se.cqst.whatif.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The World class is the representation of the world in-game. This class contains all information about
 * what state the world is in, where every {@link GenericObject} is located and what state they are in.</p>
 * 
 * <p>World is a Singleton object and should be accessed through {@code World.getInstance()}, upon calling that
 * creates a new instance of World.</p>
 * @see {@link ItemStore} (interface), {@link Serializable} (interface)
 */
public class World implements ItemStore, Serializable {
	
	/*
	 * 							Static Variables
	 */
	
	private static final long 	serialVersionUID = -3766562480367208771L;
	private static World 		instance = null;
//	private static int 			counter = 0;
	
	/*
	 * 							Variables
	 */
	
	private String 				identifier;
	private Configuration 		worldConfig;
	private List<Item> 			itemList;
	private List<Room> 			roomList;
	private Room 				currentRoom = null;
	
	private final String		CONF_FILEPATH		=	"/se/cqst/whatif/resources/config.properties";
	private final String		DICT_FILEPATH		=	"/se/cqst/whatif/resources/dict_en.properties";
	private final String		ROOM_FILEPATH		=	"/se/cqst/whatif/resources/rooms.properties";
	private final String		CONT_FILEPATH		=	"/se/cqst/whatif/resources/containers.properties";
	private final String		ITEM_FILEPATH		=	"/se/cqst/whatif/resources/items.properties";
	private final String		CONN_FILEPATH		=	"/se/cqst/whatif/resources/roomconnectors.properties";

	/*
	 * 							Constructors
	 */
	
	private 					World()
	{
		CmdLib.writeLog("INFO", "Starting new game...");
		this.setWorldConfig(new Configuration(CONF_FILEPATH, DICT_FILEPATH, ROOM_FILEPATH, CONT_FILEPATH, ITEM_FILEPATH, CONN_FILEPATH));
		this.identifier = "WORLD";
		this.itemList = new ArrayList<Item>();
		this.roomList = new ArrayList<Room>();
		WorldLoader.loadWorld(this);
		this.init();
		
//		this.init();
	}
	
	/*
	 * 							Basic get Methods
	 */
	
	public Room 				getCurrentRoom()				{	return this.currentRoom;	}
	public List<Room> 			getRoomList() 					{	return this.roomList;		}
	public Configuration 		getWorldConfig() 				{	return this.worldConfig;	}
	public String 				toString()						{	return this.identifier;		}
	
	/*
	 * 							Basic set Methods
	 */
	
	public void 				setCurrentRoom(Room room)		{	this.currentRoom=room;		}
	
	/*
	 * 							Other Methods
	 */
	
	public void 				init()
	{
		//	Enter currentRoom
		Room.enterRoom(this.getCurrentRoom());
	}
	
	
	
	/*
	 * 							Interface Methods - ItemStore
	 */
	
	public Item 				getItem(String identifier)
	{
		for(Item thing : this.getItemList())
		{
			if(thing.toString().equalsIgnoreCase(identifier))
				return thing;
		}
		return null;
	}
	
	public void 				putItem(Item thing)
	{
		this.getItemList().add(thing);
	}
	
	public List<Item> 			getItemList()
	{
		return this.itemList;
	}
	
	/*
	 * 			Static Methods
	 */
	
	public static World 		getInstance()
	{
		if(instance==null)
			instance = new World();
		return instance;
	}
	
	private static void			setInstance(World world)
	{
		World.instance = world;
	}
	
	public static void 			loadInstance(World loadedInstance)
	{
		//	TODO: Write working loadInstance();
		
		//	Set instance to loaded object
		World.setInstance(loadedInstance);
		
		//	Enter Room
		Room.enterRoom(loadedInstance.getCurrentRoom());
	}
	
	public static void 			saveInstance()
	{
		//	TODO: Write working saveInstance();
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public void setWorldConfig(Configuration worldConfig) {
		this.worldConfig = worldConfig;
	}

}

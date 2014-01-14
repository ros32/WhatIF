//	TODO: Cohersion
package se.cqst.whatif.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>The World class is the representation of the world in-game. This class contains all information about
 * what state the world is in, where every {@link GenericObject} is located and what state they are in.</p>
 * 
 * <p>World is a Singleton object and should be accessed through {@code World.getInstance()}, upon calling that
 * creates a new instance of World.</p>
 * @see {@link ItemStore} (interface), {@link Serializable} (interface)
 */
public class World {
	
	/*
	 * 							Static Variables
	 */
	
	/*
	 * 							Variables
	 */
	
	private List<Room> 			roomList;
	private List<Actor>			actorList;

	/*
	 * 							Constructors
	 */

	public					World(List<Room> roomList, List<Actor> actorList)
	{
		this.roomList=roomList;
		this.actorList=actorList;
	}
	
	public World(String name, String identifier, List<Configuration> configList)
	{
		roomList = new ArrayList<Room>();
		actorList = new ArrayList<Actor>();
		
		Configuration 	actorConfig = null,
						roomConfig = null, 
						containerConfig = null, 
						itemConfig = null, 
						connectionConfig = null;
		
		try
		{
			actorConfig = getConfig(configList, Configuration.Type.ACTOR_CONFIG);
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "No configuration file for type \"" + Configuration.Type.ACTOR_CONFIG.toString() + "\" found!");
			System.exit(-1);
		}
		
		try
		{
			roomConfig = getConfig(configList, Configuration.Type.ROOM_CONFIG);
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "No configuration file for type \"" + Configuration.Type.ROOM_CONFIG.toString() + "\" found!");
			System.exit(-1);
		}
		
		try
		{
			containerConfig = getConfig(configList, Configuration.Type.CONTAINER_CONFIG);
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "No configuration file for type \"" + Configuration.Type.CONTAINER_CONFIG.toString() + "\" found!");
			System.exit(-1);
		}
		
		try
		{
			itemConfig = getConfig(configList, Configuration.Type.ITEM_CONFIG);
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "No configuration file for type \"" + Configuration.Type.ITEM_CONFIG.toString() + "\" found!");
			System.exit(-1);
		}
		
		try
		{
			connectionConfig = getConfig(configList, Configuration.Type.ROOMCONN_CONFIG);
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "No configuration file for type \"" + Configuration.Type.ROOMCONN_CONFIG.toString() + "\" found!");
			System.exit(-1);
		}
		
		roomList = roomCreator(roomConfig, roomList);
		actorList = actorCreator(actorConfig, roomList, actorList);
		roomList = containerCreator(containerConfig, roomList);
		roomList = itemCreator(itemConfig, roomList, actorList);
		roomList = roomConnectionCreator(connectionConfig, roomList);
		
	}
	
	/*
	 * 							Basic get Methods
	 */
	
	public List<Room> 			getRoomList() 					{	return this.roomList;		}
	
	/*
	 * 							Basic set Methods
	 */
	
	/*
	 * 							Other Methods
	 */
	
	/*
	 * 							Interface Methods - ItemStore
	 */

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public List<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	
	public void use()
	{
		//	Cannot use() a World object
	}
	
	public void use(GenericObject object)
	{
		//	Cannot use(GenericObject a World object
	}

	
	
	private static Configuration getConfig(List<Configuration> configList, Configuration.Type type)
	{
		for(Configuration config : configList)
		{
			if(config.getType() == type)
				return config;
		}
		throw new NullPointerException();
	}
	
	private static List<Actor> actorCreator(Configuration actorConfig, List<Room> roomList, List<Actor> actorList)
	{
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating actor...");
		Enumeration<?> e = Configuration.filterProperties(actorConfig.getConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String actorID = (String) e.nextElement();
			actorID = actorID.replace("_NAME","");
			String actorName = actorConfig.getProperty(actorID + "_NAME");
			String actorLoc = actorConfig.getProperty(actorID + "_LOCATION");
			if(findRoom(roomList, actorLoc) != null)
			{
				actorList.add(new Actor(actorName, actorID, findRoom(roomList, actorLoc)));
				counter++;
			}
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " actors.");
		return actorList;
	}
	
	/**
	 * Create Room objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static List<Room> roomCreator(Configuration roomConfig, List<Room> roomList)
	{	
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating rooms...");
		Enumeration<?> e = Configuration.filterProperties(roomConfig.getConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String roomID = (String) e.nextElement();
			roomID = roomID.replace("_NAME","");
			String roomName = roomConfig.getProperty(roomID + "_NAME");
			String roomDesc = roomConfig.getProperty(roomID + "_DESC");
			String roomEnv = roomConfig.getProperty(roomID + "_ENV");
			roomList.add(new Room(roomName, roomID, roomDesc, roomEnv));
			counter++;
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " rooms.");
		return roomList;
	}
	
	/**
	 * Create Container objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static List<Room> containerCreator(Configuration containerConfig, List<Room> roomList)
	{
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating containers...");
		Enumeration<?> e = Configuration.filterProperties(containerConfig.getConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String containerID = (String) e.nextElement();
			containerID = containerID.replace("_NAME","");
			String containerName = containerConfig.getProperty(containerID + "_NAME");
			String containerDesc = containerConfig.getProperty(containerID + "_DESC");
			String containerLoc = containerConfig.getProperty(containerID + "_LOCATION");
			if(findRoom(roomList,containerLoc) != null)
			{
				findRoom(roomList,containerLoc).getContainerList().add(new Container(containerName,containerID));
				findRoom(roomList,containerLoc).getContainer(containerID).setDescription(containerDesc);
				counter++;
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Container location " + containerLoc + " does not exist.");
			}

		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " containers.");
		return roomList;
		
	}
	
	/**
	 * Create Item objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static List<Room> itemCreator(Configuration itemConfig, List<Room> roomList, List<Actor> actorList)
	{		
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating items...");
		Enumeration<?> e = Configuration.filterProperties(itemConfig.getConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String itemID = (String) e.nextElement();
			itemID = itemID.replace("_NAME","");
			String itemName = itemConfig.getProperty(itemID + "_NAME");
			String itemType = itemConfig.getProperty(itemID + "_TYPE");
			String itemDesc = itemConfig.getProperty(itemID + "_DESC");
			String itemLoc = itemConfig.getProperty(itemID + "_LOCATION");
			if(findItemStore(roomList, actorList, itemLoc) != null)
			{
				if(itemType.equalsIgnoreCase("staticitem"))
				{
					findItemStore(roomList, actorList, itemLoc).putItem(new StaticItem(itemName,itemID));
					findItemStore(roomList, actorList, itemLoc).getItem(itemID).setDescription(itemDesc);
					counter++;
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					findItemStore(roomList, actorList, itemLoc).putItem(new MovableItem(itemName,itemID));
					findItemStore(roomList, actorList, itemLoc).getItem(itemID).setDescription(itemDesc);
					counter++;
				}
				else
				{
					CmdLib.writeLog("DEBUG", "Unknown Item type \"" + itemType + "\" for object " + itemID + ".");
				}
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Item location " + itemLoc + " does not exist.");
			}
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " items.");
		return roomList;
	}
	
	/**
	 * Create Room Connector objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static List<Room> roomConnectionCreator(Configuration connectionConfig, List<Room> roomList)
	{		
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating room connections...");
		Enumeration<?> e = Configuration.filterProperties(connectionConfig.getConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String connectorID = (String) e.nextElement();
			connectorID = connectorID.replace("_NAME","");
			String connectorName 	= connectionConfig.getProperty(connectorID + "_NAME");
//			String connectorDesc 	= connectionConfig.getProperty(connectorID + "_DESC");
//			String connectorPrefix 	= connectionConfig.getProperty(connectorID + "_PREFIX");
			String connectorDir 	= connectionConfig.getProperty(connectorID + "_DIRECTION");
			String connectorOrigin 	= connectionConfig.getProperty(connectorID + "_ORIGIN");
			String connectorTarget 	= connectionConfig.getProperty(connectorID + "_TARGET");
			if(findRoom(roomList,connectorOrigin) == null)
			{
				System.out.println("Invalid Origin \"" + connectorOrigin + "\" for connector: " + connectorID);
				continue;
			}
			if(findRoom(roomList,connectorTarget) == null)
			{
				System.out.println("Invalid Target \"" + connectorTarget + "\" for connector: " + connectorID);
				continue;
			}
			if(connectorDir != null)
			{
				try
				{
					Room.Direction dir = Room.Direction.valueOf(connectorDir.toUpperCase());
					Room.connect(findRoom(roomList,connectorOrigin), connectorName, connectorID, findRoom(roomList,connectorTarget), dir);
//					findRoom(roomList,connectorOrigin).getRoomConnection(dir).setDescription(connectorDesc);
					counter++;	
				}
				catch(NullPointerException ex)
				{
					CmdLib.writeLog("WARNING", "Invalid direction for RoomConnector: " + connectorID);
				}
			}
			else
			{
				System.out.println("Invalid connector: " + connectorID);
			}
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " room connections.");
		return roomList;
		
	}
	
	private static Room findRoom(List<Room> roomList, String identifier)
	{
		for(Room place : roomList)
		{
			if(place.toString().equalsIgnoreCase(identifier))
				return place;
		}
		return null;
	}
	
	private static ItemStore findItemStore(List<Room> roomList, List<Actor> actorList, String identifier)
	{
		for(Actor player : actorList)
		{
			if(player.toString().equalsIgnoreCase(identifier))
				return (ItemStore) player;
		}
		for(Room place : roomList)
		{
			if(place.toString().equalsIgnoreCase(identifier))
				return (ItemStore) place;
			for(Container holder : place.getContainerList())
			{
				if(holder.toString().equalsIgnoreCase(identifier))
					return (ItemStore) holder;
			}
		}
		return null;
	}



	
}

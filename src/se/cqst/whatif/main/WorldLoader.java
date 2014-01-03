package se.cqst.whatif.main;

import java.util.Enumeration;

public class WorldLoader {
	
	public static final String	CONF_FILEPATH		=	"/se/cqst/whatif/resources/config.properties";
	public static final String	DICT_FILEPATH		=	"/se/cqst/whatif/resources/dict_en.properties";
	public static final String	ROOM_FILEPATH		=	"/se/cqst/whatif/resources/rooms.properties";
	public static final String	ZONE_FILEPATH		=	"/se/cqst/whatif/resources/zones.properties";
	public static final String	CONT_FILEPATH		=	"/se/cqst/whatif/resources/containers.properties";
	public static final String	ITEM_FILEPATH		=	"/se/cqst/whatif/resources/items.properties";
	public static final String	CONN_FILEPATH		=	"/se/cqst/whatif/resources/roomconnectors.properties";
	
	private WorldLoader()	{}
	
	/**
	 * Load configuration files for the World Configuration object
	 *
	 * @param world Target World object
	 */
	public static void loadConfigs(World world)
	{
		//	Global configuration file
		CmdLib.writeLog("INFO", "Loading global settings from " + TextLib.CONF_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setWorldConfig(CmdLib.loadProperties(World.class.getResourceAsStream(CONF_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + CONF_FILEPATH);
			System.exit(-1);
		}
	
		//	Dictionary file
		CmdLib.writeLog("INFO", "Loading dictionary from " + DICT_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setDictConfig(CmdLib.loadProperties(World.class.getResourceAsStream(DICT_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded.");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + DICT_FILEPATH);
			System.exit(-1);
		}
	
		//	Room file
		CmdLib.writeLog("INFO", "Loading rooms from " + ROOM_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setRoomConfig(CmdLib.loadProperties(World.class.getResourceAsStream(ROOM_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded.");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + ROOM_FILEPATH);
			System.exit(-1);
		}
		
		//	Container file
		CmdLib.writeLog("INFO", "Loading containers from " + CONT_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setContainerConfig(CmdLib.loadProperties(World.class.getResourceAsStream(CONT_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded.");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + CONT_FILEPATH);
			System.exit(-1);
		}
	
		//	Item file
		CmdLib.writeLog("INFO", "Loading items from " + ITEM_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setItemConfig(CmdLib.loadProperties(World.class.getResourceAsStream(ITEM_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded.");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + ITEM_FILEPATH);
			System.exit(-1);
		}
	
		//	Room Connections file
		CmdLib.writeLog("INFO", "Loading room connections from " + CONN_FILEPATH + "...");
		try
		{
			world.getWorldConfig().setConnectorConfig(CmdLib.loadProperties(World.class.getResourceAsStream(CONN_FILEPATH)));	
			CmdLib.writeLog("INFO", "Configuration file loaded.");
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + CONN_FILEPATH);
			System.exit(-1);
		}
	}
	
	/**
	 * Create Room objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	public static void roomCreator(World world)
	{	
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating rooms...");
		Enumeration<?> e = CmdLib.filterProperties(world.getWorldConfig().getRoomConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String roomID = (String) e.nextElement();
			roomID = roomID.replace("_NAME","");
			String roomName = CmdLib.getProperty(world.getWorldConfig().getRoomConfig(), roomID + "_NAME");
			String roomDesc = CmdLib.getProperty(world.getWorldConfig().getRoomConfig(), roomID + "_DESC");
			String roomEnv = CmdLib.getProperty(world.getWorldConfig().getRoomConfig(), roomID + "_ENV");
			world.getRoomList().add(new Room(roomName, roomID));
			world.getWorldRoom(roomID).setDescription(roomDesc);
			world.getWorldRoom(roomID).setEnvironment(roomEnv);
			counter++;
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " rooms.");
		
	}
	
	/**
	 * Create Item objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	public static void itemCreator(World world)
	{		
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating items...");
		Enumeration<?> e = CmdLib.filterProperties(world.getWorldConfig().getItemConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String itemID = (String) e.nextElement();
			itemID = itemID.replace("_NAME","");
			String itemName = CmdLib.getProperty(world.getWorldConfig().getItemConfig(), itemID + "_NAME");
			String itemType = CmdLib.getProperty(world.getWorldConfig().getItemConfig(), itemID + "_TYPE");
			String itemDesc = CmdLib.getProperty(world.getWorldConfig().getItemConfig(), itemID + "_DESC");
			String itemLoc = CmdLib.getProperty(world.getWorldConfig().getItemConfig(), itemID + "_LOCATION");
			if(world.getWorldItemStore(itemLoc) != null)
			{
				if(itemType.equalsIgnoreCase("staticitem"))
				{
					world.getWorldItemStore(itemLoc).putItem(new StaticItem(itemName,itemID));
					world.getWorldItemStore(itemLoc).getItem(itemID).setDescription(itemDesc);
					counter++;
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					world.getWorldItemStore(itemLoc).putItem(new MovableItem(itemName,itemID));
					world.getWorldItemStore(itemLoc).getItem(itemID).setDescription(itemDesc);
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
	}
	
	/**
	 * Create Room Connector objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	public static void roomConnectionCreator(World world)
	{		
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating room connections...");
		Enumeration<?> e = CmdLib.filterProperties(world.getWorldConfig().getConnectorConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String connectorID = (String) e.nextElement();
			connectorID = connectorID.replace("_NAME","");
			String connectorName = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_NAME");
			String connectorDesc = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_DESC");
			String connectorPrefix = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_PREFIX");
			String connectorDir = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_DIRECTION");
			String connectorOrigin = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_ORIGIN");
			String connectorTarget = CmdLib.getProperty(world.getWorldConfig().getConnectorConfig(), connectorID + "_TARGET");
			if(world.getWorldRoom(connectorOrigin) == null)
			{
				System.out.println("Invalid Origin \"" + connectorOrigin + "\" for connector: " + connectorID);
				continue;
			}
			if(world.getWorldRoom(connectorTarget) == null)
			{
				System.out.println("Invalid Target \"" + connectorTarget + "\" for connector: " + connectorID);
				continue;
			}
			if(connectorName != null && connectorDesc != null && connectorPrefix != null && connectorDir != null && connectorOrigin != null && connectorTarget != null)
			{
				if(Room.isValidDirection(connectorDir.toLowerCase()))
				{
					Room.connect(world.getWorldRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, world.getWorldRoom(connectorTarget), connectorDir.toLowerCase());
					world.getWorldRoom(connectorOrigin).getRoomConnection(connectorDir.toLowerCase()).setDescription(connectorDesc);
					counter++;	
				}
			}
			else
			{
				System.out.println("Invalid connector: " + connectorID);
			}
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " room connections.");
		
	}
	
	/**
	 * Create Container objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	public static void containerCreator(World world)
	{
		int counter = 0;
		CmdLib.writeLog("INFO", "Creating containers...");
		Enumeration<?> e = CmdLib.filterProperties(world.getWorldConfig().getContainerConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String containerID = (String) e.nextElement();
			containerID = containerID.replace("_NAME","");
			String containerName = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_NAME");
			String containerDesc = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_DESC");
			String containerLoc = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_LOCATION");
			if(world.getWorldRoom(containerLoc) != null)
			{
				world.getWorldRoom(containerLoc).getContainerList().add(new Container(containerName,containerID));
				world.getWorldRoom(containerLoc).getContainer(containerID).setDescription(containerDesc);
				counter++;
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Container location " + containerLoc + " does not exist.");
			}

		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " containers.");
		
	}

}

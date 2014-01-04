package se.cqst.whatif.main;

import java.util.Enumeration;

public class WorldLoader {
	
	private WorldLoader()	{}
	
	public static void loadWorld(World world)
	{
		//	Create Rooms
		roomCreator(world);
		
		//	Create Containers
		containerCreator(world);
		
		//	Create Items
		itemCreator(world);
		
		//	Create RoomConnectors
		roomConnectionCreator(world);
		
		//	Set currentRoom
		world.setCurrentRoom(WorldBrowser.getWorldRoom(world,CmdLib.getProperty(world.getWorldConfig().getRoomConfig(), "ROOM_START")));
	}
	
	/**
	 * Create Room objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static void roomCreator(World world)
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
			world.getRoomList().add(new Room(roomName, roomID, roomDesc, roomEnv));
			counter++;
		}
		CmdLib.writeLog("INFO", "Finished creating " + counter + " rooms.");
		
	}
	
	/**
	 * Create Item objects from the property-objects imported by the loadConfigs-method
	 *
	 * @param world Target World object
	 */
	private static void itemCreator(World world)
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
			if(WorldBrowser.getWorldItemStore(world,itemLoc) != null)
			{
				if(itemType.equalsIgnoreCase("staticitem"))
				{
					WorldBrowser.getWorldItemStore(world,itemLoc).putItem(new StaticItem(itemName,itemID));
					WorldBrowser.getWorldItemStore(world,itemLoc).getItem(itemID).setDescription(itemDesc);
					counter++;
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					WorldBrowser.getWorldItemStore(world,itemLoc).putItem(new MovableItem(itemName,itemID));
					WorldBrowser.getWorldItemStore(world,itemLoc).getItem(itemID).setDescription(itemDesc);
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
	private static void roomConnectionCreator(World world)
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
			if(WorldBrowser.getWorldRoom(world,connectorOrigin) == null)
			{
				System.out.println("Invalid Origin \"" + connectorOrigin + "\" for connector: " + connectorID);
				continue;
			}
			if(WorldBrowser.getWorldRoom(world,connectorTarget) == null)
			{
				System.out.println("Invalid Target \"" + connectorTarget + "\" for connector: " + connectorID);
				continue;
			}
			if(connectorName != null && connectorDesc != null && connectorPrefix != null && connectorDir != null && connectorOrigin != null && connectorTarget != null)
			{
				if(Room.isValidDirection(connectorDir.toLowerCase()))
				{
					Room.connect(WorldBrowser.getWorldRoom(world,connectorOrigin), connectorPrefix, connectorName, connectorID, WorldBrowser.getWorldRoom(world,connectorTarget), connectorDir.toLowerCase());
					WorldBrowser.getWorldRoom(world,connectorOrigin).getRoomConnection(connectorDir.toLowerCase()).setDescription(connectorDesc);
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
	private static void containerCreator(World world)
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
			if(WorldBrowser.getWorldRoom(world,containerLoc) != null)
			{
				WorldBrowser.getWorldRoom(world,containerLoc).getContainerList().add(new Container(containerName,containerID));
				WorldBrowser.getWorldRoom(world,containerLoc).getContainer(containerID).setDescription(containerDesc);
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

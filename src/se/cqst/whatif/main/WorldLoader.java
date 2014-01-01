package se.cqst.whatif.main;

import java.util.Enumeration;

public class WorldLoader {
	
	public static void loadConfigs(World world)
	{
		try
		{
			world.getWorldConfig().setWorldConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONF_FILEPATH)));
			world.getWorldConfig().setDictConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.DICT_FILEPATH)));
			world.getWorldConfig().setRoomConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.ROOM_FILEPATH)));
			world.getWorldConfig().setContainerConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONT_FILEPATH)));
			world.getWorldConfig().setItemConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.ITEM_FILEPATH)));
			world.getWorldConfig().setConnectorConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONN_FILEPATH)));
		}
		catch(NullPointerException ex)
		{
			System.out.println(ex.getMessage() + ex.getStackTrace());
		}
	}
	
	public static void roomCreator(World world)
	{		
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
		}
		
	}
	
	public static void itemCreator(World world)
	{		
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
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					world.getWorldItemStore(itemLoc).putItem(new MovableItem(itemName,itemID));
					world.getWorldItemStore(itemLoc).getItem(itemID).setDescription(itemDesc);
				}
				else
				{
					CmdLib.writeLog("DEBUG", "Unknown Item type \"" + itemType + "\" for object " + itemID + ".");
				}
			}/*
			else if(World.getRoom(itemLoc) != null)
			{
				if(itemType.equalsIgnoreCase("staticitem"))
				{
					World.getRoom(itemLoc).putItem(new StaticItem(itemName,itemID));
					World.getRoom(itemLoc).getItem(itemID).setDescription(itemDesc);
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					World.getRoom(itemLoc).putItem(new MovableItem(itemName,itemID));
					World.getRoom(itemLoc).getItem(itemID).setDescription(itemDesc);
				}
				else
				{
					CmdLib.writeLog("DEBUG", "Unknown Item type \"" + itemType + "\" for object " + itemID + ".");
				}	
			} */
			else
			{
				CmdLib.writeLog("DEBUG", "Item location " + itemLoc + " does not exist.");
			}
		}
		
	}
	
	public static void roomConnectionCreator(World world)
	{		
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
				switch(connectorDir.toLowerCase())
				{
				case TextLib.NORTH:
				case TextLib.SOUTH:
				case TextLib.EAST:
				case TextLib.WEST:
				case TextLib.UP:
				case TextLib.DOWN:
					Room.connect(world.getWorldRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, world.getWorldRoom(connectorTarget), connectorDir.toLowerCase());
					break;
				default:
					break;	
				}
				world.getWorldRoom(connectorOrigin).getRoomConnection(connectorDir.toLowerCase()).setDescription(connectorDesc);
			}
			else
			{
				System.out.println("Invalid connector: " + connectorID);
			}
		}
		
	}
	
	public static void containerCreator(World world)
	{		
		Enumeration<?> e = CmdLib.filterProperties(world.getWorldConfig().getContainerConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String containerID = (String) e.nextElement();
			containerID = containerID.replace("_NAME","");
			String containerName = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_NAME");
			String containerDesc = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_DESC");
//			String containerEnv = CmdLib.getProperty(World.getContainerConfig(), containerID + "_ENV");
			String containerLoc = CmdLib.getProperty(world.getWorldConfig().getContainerConfig(), containerID + "_LOCATION");
			if(world.getWorldRoom(containerLoc) != null)
			{
				world.getWorldRoom(containerLoc).getContainerList().add(new Container(containerName,containerID));
				world.getWorldRoom(containerLoc).getContainer(containerID).setDescription(containerDesc);
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Container location " + containerLoc + " does not exist.");
			}

		}
		
	}

}

package main_project;

import java.util.Enumeration;

public class WorldLoader {
	
	public static void loadConfigs()
	{
		try
		{
			World.setWorldConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONF_FILEPATH)));
			World.setDictConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.DICT_FILEPATH)));
			World.setRoomConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.ROOM_FILEPATH)));
			World.setContainerConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONT_FILEPATH)));
			World.setItemConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.ITEM_FILEPATH)));
			World.setConnectorConfig(CmdLib.loadProperties(World.class.getResourceAsStream(TextLib.CONN_FILEPATH)));
		}
		catch(NullPointerException ex)
		{
			System.out.println(ex.getMessage() + ex.getStackTrace());
		}
	}
	
	public static void roomCreator()
	{		
		Enumeration<?> e = CmdLib.filterProperties(World.getRoomConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String roomID = (String) e.nextElement();
			roomID = roomID.replace("_NAME","");
			String roomName = CmdLib.getProperty(World.getRoomConfig(), roomID + "_NAME");
			String roomDesc = CmdLib.getProperty(World.getRoomConfig(), roomID + "_DESC");
			String roomEnv = CmdLib.getProperty(World.getRoomConfig(), roomID + "_ENV");
			World.getRoomList().add(new Room(roomName, roomID));
			World.getRoom(roomID).setDescription(roomDesc);
			World.getRoom(roomID).setEnvironment(roomEnv);
		}
		
	}
	
	public static void itemCreator()
	{		
		Enumeration<?> e = CmdLib.filterProperties(World.getItemConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String itemID = (String) e.nextElement();
			itemID = itemID.replace("_NAME","");
			String itemName = CmdLib.getProperty(World.getItemConfig(), itemID + "_NAME");
			String itemType = CmdLib.getProperty(World.getItemConfig(), itemID + "_TYPE");
			String itemDesc = CmdLib.getProperty(World.getItemConfig(), itemID + "_DESC");
			String itemLoc = CmdLib.getProperty(World.getItemConfig(), itemID + "_LOCATION");
			if(World.getItemStore(itemLoc) != null)
			{
				if(itemType.equalsIgnoreCase("staticitem"))
				{
					World.getItemStore(itemLoc).putItem(new StaticItem(itemName,itemID));
					World.getItemStore(itemLoc).getItem(itemID).setDescription(itemDesc);
				}
				else if(itemType.equalsIgnoreCase("movableitem"))
				{
					World.getItemStore(itemLoc).putItem(new MovableItem(itemName,itemID));
					World.getItemStore(itemLoc).getItem(itemID).setDescription(itemDesc);
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
	
	public static void roomConnectionCreator()
	{		
		Enumeration<?> e = CmdLib.filterProperties(World.getConnectorConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String connectorID = (String) e.nextElement();
			connectorID = connectorID.replace("_NAME","");
			String connectorName = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_NAME");
			String connectorDesc = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_DESC");
			String connectorPrefix = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_PREFIX");
			String connectorDir = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_DIRECTION");
			String connectorOrigin = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_ORIGIN");
			String connectorTarget = CmdLib.getProperty(World.getConnectorConfig(), connectorID + "_TARGET");
			if(World.getRoom(connectorOrigin) == null)
			{
				System.out.println("Invalid Origin \"" + connectorOrigin + "\" for connector: " + connectorID);
				continue;
			}
			if(World.getRoom(connectorTarget) == null)
			{
				System.out.println("Invalid Target \"" + connectorTarget + "\" for connector: " + connectorID);
				continue;
			}
			if(connectorName != null && connectorDesc != null && connectorPrefix != null && connectorDir != null && connectorOrigin != null && connectorTarget != null)
			{
				switch(connectorDir.toLowerCase())
				{
				case TextLib.NORTH:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.NORTH);
					break;
				case TextLib.SOUTH:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.SOUTH);
					break;
				case TextLib.EAST:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.EAST);
					break;
				case TextLib.WEST:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.WEST);
					break;
				case TextLib.UP:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.UP);
					break;
				case TextLib.DOWN:
					Room.connect(World.getRoom(connectorOrigin), connectorPrefix, connectorName, connectorID, World.getRoom(connectorTarget), TextLib.DOWN);
					break;
				default:
					break;	
				}
				World.getRoom(connectorOrigin).getRoomConnection(connectorDir.toLowerCase()).setDescription(connectorDesc);
			}
			else
			{
				System.out.println("Invalid connector: " + connectorID);
			}
		}
		
	}
	
	public static void containerCreator()
	{		
		Enumeration<?> e = CmdLib.filterProperties(World.getContainerConfig(), "_NAME").propertyNames();
		while(e.hasMoreElements())
		{
			String containerID = (String) e.nextElement();
			containerID = containerID.replace("_NAME","");
			String containerName = CmdLib.getProperty(World.getContainerConfig(), containerID + "_NAME");
			String containerDesc = CmdLib.getProperty(World.getContainerConfig(), containerID + "_DESC");
//			String containerEnv = CmdLib.getProperty(World.getContainerConfig(), containerID + "_ENV");
			String containerLoc = CmdLib.getProperty(World.getContainerConfig(), containerID + "_LOCATION");
			if(World.getRoom(containerLoc) != null)
			{
				World.getRoom(containerLoc).getContainerList().add(new Container(containerName,containerID));
				World.getRoom(containerLoc).getContainer(containerID).setDescription(containerDesc);
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Container location " + containerLoc + " does not exist.");
			}

		}
		
	}

}

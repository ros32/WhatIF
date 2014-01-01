package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class World {
	
	public World()
	{
		
	}
	
	private static List<Room> roomList = new ArrayList<Room>();

	private static Properties worldConfig;
	private static Properties dictConfig;
	private static Properties roomConfig;
	private static Properties containerConfig;
	private static Properties itemConfig;
	private static Properties connectorConfig;
	private static Room currentRoom = null;
	
	public static Room getCurrentRoom()		{	return World.currentRoom;	}
	public static void setCurrentRoom(Room room)	{	World.currentRoom=room;		}
	
	public static void init()
	{
		WorldLoader.loadConfigs();
		WorldLoader.roomCreator();
		WorldLoader.containerCreator();
		WorldLoader.itemCreator();
		WorldLoader.roomConnectionCreator();
		World.currentRoom = getRoom(CmdLib.getProperty(World.getRoomConfig(), "ROOM_START"));
		Room.enterRoom(currentRoom);
	}
	
	public static Room getRoom(String identifier)
	{
		for(Room place : getRoomList())
		{
			if(place.toString().equals(identifier))
				return place;
		}
		return null;
	}
	
	public static Item getItem(String identifier)
	{
		for(Room place : getRoomList())
		{
			if(place.getItem(identifier) != null)
				return place.getItem(identifier);
			else
			{
				for(Container store : place.getContainerList())
				{
					if(store.getItem(identifier) != null)
						return store.getItem(identifier);
				}
			}
		}
		return null;
	}
	
	public static Container getContainer(String identifier)
	{
		for(Room place : getRoomList())
		{
			for(Container store : place.getContainerList())
			{
				if(store.toString().equals(identifier))
					return store;
			}
		}
		return null;
	}
	
	public static ItemStore getItemStore(String identifier)
	{
		for(Room place : World.getRoomList())
		{
			if(place.toString().equalsIgnoreCase(identifier))
				return place;
			for(ItemStore store : place.getContainerList())
			{
				if(store.toString().equalsIgnoreCase(identifier))
					return store;
			}
		}
		return null;
	}
	
	public static boolean isValidObject(String identifier)
	{
		if(World.getObject(identifier) != null)
			return true;
		else
			return false;
			
	}
	
	public static String getObjectID(String name)
	{
		for(Room place : World.getRoomList())
		{
			if(place.getName().equalsIgnoreCase(name))
				return place.toString();
			for(Item something : place.getItemList())
			{
				if(something.getName().equalsIgnoreCase(name))
					return something.toString();
			}
			for(Container store : place.getContainerList())
			{
				if(store.getName().equalsIgnoreCase(name))
					return store.toString();
				for(Item thing : store.getItemList())
				{
					if(thing.getName().equalsIgnoreCase(name))
						return thing.toString();
				}
			}
		}
		return null;
	}
	
	public static GenericObject getObject(String identifier)
	{
		for(Room place : World.getRoomList())
		{
			if(place.toString().equalsIgnoreCase(identifier))
				return (GenericObject) place;
			else
			{
				for(GenericObject thing : place.getItemList())
				{
					if(thing.toString().equalsIgnoreCase(identifier))
						return thing;
				}
				for(Container store : place.getContainerList())
				{
					if(store.toString().equalsIgnoreCase(identifier))
						return (GenericObject) store;
					else
					{
						for(GenericObject something : store.getItemList())
						{
							if(something.toString().equalsIgnoreCase(identifier))
								return something;
						}
					}
				}
			}
		}
		return null;
	}

	public static Properties getWorldConfig() {
		return worldConfig;
	}

	public static void setWorldConfig(Properties worldConfig) {
		World.worldConfig = worldConfig;
	}

	public static Properties getDictConfig() {
		return dictConfig;
	}

	public static void setDictConfig(Properties dictConfig) {
		World.dictConfig = dictConfig;
	}

	public static Properties getRoomConfig() {
		return roomConfig;
	}

	public static void setRoomConfig(Properties roomConfig) {
		World.roomConfig = roomConfig;
	}

	public static Properties getContainerConfig() {
		return containerConfig;
	}

	public static void setContainerConfig(Properties containerConfig) {
		World.containerConfig = containerConfig;
	}

	public static Properties getItemConfig() {
		return itemConfig;
	}

	public static void setItemConfig(Properties itemConfig) {
		World.itemConfig = itemConfig;
	}

	public static Properties getConnectorConfig() {
		return connectorConfig;
	}

	public static void setConnectorConfig(Properties connectorConfig) {
		World.connectorConfig = connectorConfig;
	}

	public static List<Room> getRoomList() {
		return roomList;
	}

	public static void setRoomList(List<Room> roomList) {
		World.roomList = roomList;
	}
}

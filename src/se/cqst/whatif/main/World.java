package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class World implements ItemStore {
	
	private static World currentWorld = null;
	private Configuration worldConfig;
	private boolean isActive;
	private List<Item> itemList;
	
	public World()
	{
		this.setActive(true);
		this.setWorldConfig(new Configuration());
	}
	
	private static List<Room> roomList = new ArrayList<Room>();

	private Room currentRoom = null;
	
	public Room getCurrentRoom()		{	return this.currentRoom;	}
	public void setCurrentRoom(Room room)	{	this.currentRoom=room;		}
	
	public void init()
	{
		WorldLoader.loadConfigs(this);
		WorldLoader.roomCreator(this);
		WorldLoader.containerCreator(this);
		WorldLoader.itemCreator(this);
		WorldLoader.roomConnectionCreator(this);
		this.currentRoom = getWorldRoom(CmdLib.getProperty(this.getWorldConfig().getRoomConfig(), "ROOM_START"));
		Room.enterRoom(this.currentRoom);
	}
	
	public Room getWorldRoom(String identifier)
	{
		for(Room place : this.getRoomList())
		{
			if(place.toString().equals(identifier))
				return place;
		}
		return null;
	}
	
	public Item getItem(String identifier)
	{
		for(Item thing : this.getItemList())
		{
			if(thing.toString().equalsIgnoreCase(identifier))
				return thing;
		}
		return null;
	}
	
	public void putItem(Item thing)
	{
		this.getItemList().add(thing);
	}
	
	public List<Item> getItemList()
	{
		return this.itemList;
	}
	
	public Item getWorldItem(String identifier)
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
	
	public Container getWorldContainer(String identifier)
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
	
	public ItemStore getWorldItemStore(String identifier)
	{
		for(Room place : this.getRoomList())
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
	
	public boolean isValidObject(String identifier)
	{
		if(this.getObject(identifier) != null)
			return true;
		else
			return false;
			
	}
	
	public String getObjectID(String name)
	{
		for(Room place : this.getRoomList())
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
	
	public GenericObject getObject(String identifier)
	{
		for(Room place : this.getRoomList())
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

	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		World.roomList = roomList;
	}
	public Configuration getWorldConfig() {
		return worldConfig;
	}
	public void setWorldConfig(Configuration worldConfig) {
		this.worldConfig = worldConfig;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public static World getCurrentWorld() {
		return currentWorld;
	}
	public static void setCurrentWorld(World currentWorld) {
		World.currentWorld = currentWorld;
	}
}

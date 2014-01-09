package se.cqst.whatif.main;

import java.io.Serializable;

public class Game implements Serializable {

	private static final long serialVersionUID = 3038330173731198169L;
	
	private World world;
	private Room currentRoom;
	private Actor currentActor;
	
	public Game()
	{
//		this.setCurrentWorld(world);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World currentWorld) {
		this.world = currentWorld;
	}
	
	public Actor findActor(String identifier)
	{
		for(Actor player : world.getActorList())
		{
			if(player.toString().equals(identifier))
				return player;
		}
		return null;
	}
	
	public Room findRoom(String identifier)
	{
		for(Room place : world.getRoomList())
		{
			if(place.toString().equals(identifier))
				return place;
		}
		return null;
	}
	
	public Item findItem(String identifier)
	{
		for(Room place : this.getWorld().getRoomList())
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
	
	public Container findContainer(String identifier)
	{
		for(Room place : this.getWorld().getRoomList())
		{
			for(Container store : place.getContainerList())
			{
				if(store.toString().equals(identifier))
					return store;
			}
		}
		return null;
	}
	
	public ItemStore findItemStore(String identifier)
	{
		for(Room place : this.getWorld().getRoomList())
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
		if(this.findObject(identifier) != null)
			return true;
		else
			return false;
			
	}
	
	public String findObjectID(String name)
	{
		//	Accept objectID
		if(findObject(name) != null)
			return findObject(name).toString();
		for(Room place : this.getWorld().getRoomList())
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
	
	public GenericObject findObject(String identifier)
	{
		for(Room place : this.getWorld().getRoomList())
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

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public Actor getCurrentActor() {
		return currentActor;
	}

	public void setCurrentActor(Actor currentActor) {
		this.currentActor = currentActor;
	}

}

package se.cqst.whatif.main;

public class WorldBrowser {

	public static Room getWorldRoom(World world, String identifier)
	{
		for(Room place : world.getRoomList())
		{
			if(place.toString().equals(identifier))
				return place;
		}
		return null;
	}
	

	

	
	public static Item getWorldItem(World world, String identifier)
	{
		if(world.getItem(identifier) != null)
			return world.getItem(identifier);
		for(Room place : world.getRoomList())
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
	
	public static Container getWorldContainer(World world, String identifier)
	{
		for(Room place : world.getRoomList())
		{
			for(Container store : place.getContainerList())
			{
				if(store.toString().equals(identifier))
					return store;
			}
		}
		return null;
	}
	
	public static ItemStore getWorldItemStore(World world, String identifier)
	{
		if(identifier.equalsIgnoreCase("WORLD"))
			return world;
		for(Room place : world.getRoomList())
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
	
	public static boolean isValidObject(World world, String identifier)
	{
		if(WorldBrowser.getObject(world, identifier) != null)
			return true;
		else
			return false;
			
	}
	
	public static String getObjectID(World world, String name)
	{
		for(Room place : world.getRoomList())
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
	
	public static GenericObject getObject(World world, String identifier)
	{
		for(Room place : world.getRoomList())
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
	
}

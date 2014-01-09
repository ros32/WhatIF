package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Actor extends GenericObject implements ItemStore {
	
	private List<Item> itemStore = new ArrayList<Item>();
	
	private Room currentLocation = null;
	
	
	
	public Actor(String name, String identifier)
	{
		super(name, identifier);
	}
	
	public Actor(String name, String identifier, Room currentRoom)
	{
		super(name,identifier);
		this.setCurrentLocation(currentRoom);
		CmdLib.writeLog("DEBUG", "Actor Object " + identifier + " created.");
	}
	
	public void use()
	{
		
	}
	
	public void use(GenericObject object)
	{
		
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
	
	public void removeItem(String identifier)
	{
		for(Item thing : this.getItemList())
		{
			if(thing.toString().equals(identifier))
				this.getItemList().remove(thing);
		}
	}
	
	public List<Item> getItemList()
	{
		return this.itemStore;
	}

	public Room getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Room currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
	

}

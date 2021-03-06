package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Actor extends GenericObject implements ItemStore {
	
	private List<Item> itemList = new ArrayList<Item>();
	
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
		this.putItem(thing,false);
	}
	
	public void putItem(Item thing, boolean suppressEvent)
	{
		this.getItemList().add(thing);
		if(!suppressEvent)
			System.out.println("You place the " + thing.getName() + " in your inventory.");
	}
	
	public void removeItem(String identifier)
	{
		Item tempItem = null;
		for(Item thing : this.getItemList())
		{
			if(thing.toString().equals(identifier))
				tempItem=thing;
		}
		if(tempItem != null)
			this.getItemList().remove(tempItem);
	}
	
	public List<Item> getItemList()
	{
		return this.itemList;
	}

	public Room getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Room currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public void look()
	{
//		this.printDescription();
		System.out.println("You examine your inventory: ");
		System.out.println(this.printItem());
		System.out.println();
	}
	
	public String		printItem()
	{
		String zoneString = "";
		for(Item thing : this.itemList)
		{
			zoneString += thing.getName() + ", ";
		}
		if(!zoneString.equals(""))
		{
			zoneString = zoneString.substring(0,zoneString.length()-2);
		}
		else
		{
			zoneString = "There are no items here.";
		}
		return zoneString;
	}
	
	

}

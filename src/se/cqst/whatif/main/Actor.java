package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Actor extends GenericObject implements ItemStore {
	
	private List<Item> itemStore = new ArrayList<Item>();
	
	
	
	public Actor(String name, String identifier)
	{
		super(name, identifier);
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
		
	}
	
	public List<Item> getItemList()
	{
		return this.itemStore;
	}
	
	
	

}

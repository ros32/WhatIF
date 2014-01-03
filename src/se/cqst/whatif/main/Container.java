package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Container extends GenericObject implements ItemStore {
	
	/*
	 * 			Static Variables
	 */
	
//	private static int 	counter;
	
	/*
	 * 			Variables
	 */
	

//	private String 		identifier;
//	private String 		name;
//	private String 		description;
	private List<Item> 	itemList;
	
	/*
	 * 			Constructors
	 */
	
//	public			Container(String name)		{	this(name, name + counter++); counter--;	}
	
	public			Container(String name, String identifier)
	{
		super(name, identifier);
		this.itemList = new ArrayList<Item>();
		CmdLib.writeLog("DEBUG", "Container Object " + identifier + " created.");
	}
	
	/*
	 * 			Overwritten Object Methods
	 */
	
	
	/*
	 * 			Basic get Methods
	 */
	
	
	/*
	 * 			Basic set Methods
	 */
	
	
	/*
	 * 			Other Methods
	 */
	
	public void			use()
	{
		CmdLib.writeLog("DEBUG", "A Container cannot be used");
	}
	
	public void			use(GenericObject object)
	{
		CmdLib.writeLog("DEBUG", "A Container cannot be used");
	}
		
	/*
	 * 			Interface Methods - ItemStore
	 */
	
	public Item 		getItem(String identifier)
	{
		for(Item thing : itemList)
		{
			if(thing.toString().equals(identifier))
				return thing;
		}
		return null;
	}
	
	public void 		putItem(Item thing)
	{
		itemList.add(thing);
	}
	
	public List<Item> 	getItemList()			{	return this.itemList;		}
	
	/*
	 * 			Static Methods
	 */
	


	


	
}

//	TODO: Deprecated, To Be Deleted
package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Zone implements ItemStore, ContainerStore, Lookable {

	/*
	 * 			Static Variables
	 */
	
	private static int 	counter;
	
	/*
	 * 			Variables
	 */
	
	private String 		name;
	private String 		identifier;
	private List<Item> 	itemList = new ArrayList<Item>();
	private List<Container> containerList = new ArrayList<Container>();
	private String		description;
//	private String		focusName, focusPrefix, focusDescription;
//	private String		focusName;
	
	/*
	 * 			Constructors
	 */
	
	public			Zone(String name)		{	this(name, name + counter++); counter--;	}
	
	public 			Zone(String name, String identifier)
	{
		this.name = name;
		this.identifier = identifier;
		counter++;
		this.description = "";
//		this.focusName=this.name;
//		this.focusDescription="";
//		this.focusPrefix="";
		CmdLib.writeLog("DEBUG", "Zone Object " + identifier + " created.");
		
	}
	
	/*
	 * 			Overwritten Object Methods
	 */
	
	public String 		toString()			{	return this.identifier;		}
	
	/*
	 * 			Basic get Methods
	 */
	
	public String		getName()			{	return this.name;		}
	public String		getDescription()		{	return this.description;	}
//	public String		getFocusName()			{	return this.focusName;		}
//	public String		getFocusDescription()		{	return this.focusDescription;	}
//	public String		getFocusPrefix()		{	return this.focusPrefix;	}
	
	
	/*
	 * 			Basic set Methods
	 */
	
	public void		setDescription(String desc)	{	this.description=desc;		}
//	public void		setFocusName(String name)	{	this.focusName=name;		}
//	public void		setFocsDescription(String desc)	{	this.focusDescription=desc;	}
//	public void		setFocusPrefix(String prefix)	{	this.focusPrefix=prefix;	}
	
	/*
	 * 			Other Methods
	 */
	
	
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
	
	public void 		putItem(Item thing)		{	itemList.add(thing);		}
	
	public List<Item> 	getItemList()			{	return this.itemList;		}
	
	/*
	 * 			Interface Methods - ContainerStore
	 */	
	
	public Container 	getContainer(String identifier)
	{
		for(Container thing : containerList)
		{
			if(thing.toString().equals(identifier))
				return thing;
		}
		return null;
	}
	
	/*
	 * 			Interface Methods - Lookable
	 */
	
	public void		look()
	{
				System.out.println(this.description);
	}
	
	public List<Container> 	getContainerList()	{	return this.containerList;	}
	

	

}

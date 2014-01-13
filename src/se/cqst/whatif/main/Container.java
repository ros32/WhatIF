package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Container extends GenericObject implements ItemStore {
	
	/*
	 * 			Static Variables
	 */
	
	public static final String	TXT_LINE_HOR		=	"================================================================================";
	public static final String	ROOM_ENTER_ITEMS	=	"You see the following items in this container:";	
	public static final String	ROOM_ENTER_ITEMS_NONE	=	"There are no items here.";
	
//	private static int 	counter;
	
	/*
	 * 			Variables
	 */
	
	private List<Item> 	itemList;
	
	/*
	 * 			Constructors
	 */
	
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
	
	public void		look()
	{
		this.printDescription();
		System.out.println(ROOM_ENTER_ITEMS);
		System.out.println(this.printItem());
		System.out.println();
		System.out.println(TXT_LINE_HOR);
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
			zoneString = ROOM_ENTER_ITEMS_NONE;
		}
		return zoneString;
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
	
	public void putItem(Item thing)
	{
		this.getItemList().add(thing);
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
	
	public List<Item> 	getItemList()			{	return this.itemList;		}
	
	/*
	 * 			Static Methods
	 */
	


	


	
}

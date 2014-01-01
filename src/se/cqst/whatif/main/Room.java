package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

public class Room extends GenericObject implements ItemStore, Lookable {
	
	/*
	 * 			Static variables
	 */
	
	
	/*
	 *			Variables
	 */
	
	private List<Item> 	itemList;
	private List<Container>	containerList;
	private RoomConnector[]	roomConnection = {null,null,null,null,null,null};
	
	private String 		environment;

	/*
	 * 			Constructors
	 */
	
//	public			Room(String name)		{	this(name, name + counter++); counter--;	}
	
	public 			Room(String name, String identifier)
	{
		super(name,identifier);
		itemList = new ArrayList<Item>();
		containerList = new ArrayList<Container>();
		environment = "";
		CmdLib.writeLog("DEBUG", "Room Object " + identifier + " created.");
	}	
	
	/*
	 * 			Overwritten Object methods
	 */
	
	/*
	 * 			Basic get Methods
	 */
	
	public String 		getEnvironment()		{	return this.environment;	}
	
	/*
	 * 			Basic set Methods
	 */
	
	public void 		setEnvironment(String desc)	{	this.environment=desc;		}	
	
	
	/*
	 * 			Other Methods
	 */
	
	public boolean		inRoom(String identifier)
	{
		for(Item thing : this.itemList)
		{
			if(thing.toString().equalsIgnoreCase(identifier))
				return true;
		}
		for(Container store : this.containerList)
		{
			if(store.toString().equalsIgnoreCase(identifier))
				return true;
			for(Item something : store.getItemList())
			{
				if(something.toString().equalsIgnoreCase(identifier))
					return true;
			}
		}
		return false;
	}
	
	public RoomConnector 	getRoomConnection(String direction)
	{
		switch(direction)
		{
		case TextLib.NORTH:
			return roomConnection[0];
		case TextLib.SOUTH:
			return roomConnection[1];
		case TextLib.EAST:
			return roomConnection[2];
		case TextLib.WEST:
			return roomConnection[3];
		case TextLib.UP:
			return roomConnection[4];
		case TextLib.DOWN:
			return roomConnection[5];
		default:
			return null;
		}
	}
	
	public boolean 		isValidDirection(String direction)
	{
		switch(direction)
		{
		case TextLib.NORTH:
		case TextLib.SOUTH:
		case TextLib.EAST:
		case TextLib.WEST:
		case TextLib.UP:
		case TextLib.DOWN:
			return true;
		default:
			return false;
		}
	}
	
	public void 		setRoomConnection(RoomConnector destination, String direction)
	{
		switch(direction)
		{
		case TextLib.NORTH:
			roomConnection[0]		=	destination;
			break;
		case TextLib.SOUTH:
			roomConnection[1]		=	destination;
			break;
		case TextLib.EAST:
			roomConnection[2]		=	destination;
			break;
		case TextLib.WEST:
			roomConnection[3]		=	destination;
			break;
		case TextLib.UP:
			roomConnection[4]		=	destination;
			break;
		case TextLib.DOWN:
			roomConnection[5]		=	destination;
			break;
		default:
			break;
		}		
	}
	
	public Room 		travel(String destination) throws InvalidRoomConnectionException
	{	if(this.getRoomConnection(destination) != null)
			return this.getRoomConnection(destination).getTarget();
		else
			throw new InvalidRoomConnectionException(TextLib.EX_INVALID_ROOM_CONN);
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
			zoneString = TextLib.ROOM_ENTER_ITEMS_NONE;
		}
		return zoneString;
	}
	
	/*
	 * 			Interface Methods - ContainerStore
	 */

	public Container	getContainer(String identifier)
	{
		for(Container store : containerList)
		{
			if(store.toString().equals(identifier))
				return store;
		}
		return null;
	}
	
	public List<Container> 	getContainerList()		{	return this.containerList;		}
	
	public String		printContainer()
	{
		String zoneString = "";
		for(Container store : this.getContainerList())
		{
			zoneString += store.getName() + ", ";
		}
		if(!zoneString.equals(""))
		{
			zoneString = zoneString.substring(0,zoneString.length()-2);
		}
		else
		{
			zoneString = TextLib.ROOM_ENTER_ZONES_NONE;
		}
		return zoneString;
	}
	
	/*
	 * 			Interface Methods - ItemStore
	 */
	
	public void		look()
	{
				System.out.println(super.getDescription());
	}
	
	/*
	 * 			Static Methods
	 */
	
	public static void 	connect(Room origin, String prefix, String name, String identifier, Room target, String direction)
	{
		origin.setRoomConnection(new RoomConnector(name, identifier, target), direction);
		origin.getRoomConnection(direction).setPrefix(prefix);
	}
	
	public static void 	enterRoom(Room place)
	{
		System.out.println();
		System.out.println(place.getName());
		System.out.println(TextLib.TXT_LINE_HOR);
		System.out.println(place.getDescription());
		System.out.println();
		System.out.println(TextLib.ROOM_ENTER_ZONES);
		System.out.println(place.printContainer());
		System.out.println();
		System.out.println(TextLib.ROOM_ENTER_ITEMS);
		System.out.println(place.printItem());
		System.out.println();
		System.out.println(place.getEnvironment());
		System.out.println(TextLib.TXT_LINE_HOR);
	}
	
	
	
}

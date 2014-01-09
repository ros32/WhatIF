//	TODO: Cohersion
package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The Room class is an extension of {@link GenericObject}, and represents a Room in the game.</p>
 * 
 * <p>Each Room has six {@link RoomConnector} objects associated with it through the {@code roomConnection[]} array,
 * which connects Rooms with eachother.</p>
 * 
 * <p>Room also implements the {@link ItemStore} interface, allowing it to have an {@link Item} inventory-system like the {@link Container}- and {@link World}-class.</p>
 * 
 * @see {@link GenericObject} (parent), {@link ItemStore} (interface)
 */
public class Room extends GenericObject implements ItemStore {
	
	/*
	 * 			Static variables
	 */
	
//	public static final String	NORTH			=	"north";
//	public static final String	SOUTH			=	"south";
//	public static final String	EAST			=	"east";
//	public static final String	WEST			=	"west";
//	public static final String	UP				=	"up";
//	public static final String	DOWN			=	"down";
	
	public static final String	TXT_LINE_HOR		=	"================================================================================";
	
	public static final String	ROOM_ENTER_ZONES	=	"The following containers exist here:";
	public static final String	ROOM_ENTER_ZONES_NONE	=	"There are no containers in this room.";
	public static final String	ROOM_ENTER_ITEMS	=	"You see the following items on the ground here:";	
	public static final String	ROOM_ENTER_ITEMS_NONE	=	"There are no items on the ground.";
	public static final String	EX_INVALID_ROOM_CONN	=	"You can not go that way.";
	
	public static enum Direction {	NORTH, SOUTH, EAST, WEST, UP, DOWN;	}
	
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
	
	public			Room(String name, String identifier, String description, String environment)
	{
		this(name, identifier);
		this.setDescription(description);
		this.setEnvironment(environment);
	}
	
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
	
	public void			use()
	{
		//	A Room cannot be used
		CmdLib.writeLog("DEBUG", "A Room cannot be used");
	}
	
	public void			use(GenericObject object)
	{
		//	A Room cannot be used
		CmdLib.writeLog("DEBUG", "A Room cannot be used");
	}
	
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
	
	public RoomConnector 	getRoomConnection(Direction direction)
	{
		switch(direction)
		{
		case NORTH:
			return roomConnection[0];
		case SOUTH:
			return roomConnection[1];
		case EAST:
			return roomConnection[2];
		case WEST:
			return roomConnection[3];
		case UP:
			return roomConnection[4];
		case DOWN:
			return roomConnection[5];
		default:
			return null;
		}
	}
	
	public void 		setRoomConnection(RoomConnector destination, Direction direction)
	{
		switch(direction)
		{
		case NORTH:
			roomConnection[0]		=	destination;
			break;
		case SOUTH:
			roomConnection[1]		=	destination;
			break;
		case EAST:
			roomConnection[2]		=	destination;
			break;
		case WEST:
			roomConnection[3]		=	destination;
			break;
		case UP:
			roomConnection[4]		=	destination;
			break;
		case DOWN:
			roomConnection[5]		=	destination;
			break;
		default:
			break;
		}		
	}
	
	public Room 		travel(Direction destination) throws InvalidRoomConnectionException
	{	if(this.getRoomConnection(destination) != null)
			return this.getRoomConnection(destination).getTarget();
		else
			throw new InvalidRoomConnectionException(EX_INVALID_ROOM_CONN);
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
			zoneString = ROOM_ENTER_ZONES_NONE;
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
	
//	public static boolean 		isValidDirection(Direction direction)
//	{
//		switch(direction)
//		{
//		case NORTH:
//		case SOUTH:
//		case EAST:
//		case WEST:
//		case UP:
//		case DOWN:
//			return true;
//		default:
//			return false;
//		}
//	}
	
	public static void 	connect(Room origin, String name, String identifier, Room target, Direction direction)
	{
		origin.setRoomConnection(new RoomConnector(name, identifier, target), direction);
//		origin.getRoomConnection(direction).setPrefix(prefix);
	}
	
	public void enterRoom()
	{
		Room.enterRoom(this);
	}
	
	public static void 	enterRoom(Room place)
	{
		System.out.println();
		System.out.println(place.getName());
		System.out.println(TXT_LINE_HOR);
		System.out.println(place.getDescription());
		System.out.println();
		System.out.println(ROOM_ENTER_ZONES);
		System.out.println(place.printContainer());
		System.out.println();
		System.out.println(ROOM_ENTER_ITEMS);
		System.out.println(place.printItem());
		System.out.println();
		System.out.println(place.getEnvironment());
		System.out.println(TXT_LINE_HOR);
	}
	
	
	
}

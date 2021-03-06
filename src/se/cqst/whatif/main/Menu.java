package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
	
	public static final String	GO_VALID_CMDS		=	"";
	public static final String	GO_VALID_DIRECTIONS	=	"north, south, east, west, up and down are valid inputs";
	public static final String	GO_EMPTY_DIRECTION	=	"Go where? (" + GO_VALID_DIRECTIONS + ")";
	
	public static enum LoadOption {NO_SELECTION, NEW_GAME, LOAD_GAME;}
	public static enum ObjectType	{	ITEM, ITEM_STORE;	}
	
	private boolean displayStart = true;
	private Configuration dictConfig;
	private LoadOption loadState = LoadOption.NO_SELECTION;	
	
	public Menu(Configuration dictConfig)
	{
		this.setDictConfig(dictConfig);
	}
	
	public void printStart()
	{
		System.out.println();
		System.out.println("=== Cool intro or header goes here ===");
		System.out.println();
		System.out.println("Type \"new\" to start a new game, or \"exit\" to quit the game.");
	}
	
	public boolean drawStart(List<String> cmdList)
	{
		switch(cmdList.get(0).toLowerCase())
		{
		case "new":
			this.setLoadState(LoadOption.NEW_GAME);
			this.setDisplayStart(false);
			return true;
		case "load":
			this.setLoadState(LoadOption.LOAD_GAME);
			this.setDisplayStart(false);
			return true;
		case "exit":
		case "quit":
			this.setDisplayStart(false);
			return false;
		default:
			System.out.println(String.format(this.dictConfig.getProperty("TXT_INVALID_COMMAND"), "help"));
			return true;
		}
	}

	public String[] 		getGoInvalidDirArray()
	{	return new String[]
			{
			
				String.format(this.dictConfig.getProperty("GO_INVALID_DIR_1"), GO_VALID_DIRECTIONS),
				String.format(this.dictConfig.getProperty("GO_INVALID_DIR_2"), GO_VALID_DIRECTIONS),
				String.format(this.dictConfig.getProperty("GO_INVALID_DIR_3"), GO_VALID_DIRECTIONS)		
			};
	}
	
	public String[] 		getInvalidRoomConnArray()
	{	return new String[]
			{
				"You see no path there.",
				"It is not possible to go that way.",
				"You try but fail to progress in that direction."
			};
	}
	
	//	TODO: Allow load/save function through drawMenu()
	public boolean drawMenu(Game game, List<String> cmdList)
	{
		switch(cmdList.get(0).toLowerCase())
		{
		case "go":
			doGo(game, cmdList);
			break;
		case "look":
			doLook(game, cmdList);
			break;
		case "take":
		case "get":
		case "grab":
			doTake(game, cmdList);
			break;
		case "put":
		case "drop":
		case "set":
			doPut(game, cmdList);
			break;
		case "help":
		case "?":
			doHelp(cmdList);
			break;
		case "exit":
		case "quit":
			return true;
		default:
			System.out.println(String.format(this.dictConfig.getProperty("TXT_INVALID_COMMAND"), "help"));
			break;
		}
		return false;
	}
	
	public static List<String> getParam(String cmd)
	{
		List<String> list = new ArrayList<String>();
		//	This regex will interpret "quoted sentences" as one word when splitting input to list.
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(cmd);
		while (m.find())
			//	Remove quotes from elements
		    list.add(m.group(1).replace("\"", ""));
		return list;
	}
		
	public void doHelp(List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			switch(cmdList.get(1).toLowerCase())
			{
			case "go":
				System.out.println(this.dictConfig.getProperty("TXT_HELP_GO"));
				break;
			case "look":
				System.out.println(this.dictConfig.getProperty("TXT_HELP_LOOK"));
				break;
			case "take":
			case "get":
			case "grab":
				System.out.println(this.dictConfig.getProperty("TXT_HELP_TAKE"));
				break;
			case "put":
			case "drop":
			case "set":
				System.out.println(this.dictConfig.getProperty("TXT_HELP_PUT"));
				break;
			case "help":
			case "?":
				//	A bit redundant perhaps
				System.out.println(this.dictConfig.getProperty("TXT_HELP_HELP"));
				break;
			case "exit":
			case "quit":
				System.out.println(this.dictConfig.getProperty("TXT_HELP_EXIT"));
				break;
			//	TODO: Implement all supported commands
			default:
				System.out.println(this.dictConfig.getProperty("TXT_HELP_CONTENT"));
				break;
			}
		}
		else
		{
			System.out.println(dictConfig.getProperty("TXT_HELP_CONTENT"));
		}
	}
	
	//	TODO: Add overloaded drawHelp(String[] command)
	//		This will allow methods to call drawHelp with
	//		specific args
	
	public  void doGo(Game game, List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			switch(cmdList.get(1).toLowerCase())
			{
			case "north":
			case "south":
			case "east":
			case "west":
			case "up":
			case "down":
				try
				{
					Room tempRoom = game.getCurrentRoom().travel(Room.Direction.valueOf(cmdList.get(1).toUpperCase()));
					game.setCurrentRoom(tempRoom);
					Room.enterRoom(tempRoom);
				}
				catch(InvalidRoomConnectionException ex)
				{
					System.out.println(CmdLib.getRandElement(getInvalidRoomConnArray()));
				}
				break;
			case "help":
			case "?":
				break;
			default:
				System.out.println(CmdLib.getRandElement(getGoInvalidDirArray()));
				break;
			}
		}
		else
		{
			System.out.println(dictConfig.getProperty("GO_EMPTY_DIRECTION"));
		}
	}
	
	public void doLook(Game game, List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			String targetObject = "";
			cmdList.remove(0);
			if(cmdList.get(0).equals("at"))
				cmdList.remove(0);
			if(cmdList.size() == 0)
				System.out.println("Look at what?");
			else
			{
				int i = 0;
				for(String word : cmdList)
				{
					//	The first word should not have a leading space
					if (i == 0)
						targetObject += word;
					else
						targetObject = targetObject + " " + word;
					i++;
				}
				//	If target object is a valid object AND is in the current room OR on the current actor, look() at the object
				if(game.getCurrentRoom().findObject(game.getCurrentRoom().findObjectID(targetObject)) != null)
					game.getCurrentRoom().findObject(game.getCurrentRoom().findObjectID(targetObject)).look();
				else if(game.getCurrentActor().getItem(game.findObjectID(targetObject)) != null)
					game.getCurrentActor().getItem(game.findObjectID(targetObject)).look();
				else if(game.getCurrentActor().toString().equalsIgnoreCase(targetObject) || targetObject.equalsIgnoreCase("inventory"))
					game.getCurrentActor().look();
				else
					//	TODO: Change to TextLib
					System.out.println("Object is not here");
			}
		}
		else
		{
			Room.enterRoom(game.getCurrentRoom());
		}
	}
	
	//	Take Item from currentRoom (and ItemStore within) and move to currentActor's ItemStore
	public void doTake(Game game, List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			String objectName = getObjectFromInput(ObjectType.ITEM, cmdList);
			String itemStoreName = getObjectFromInput(ObjectType.ITEM_STORE, cmdList);
			//	Default to room of no item store is set
			if(itemStoreName == null)
				itemStoreName = game.getCurrentRoom().toString();
			if(objectName == null)
				objectName = "unknown";
			//	If object is floor or room, set objectName to RoomID
			if(itemStoreName.toLowerCase().equals("floor") || itemStoreName.toLowerCase().equals("room"))
				itemStoreName = game.getCurrentRoom().toString();
			CmdLib.writeLog("DEBUG", "Take \"" + objectName + "\" from \"" + itemStoreName + "\"");
			
			ItemStore tempItemStore = null;
			Item tempItem = null;
			boolean validItem = false;
			boolean validItemStore = false;
			
			//	Does ItemStore exist?
			if(game.findItemStore(game.getCurrentRoom().findObjectID(itemStoreName)) != null)
			{
				tempItemStore = game.findItemStore(game.getCurrentRoom().findObjectID(itemStoreName));
				validItemStore = true;
			}
			else
			{
				validItemStore = false;
				CmdLib.writeLog("DEBUG", "Container \"" + itemStoreName + "\" does not exist!");
			}
			//	If ItemStore is valid, check if Item exist within
			if(validItemStore && tempItemStore.getItem(game.getCurrentRoom().findObjectID(objectName)) != null)
			{
				tempItem = tempItemStore.getItem(game.getCurrentRoom().findObjectID(objectName));
				validItem = true;
				
			}
			else
			{
				validItem = false;
				CmdLib.writeLog("DEBUG", "Object \"" + objectName + "\" does not exist!");
			}
			
			if(validItemStore && validItem)
			{
				if(game.getCurrentRoom().inRoom(tempItemStore.toString()))
				{
					tempItem.move(tempItemStore, game.getCurrentActor());
				}
				else
				{
					CmdLib.writeLog("DEBUG", "Object or target not found in current room");
				}
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Object or target does not exist!");
			}
		}
		else
		{
			System.out.println("Take what?");
		}
		
	}
	
	//	Put Item from currentActor's ItemStore to any ItemStore in currentRoom
	public void doPut(Game game, List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			String objectName = getObjectFromInput(ObjectType.ITEM, cmdList);
			String itemStoreName = getObjectFromInput(ObjectType.ITEM_STORE, cmdList);
			//	Default to room of no item store is set
			if(itemStoreName == null)
				itemStoreName = game.getCurrentRoom().toString();
			if(objectName == null)
				objectName = "unknown";
			//	If object is floor or room, set objectName to RoomID
			if(itemStoreName.toLowerCase().equals("floor") || itemStoreName.toLowerCase().equals("room"))
				itemStoreName = game.getCurrentRoom().toString();
			CmdLib.writeLog("DEBUG", "Put \"" + objectName + "\" in \"" + itemStoreName + "\"");
			
			ItemStore tempItemStore = null;
			Item tempItem = null;
			boolean validItem = false;
			boolean validItemStore = false;
			
			//	Does ItemStore exist, first in-game, then in currentRoom?
			if(game.findItemStore(game.getCurrentRoom().findObjectID(itemStoreName)) != null && (game.getCurrentRoom().inRoom(game.getCurrentRoom().findObjectID(itemStoreName))))
			{
				tempItemStore = game.findItemStore(game.getCurrentRoom().findObjectID(itemStoreName));
				validItemStore = true;
			}
			else
			{
				validItemStore = false;
				CmdLib.writeLog("DEBUG", "Container \"" + itemStoreName + "\" does not exist!");
			}
			//	Only check currentActor for Item
			if(game.getCurrentActor().getItem(game.findObjectID(objectName)) != null)
			{
				tempItem = game.findItem(game.findObjectID(objectName));
				validItem = true;
				
			}
			else
			{
				validItem = false;
				CmdLib.writeLog("DEBUG", "Object \"" + objectName + "\" does not exist!");
			}
			
			if(validItemStore && validItem)
			{
				if(game.getCurrentRoom().inRoom(tempItemStore.toString()) || game.getCurrentRoom().toString().equals(tempItemStore.toString()))
				{
					tempItem.move(game.getCurrentActor(), tempItemStore);
				}
				else
				{
					CmdLib.writeLog("DEBUG", "Object or target not found in current room");
				}
			}
			else
			{
				CmdLib.writeLog("DEBUG", "Object or target does not exist!");
			}

		}
		else
		{
			System.out.println("Take what?");
		}
	}
	
	//	TODO: Rename to something more coherent?
	public String getObjectFromInput (ObjectType type, List<String> cmdList)
	{
		String[] separatorWords = {"from", "to", "on", "at", "in"	};
		
		int objectNameElements = 0;
		int itemStoreNameElements = 0;
		boolean foundSeparator = false;
		String firstName = null;
		String secondName = null;
		//	Remove first entry in cmdList, since it contains get/put/etc.
		cmdList.remove(0);
		for(String word : cmdList)
		{	
			//	After delimiter is the name of the target item store
			if(foundSeparator)
			{
				if(itemStoreNameElements == 0)
					secondName = word;
				else
					secondName += " " + word;
				
				itemStoreNameElements++;
			}
			
			//	If delimiter is found, set foundFrom to true
			if(!foundSeparator)
			{
				for(String delimiter : separatorWords)
				{
					if(word.equalsIgnoreCase(delimiter))
						foundSeparator = true;
				}
			}

			//	If delimiter was not found we are still on the object name
			if(!foundSeparator)
			{
				if(objectNameElements == 0)
					firstName = word;
				else
					firstName += " " + word;
					
				objectNameElements++;
			}
		}
		
		if(type == ObjectType.ITEM)
			return firstName;
		if(type == ObjectType.ITEM_STORE)
			return secondName;
		else
			return null;
	}


	public boolean displayStart() {
		return displayStart;
	}

	public void setDisplayStart(boolean displayStart) {
		this.displayStart = displayStart;
	}

	public LoadOption getLoadState() {
		return loadState;
	}

	public void setLoadState(LoadOption loadState) {
		this.loadState = loadState;
	}

	public Configuration getDictConfig() {
		return dictConfig;
	}

	public void setDictConfig(Configuration dictConfig) {
		this.dictConfig = dictConfig;
	}
	
	
	
}

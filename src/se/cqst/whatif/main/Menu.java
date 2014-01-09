package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
	
	public static final String 	SW_EXIT_1			=	"exit";
	public static final String 	SW_EXIT_2			=	"quit";
	public static final String 	SW_HELP_1			=	"help";
	public static final String 	SW_HELP_2			=	"?";
	
	public static final String	SW_GO				=	"go";
	public static final String	SW_TAKE				=	"take";
	public static final String	SW_LOOK				=	"look";
	public static final String	SW_USE				=	"use";
	public static final String	SW_PUT				=	"put";
	public static final String	SW_DROP				=	"drop";
	
	public static final String	GO_VALID_CMDS		=	"";
	public static final String	GO_VALID_DIRECTIONS	=	"north, south, east, west, up and down are valid inputs";
	public static final String	GO_EMPTY_DIRECTION	=	"Go where? (" + GO_VALID_DIRECTIONS + ")";
	
	public static enum LoadOption {NO_SELECTION, NEW_GAME, LOAD_GAME;}
	
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
		System.out.println("Cool into or header goes here");
		System.out.println();
		System.out.println("Type \"new game\" to start a new game.");
	}
	
	public boolean drawStart(List<String> cmdList)
	{
		switch(cmdList.get(0))
		{
		case "new":
			this.setLoadState(LoadOption.NEW_GAME);
			this.setDisplayStart(false);
			return true;
		case "load":
			this.setLoadState(LoadOption.LOAD_GAME);
			this.setDisplayStart(false);
			return true;
		case SW_EXIT_1:
			this.setDisplayStart(false);
			return false;
		default:
			System.out.println(String.format(this.dictConfig.getProperty("TXT_INVALID_COMMAND"), SW_HELP_1));
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
	
	public static String[] 		getInvalidRoomConnArray()
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
		case SW_GO:
			doGo(game, cmdList);
			break;
		case SW_LOOK:
			doLook(game, cmdList);
			break;
		case SW_TAKE:
			break;
		case SW_PUT:
			break;
		case SW_DROP:
			break;
		case SW_HELP_1:
		case SW_HELP_2:
			drawHelp(cmdList);
			break;
		case SW_EXIT_1:
		case SW_EXIT_2:
			return true;
		default:
			System.out.println(String.format(this.dictConfig.getProperty("TXT_INVALID_COMMAND"), SW_HELP_1));
			break;
		}
		return false;
	}
	
	public static List<String> getParam(String cmd)
	{
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(cmd);
		while (m.find())
		    list.add(m.group(1).replace("\"", ""));
		return list;
	}
		
	public void drawHelp(List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			switch(cmdList.get(1).toLowerCase())
			{
			case SW_HELP_1:
			case SW_HELP_2:
				//	A bit redundant perhaps
				System.out.println(this.dictConfig.getProperty("TXT_HELP_HELP"));
				break;
			case SW_EXIT_1:
			case SW_EXIT_2:
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
					//	valueOf needs uppercase letters
					game.setCurrentRoom(game.getCurrentRoom().travel(Room.Direction.valueOf(cmdList.get(1).toUpperCase())));
					Room.enterRoom(game.getCurrentRoom());
				}
				catch(InvalidRoomConnectionException ex)
				{
					System.out.println(CmdLib.getRandElement(getInvalidRoomConnArray()));
				}
				catch(IllegalArgumentException ex)
				{
					//	Should not get here
					CmdLib.writeLog("WARNING", "Invalid direction \"" + cmdList.get(1).toUpperCase() + "\"!");
				}
				break;
			case SW_HELP_1:
			case SW_HELP_2:
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
				//	If target object is a valid object AND is in the current room, look() at the object
				if(game.isValidObject(game.findObjectID(targetObject)) && game.getCurrentRoom().inRoom(game.findObjectID(targetObject)))
					game.findObject(game.findObjectID(targetObject)).look();
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

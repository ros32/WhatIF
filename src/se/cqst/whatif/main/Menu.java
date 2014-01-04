package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	
	public static final String	TXT_INVALID_COMMAND	=	"Invalid command. Please type \"" + SW_HELP_1 + "\" for a list of commands.\n";
	
	public static final String	TXT_HELP_CONTENT	=	CmdLib.getProperty(World.getInstance().getWorldConfig().getDictConfig(), "TXT_HELP_CONTENT");
	public static final String	TXT_HELP_HELP		=	CmdLib.getProperty(World.getInstance().getWorldConfig().getDictConfig(), "TXT_HELP_HELP");
	public static final String	TXT_HELP_EXIT		=	CmdLib.getProperty(World.getInstance().getWorldConfig().getDictConfig(), "TXT_HELP_EXIT");
	public static final String	TXT_HELP_GO			=	CmdLib.getProperty(World.getInstance().getWorldConfig().getDictConfig(), "TXT_HELP_GO");
	public static final String	TXT_HELP_LOOK		=	CmdLib.getProperty(World.getInstance().getWorldConfig().getDictConfig(), "TXT_HELP_LOOK");
	
	public static final String	GO_VALID_CMDS		=	"";
	public static final String	GO_VALID_DIRECTIONS	=	Room.NORTH + ", " + Room.SOUTH + ", " + Room.EAST + ", " + Room.WEST + ", " + Room.UP + " and " + Room.DOWN + " are valid inputs";
	public static final String	GO_EMPTY_DIRECTION	=	"Go where? (" + GO_VALID_DIRECTIONS + ")";

	public static String[] 		multi_GO_INVALID_DIR()
	{	return new String[]
			{
				"That was an unusual direction. Perhaps you should try a more well-known one instead?\n  (" + GO_VALID_DIRECTIONS + ")",
				"I choose to disobey you; mostly because I don't understand you.\n  (" + GO_VALID_DIRECTIONS + ")",
				"You have entered an unknown direction. Try a known direction instead.\n  (" + GO_VALID_DIRECTIONS + ")"		
			};
	}
	
	public static String[] 		multi_GO_INVALID_ROOM_CONN()
	{	return new String[]
			{
				"You see no path there.",
				"It is not possible to go that way.",
				"You try but fail to progress in that direction."
			};
	}
	
	//	TODO: Allow load/save function through drawMenu()
	public static boolean drawMenu(Scanner sc, List<String> cmdList)
	{
		switch(cmdList.get(0))
		{
		case SW_GO:
			drawGo(cmdList);
			break;
		case SW_LOOK:
			drawLook(cmdList);
			break;
		case SW_HELP_1:
		case SW_HELP_2:
			drawHelp(cmdList);
			break;
		case SW_EXIT_1:
		case SW_EXIT_2:
			return true;
		default:
			System.out.println(TXT_INVALID_COMMAND);
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
		
	public static void drawHelp(List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			switch(cmdList.get(1))
			{
			case SW_HELP_1:
			case SW_HELP_2:
				//	A bit redundant perhaps
				System.out.println(TXT_HELP_HELP);
				break;
			case SW_EXIT_1:
			case SW_EXIT_2:
				System.out.println(TXT_HELP_EXIT);
				break;
			//	TODO: Implement all supported commands
			default:
				System.out.println(TXT_HELP_CONTENT);
				break;
			}
		}
		else
		{
			System.out.println(TXT_HELP_CONTENT);
		}
	}
	
	//	TODO: Add overloaded drawHelp(String[] command)
	//		This will allow methods to call drawHelp with
	//		specific args
	
	public static void drawGo(List<String> cmdList)
	{
		if(cmdList.size() != 1)
		{
			switch(cmdList.get(1))
			{
			case Room.NORTH:
			case Room.SOUTH:
			case Room.EAST:
			case Room.WEST:
			case Room.UP:
			case Room.DOWN:
				try
				{
					World.getInstance().setCurrentRoom(World.getInstance().getCurrentRoom().travel(cmdList.get(1)));
					Room.enterRoom(World.getInstance().getCurrentRoom());
				}
				catch(InvalidRoomConnectionException ex)
				{
					System.out.println(CmdLib.getRandElement(multi_GO_INVALID_ROOM_CONN()));
				}
				break;
			case SW_HELP_1:
			case SW_HELP_2:
				break;
			default:
				System.out.println(CmdLib.getRandElement(multi_GO_INVALID_DIR()));
				break;
			}
		}
		else
		{
			System.out.println(GO_EMPTY_DIRECTION);
		}
	}
	
	public static void drawLook(List<String> cmdList)
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
				if(WorldBrowser.isValidObject(World.getInstance(),WorldBrowser.getObjectID(World.getInstance(),targetObject)) && World.getInstance().getCurrentRoom().inRoom(WorldBrowser.getObjectID(World.getInstance(),targetObject)))
					WorldBrowser.getObject(World.getInstance(),WorldBrowser.getObjectID(World.getInstance(),targetObject)).look();
				else
					//	TODO: Change to TextLib
					System.out.println("Object is not here");
			}
		}
		else
		{
			Room.enterRoom(World.getInstance().getCurrentRoom());
		}
		
		
	}
	
	
	
}

package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

	//	TODO: Allow load/save function through drawMenu()
	public static boolean drawMenu(Scanner sc, List<String> cmdList)
	{
		switch(cmdList.get(0))
		{
		case TextLib.SW_GO:
			drawGo(cmdList);
			break;
		case TextLib.SW_LOOK:
			drawLook(cmdList);
			break;
		case TextLib.SW_HELP_1:
		case TextLib.SW_HELP_2:
			drawHelp(cmdList);
			break;
		case TextLib.SW_EXIT_1:
		case TextLib.SW_EXIT_2:
			return true;
		default:
			System.out.println(TextLib.TXT_INVALID_COMMAND);
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
			case TextLib.SW_HELP_1:
			case TextLib.SW_HELP_2:
				//	A bit redundant perhaps
				System.out.println(TextLib.TXT_HELP_HELP);
				break;
			case TextLib.SW_EXIT_1:
			case TextLib.SW_EXIT_2:
				System.out.println(TextLib.TXT_HELP_EXIT);
				break;
			//	TODO: Implement all supported commands
			default:
				System.out.println(TextLib.TXT_HELP_CONTENT);
				break;
			}
		}
		else
		{
			System.out.println(TextLib.TXT_HELP_CONTENT);
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
			case TextLib.NORTH:
			case TextLib.SOUTH:
			case TextLib.EAST:
			case TextLib.WEST:
				try
				{
					World.getCurrentWorld().setCurrentRoom(World.getCurrentWorld().getCurrentRoom().travel(cmdList.get(1)));
					Room.enterRoom(World.getCurrentWorld().getCurrentRoom());
				}
				catch(InvalidRoomConnectionException ex)
				{
					System.out.println(TextLib.getRandElement(TextLib.multi_GO_INVALID_ROOM_CONN()));
				}
				break;
			case TextLib.SW_HELP_1:
			case TextLib.SW_HELP_2:
				break;
			default:
				System.out.println(TextLib.getRandElement(TextLib.multi_GO_INVALID_DIR()));
				break;
			}
		}
		else
		{
			System.out.println(TextLib.GO_EMPTY_DIRECTION);
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
				if(World.getCurrentWorld().isValidObject(World.getCurrentWorld().getObjectID(targetObject)) && World.getCurrentWorld().getCurrentRoom().inRoom(World.getCurrentWorld().getObjectID(targetObject)))
					World.getCurrentWorld().getObject(World.getCurrentWorld().getObjectID(targetObject)).look();
				else
					//	TODO: Change to TextLib
					System.out.println("Object is not here");
			}
		}
		else
		{
			Room.enterRoom(World.getCurrentWorld().getCurrentRoom());
		}
		
		
	}
	
	
	
}

package se.cqst.whatif.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainProject {
	
	public static final String	SYS_STRING		=	"WhatIF-alpha-0.6";
	public static final String 	TXT_PROMPT		=	"Enter command: ";
	
	private static Game game = null;
	
	public static void main(String[] args) {
		/*
		 * 	Set debug status:
		 *		-1 = No messages
		 *		0  = Show error messages
		 *		1  = Show error & warning messages
		 *		2  = Show error, warning & info messages
		 *		3  = Show error, warning, info & debug messages 
		 */	
		if(args.length > 0)
		{
			try
			{
				CmdLib.setDebug(Integer.parseInt(args[0]));
			}
			catch(NumberFormatException ex)
			{
				CmdLib.setDebug(3);
			}
		}
		else
			CmdLib.setDebug(3);
		
		Scanner sc = new Scanner(System.in);
		boolean sysExit = false;

		//	Print version information
		System.out.println(SYS_STRING);
		
		List<Configuration> configList = getConfigList();
		
		//	Create Menu
		Menu mainMenu = new Menu(getConfig("dictConfig", configList));

		
		do
		{
			//	Print initial menu
			mainMenu.printStart();
			
			//	Return false unless user inputs exit command
			if(!mainMenu.drawStart(Menu.getParam(CmdLib.readString(sc, TXT_PROMPT))))
				sysExit = true;
		} while(mainMenu.displayStart() && !sysExit);
		
		if(!sysExit)
		{
			switch(mainMenu.getLoadState())
			{
			case NEW_GAME:
				CmdLib.writeLog("INFO", "Starting new game...");
				game = new Game();
				game.setWorld(new World(configList));
				game.setCurrentActor(game.findActor(getConfig("actorConfig", configList).getProperty("ACTOR_START")));
				CmdLib.writeLog("DEBUG", "Starting Actor set to " + game.getCurrentActor().toString());
				game.setCurrentRoom(game.getCurrentActor().getCurrentLocation());
				CmdLib.writeLog("DEBUG", "Starting Room set to " + game.getCurrentRoom().toString());
				break;
			case LOAD_GAME:
				CmdLib.writeLog("INFO", "Loading saved game...");
				//	TODO: Write logic for loading games
				break;
			case NO_SELECTION:
			default:
				//	You should not be able to get here
				System.exit(-1);
				break;		
			}
			
			//	Enter room
			CmdLib.writeLog("DEBUG", "Starting game...");
			game.getCurrentRoom().enterRoom();
			
			//	Draw menu and loop until exit
			while(!sysExit)
			{
				//	Return false unless user inputs exit command
				if(mainMenu.drawMenu(game, Menu.getParam(CmdLib.readString(sc,TXT_PROMPT))))
					sysExit = true;
			}
			CmdLib.writeLog("DEBUG", "Exiting game...");
		}
		sc.close();

	}
	
	public static List<Configuration> getConfigList()
	{
		List<Configuration> configList = new ArrayList<Configuration>();
		
		//	Add Actor configuration file
		configList.add(new Configuration("actorConfig", Configuration.Type.ACTOR_CONFIG, "/se/cqst/whatif/resources/actors.properties"));
		
		//	Add Room configuration file
		configList.add(new Configuration("roomConfig", Configuration.Type.ROOM_CONFIG, "/se/cqst/whatif/resources/rooms.properties"));
		
		//	Add Container configuration file
		configList.add(new Configuration("containerConfig", Configuration.Type.CONTAINER_CONFIG, "/se/cqst/whatif/resources/containers.properties"));
		
		//	Add Item configuration file
		configList.add(new Configuration("itemConfig", Configuration.Type.ITEM_CONFIG, "/se/cqst/whatif/resources/items.properties"));
		
		//	Add RoomConnection configuration file
		configList.add(new Configuration("connectionConfig", Configuration.Type.ROOMCONN_CONFIG, "/se/cqst/whatif/resources/roomconnectors.properties"));
		
		//	Add General configuration file
		configList.add(new Configuration("mainConfig", Configuration.Type.GAME_CONFIG, "/se/cqst/whatif/resources/config.properties"));
		
		//	Add Dictionary configuration file
		configList.add(new Configuration("dictConfig", Configuration.Type.DICT_CONFIG, "/se/cqst/whatif/resources/dict_en.properties"));		
		
		return configList;
	}
	
	public static Configuration getConfig(String name, List<Configuration> configList)
	{
		for(Configuration config : configList)
		{
			if(config.getName().equals(name))
				return config;
		}
		throw new NullPointerException();
	}

}

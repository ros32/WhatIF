package se.cqst.whatif.main;

import java.util.Scanner;

public class MainProject {

	
/*
 * 	Set debug status:
 *		-1 = No messages
 *		0  = Show error messages
 *		1  = Show error & warning messages
 *		2  = Show error, warning & info messages
 *		3  = Show error, warning, info & debug messages 
 */	
	private static int debug = 3;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		boolean sysExit = false;

		//	Print version information
		System.out.println(TextLib.SYS_STRING);
		
		//	Create new game and initialize
		World.setCurrentWorld(new World());
		World.getCurrentWorld().init();
		
		//	Draw menu and loop until exit
		do
		{
			if(Menu.drawMenu(sc,Menu.getParam(CmdLib.readString(sc,TextLib.TXT_PROMPT))))
				sysExit = true;
		} while (!sysExit);
		sc.close();

	}

	public static int getDebug() {
		return debug;
	}

	public static void setDebug(int debug) {
		MainProject.debug = debug;
	}

}

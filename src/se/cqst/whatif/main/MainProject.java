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
	//	TODO: Import debug from config.properties?
	//		Set to initial 1, load config, then
	//		set to configured value
	private static int debug = 3;
	
	public static final String	SYS_NAME		=	"WhatIF";
	public static final String 	SYS_VERSION		=	"0.2";
	public static final String	SYS_PHASE		=	"alpha";
	public static final String	SYS_STRING		=	SYS_NAME + "-" + SYS_PHASE + "-" + SYS_VERSION;
	
	public static final String 	TXT_PROMPT		=	"Enter command: ";
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		boolean sysExit = false;

		//	Print version information
		System.out.println(SYS_STRING);
		
		//	Create new game and initialize
		World.getInstance();
		
		//	Draw menu and loop until exit
		do
		{
			if(Menu.drawMenu(sc,Menu.getParam(CmdLib.readString(sc,TXT_PROMPT))))
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

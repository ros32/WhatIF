package se.cqst.whatif.main;

import java.lang.reflect.Array;

public class TextLib {
	
	public static final String	SYS_NAME		=	"WhatIF";
	public static final String 	SYS_VERSION		=	"0.2";
	public static final String	SYS_PHASE		=	"alpha";
	public static final String	SYS_STRING		=	SYS_NAME + "-" + SYS_PHASE + "-" + SYS_VERSION;
	
	public static final String	CONF_FILEPATH		=	"/se/cqst/whatif/resources/config.properties";
	public static final String	DICT_FILEPATH		=	"/se/cqst/whatif/resources/dict_en.properties";
	public static final String	ROOM_FILEPATH		=	"/se/cqst/whatif/resources/rooms.properties";
	public static final String	ZONE_FILEPATH		=	"/se/cqst/whatif/resources/zones.properties";
	public static final String	CONT_FILEPATH		=	"/se/cqst/whatif/resources/containers.properties";
	public static final String	ITEM_FILEPATH		=	"/se/cqst/whatif/resources/items.properties";
	public static final String	CONN_FILEPATH		=	"/se/cqst/whatif/resources/roomconnectors.properties";
	
	public static final String 	SW_EXIT_1		=	"exit";
	public static final String 	SW_EXIT_2		=	"quit";
	public static final String 	SW_HELP_1		=	"help";
	public static final String 	SW_HELP_2		=	"?";
	public static final String 	SW_Y			=	"y";
	public static final String 	SW_N			=	"n";
	
	public static final String	SW_GO			=	"go";
	public static final String	SW_TAKE			=	"take";
	public static final String	SW_LOOK			=	"look";
	public static final String	SW_USE			=	"use";
	public static final String	SW_PUT			=	"put";
	public static final String	SW_DROP			=	"drop";
	
	public static final String 	TXT_LF			=	"\n";
	public static final String 	TXT_LF_INDENT_3 	=	"\n   ";
	public static final String	TXT_TAB			=	"\t";
	public static final String	TXT_LINE_HOR		=	"================================================================================";
	public static final String 	TXT_PROMPT		=	"Enter command: ";
	public static final String 	TXT_Y_N_PROMPT		=	"[Y]es/[N]o:";
	
	public static final String 	ERR_OUT_OF_RANGE_1	=	"Please enter a value between ";
	public static final String 	ERR_OUT_OF_RANGE_2	=	" and ";
	public static final String 	ERR_OUT_OF_RANGE_3	=	".";
	public static final String 	ERR_INVALID_Y_N_INPUT	=	"Please enter Y/y for Yes or N/n for No";
	public static final String 	ERR_NO_INPUT		=	"You must enter a value!";
	public static final String 	ERR_NUM_FORMAT		=	"The value entered is not correct. Please enter a number.";
	
	public static final String	TXT_HELP_CONTENT	=	"";
	public static final String	TXT_HELP_HELP		=	"";
	public static final String	TXT_HELP_EXIT		=	"";
	
	public static final String	NORTH			=	"north";
	public static final String	NORTH_LOC		=	"To the" + NORTH;
	public static final String	SOUTH			=	"south";
	public static final String	SOUTH_LOC		=	"To the" + SOUTH;
	public static final String	EAST			=	"east";
	public static final String	EAST_LOC		=	"To the" + EAST;
	public static final String	WEST			=	"west";
	public static final String	WEST_LOC		=	"To the" + WEST;
	public static final String	UP			=	"up";
	public static final String	UP_LOC			=	"Above you";
	public static final String	DOWN			=	"down";
	public static final String	DOWN_LOC		=	"Below you";
	
	public static final String	ROOMCONN_OVERWRITE	=	"Overwriting existing room connection...";
	public static final String	ROOMCONN_DESC_SINGLE	=	"There is ";
	
	public static final String	TXT_INVALID_COMMAND	=	"Invalid command. Please type \"" + SW_HELP_1 + "\" for a list of commands.\n";
	
	public static final String	EX_INVALID_ROOM_CONN	=	"You can not go that way.";
	
	public static final String	GO_VALID_CMDS		=	"";
	public static final String	GO_VALID_DIRECTIONS	=	NORTH + ", " + SOUTH + ", " + EAST + ", " + WEST + ", " + UP + " and " + DOWN + " are valid inputs";
	public static final String	GO_EMPTY_DIRECTION	=	"Go where? (" + GO_VALID_DIRECTIONS + ")";
	
	public static final String	ROOM_CONN_DESC_ART_A	=	"a";
	public static final String	ROOM_CONN_DESC_ART_AN	=	"an";
	public static final String	ROOM_ENTER_ZONES	=	"The following containers exist here:";
	public static final String	ROOM_ENTER_ZONES_NONE	=	"There are no containers in this room.";
	public static final String	ROOM_ENTER_ITEMS	=	"You see the following items on the ground here:";	
	public static final String	ROOM_ENTER_ITEMS_NONE	=	"There are no items on the ground.";

	public static final String 	ERR_PROPERTY_NOT_FOUND 	= 	"ERROR: Could not load property:";

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
	
	public static String getRandElement(String[] array)
	{	return array[(int)(Math.random() * ((Array.getLength(array))))];	}
}

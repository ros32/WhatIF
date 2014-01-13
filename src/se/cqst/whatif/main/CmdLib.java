/* 
 * 
 */
package se.cqst.whatif.main;

import java.lang.reflect.Array;
import java.util.Scanner;

public class CmdLib {
	
	public static final String 	SW_Y			=	"y";
	public static final String 	SW_N			=	"n";
	
	public static final String 	ERR_OUT_OF_RANGE_1			=	"Please enter a value between ";
	public static final String 	ERR_OUT_OF_RANGE_2			=	" and ";
	public static final String 	ERR_OUT_OF_RANGE_3			=	".";
	public static final String 	ERR_INVALID_Y_N_INPUT		=	"Please enter Y/y for Yes or N/n for No";
	public static final String 	ERR_NO_INPUT				=	"You must enter a value!";
	public static final String 	ERR_NUM_FORMAT				=	"The value entered is not correct. Please enter a number.";
	
	private static int debug;
	
	public static int readInt(Scanner sc, String prompt)
	{
		boolean getValue = false;
		int value = -1;
		while (!getValue)
		{
			try
			{
				System.out.print(prompt);
				value = Integer.parseInt(sc.nextLine());
				getValue=true;
			}
			catch (NumberFormatException ex)
			{
				System.out.println(ERR_NUM_FORMAT);
				getValue=false;
			}
		}
		return value;
	}
	
	public static int readInt(Scanner sc, String prompt, int min, int max)
	{
			boolean getValue = false;
			int value = -1;
			while (!getValue)
			{
				value = CmdLib.readInt(sc,prompt);
				getValue = true;
				if (!((value >= min) && (value <= max)))
				{
					System.out.println(ERR_OUT_OF_RANGE_1 + min + 
							ERR_OUT_OF_RANGE_2 + max + ERR_OUT_OF_RANGE_3);
					getValue=false;
				}
		}
		return value;
	}
	
	public static double readDouble(Scanner sc, String prompt)
	{
		boolean getValue = false;
		double value = -1f;
		while (!getValue)
		{
			try
			{
				System.out.print(prompt);
				value = Double.parseDouble(sc.nextLine());
				getValue=true;
			}
			catch (NumberFormatException ex)
			{
				System.out.println(ERR_NUM_FORMAT);
				getValue=false;
			}
		}
		return value;
	}
	
	public static double readDouble(Scanner sc, String prompt, double min, double max)
	{
			boolean getValue = false;
			double value = -1f;
			while (!getValue)
			{
				value = CmdLib.readDouble(sc, prompt);
				getValue = true;
				if (!((value >= min) && (value <= max)))
				{
					System.out.println(ERR_OUT_OF_RANGE_1 + min + 
							ERR_OUT_OF_RANGE_2 + max + ERR_OUT_OF_RANGE_3);
					getValue=false;
				}
		}
		return value;
	}
	
	public static String readString(Scanner sc,String prompt)
	{
		return CmdLib.readString(sc,prompt,false);
	}	
	
	public static String readString(Scanner sc,String prompt, boolean allowEmpty)
	{
		boolean getValue = false;
		
		String value = null;
		while(!getValue)
		{
			System.out.print(prompt);
			value = sc.nextLine();
			getValue = true;
			
			if (!allowEmpty && (value.equals("")))
			{
				System.out.println(ERR_NO_INPUT);
				getValue = false;
			}
		}
		return value;
	}
	
	public static boolean confirm(Scanner sc, String prompt)
	{
		return CmdLib.confirm(sc,prompt,false,false);
	}
	
	public static boolean confirm(Scanner sc, String prompt, boolean useDefault, boolean defaultValue)
	{
		String value = null;
		while(true)
		{
			System.out.print(prompt);
			value = sc.nextLine();
			switch(value.toLowerCase())
			{
			case SW_Y:
				return true;
			case SW_N:
				return false;
			default:
				if(useDefault && value.equalsIgnoreCase(""))
				{
					return defaultValue;
				}
				else
				{
					System.out.print(ERR_INVALID_Y_N_INPUT);
					break;	
				}
			}
		}
	}
	
	public static String textBlock(String input,int size)
	{
		return CmdLib.textBlock(input,size,' ');
	}
	
	public static String textBlock(String input,int size, char padding)
	{
		if(input.length() == size)
			return input;
		else if (input.length() > size)
			return input.substring(0,(size));
		else if (input.length() < size)
		{
			while(input.length() < size)
			{
				input = input + padding;
			}
			return input;
		}
		else
		{
			String noVal = "";
			while(noVal.length() < size)
			{
				noVal = noVal + padding;
			}
			return noVal;
		}
	}

	public static void writeLog(String importance, String message)
	{	
		if(importance.equalsIgnoreCase("DEBUG") && getDebug() >= 3)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("INFO") && getDebug() >= 2)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("WARNING") && getDebug() >= 1)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("ERROR") && getDebug() >= 0)
			System.out.println(importance.toUpperCase() + ": " + message);	
	}
	
	public static String getRandElement(String[] array)
	{	return array[(int)(Math.random() * ((Array.getLength(array))))];	}


	public static int getDebug() {
		return debug;
	}


	public static void setDebug(int debugValue) {
		debug = debugValue;
	}
}

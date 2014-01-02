package se.cqst.whatif.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

public class CmdLib {
	
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
				System.out.println(TextLib.ERR_NUM_FORMAT);
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
					System.out.println(TextLib.ERR_OUT_OF_RANGE_1 + min + 
							TextLib.ERR_OUT_OF_RANGE_2 + max + TextLib.ERR_OUT_OF_RANGE_3);
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
				System.out.println(TextLib.ERR_NUM_FORMAT);
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
					System.out.println(TextLib.ERR_OUT_OF_RANGE_1 + min + 
							TextLib.ERR_OUT_OF_RANGE_2 + max + TextLib.ERR_OUT_OF_RANGE_3);
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
				System.out.println(TextLib.ERR_NO_INPUT);
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
			case TextLib.SW_Y:
				return true;
			case TextLib.SW_N:
				return false;
			default:
				if(useDefault && value.equalsIgnoreCase(""))
				{
					return defaultValue;
				}
				else
				{
					System.out.print(TextLib.ERR_INVALID_Y_N_INPUT);
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

	public static Properties loadProperties(InputStream filepath)
	{
		Properties properties = new Properties();
		
		try
		{
			properties.load(filepath);
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage() + ex.getStackTrace());
		}
		return properties;
	}
	
	public static Properties loadProperties(InputStream filepath, String filter)
	{
		Properties properties = new Properties();
		Properties filteredProperties = new Properties();
		
		try
		{
			properties.load(filepath);
			filteredProperties = filterProperties(properties, filter);
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage() + ex.getStackTrace());
		}
		return filteredProperties;
	}
	
	public static Properties filterProperties(Properties toFilter, String filter)
	{
		Properties filteredProperties = new Properties();
		Enumeration<?> e = toFilter.propertyNames();
		while(e.hasMoreElements())
		{
			String key = (String) e.nextElement(); 
			if(key.contains(filter))
				filteredProperties.setProperty(key, toFilter.getProperty(key));
		}
		return filteredProperties;
	}

	public static String getProperty(Properties config, String key)
	{	return config.getProperty(key, TextLib.ERR_PROPERTY_NOT_FOUND + " " + key);}

	public static void writeLog(String importance, String message)
	{	
		if(importance.equalsIgnoreCase("DEBUG") && MainProject.getDebug() >= 3)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("INFO") && MainProject.getDebug() >= 2)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("WARNING") && MainProject.getDebug() >= 1)
			System.out.println(importance.toUpperCase() + ": " + message);	
		if(importance.equalsIgnoreCase("ERROR") && MainProject.getDebug() >= 0)
			System.out.println(importance.toUpperCase() + ": " + message);	
	}
}

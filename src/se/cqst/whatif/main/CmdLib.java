/* 
 * 
 */
package se.cqst.whatif.main;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

/**
 * <p>The CmdLib class contains generic methods used throughout the project.</p>
 * <p>Methods in the CmdLib class are generic in the form that they aren't explictly associated
 * with any other class. Most methods handle data input with some kind of data validation control.</p>
 * 
 */
public class CmdLib {
	
	public static final String 	SW_Y			=	"y";
	public static final String 	SW_N			=	"n";
	
//	public static final String 	TXT_Y_N_PROMPT				=	"[Y]es/[N]o:";
	
	public static final String 	ERR_OUT_OF_RANGE_1			=	"Please enter a value between ";
	public static final String 	ERR_OUT_OF_RANGE_2			=	" and ";
	public static final String 	ERR_OUT_OF_RANGE_3			=	".";
	public static final String 	ERR_INVALID_Y_N_INPUT		=	"Please enter Y/y for Yes or N/n for No";
	public static final String 	ERR_NO_INPUT				=	"You must enter a value!";
	public static final String 	ERR_NUM_FORMAT				=	"The value entered is not correct. Please enter a number.";
	public static final String 	ERR_PROPERTY_NOT_FOUND 		= 	"ERROR: Could not load property:";
	
	/**
	 * Read an int from the provided Scanner-object.
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @return int
	 */
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
	
	
	/**
	 * Read an int from the provided Scanner-object within the specified min and max range
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return int
	 */
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
	
	/**
	 * Read a double from the provided Scanner-object.
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @return double
	 */
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
	
	/**
	 * Read a double from the provided Scanner-object withing the specified min and max value.
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return double
	 */
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
	
	/**
	 * Read a String from the provided Scanner-object.
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @return String
	 */
	public static String readString(Scanner sc,String prompt)
	{
		return CmdLib.readString(sc,prompt,false);
	}	
	
	/**
	 * Read a String from the provided Scanner-object that could be empty or not.
	 *
	 * @param sc Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @param allowEmpty Allow the String to be empty
	 * @return String
	 */
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
	
	/**
	 * Ask the user to confirm an action by answering Y or N in a prompt.
	 *
	 * @param Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @return true, if successful
	 */
	public static boolean confirm(Scanner sc, String prompt)
	{
		return CmdLib.confirm(sc,prompt,false,false);
	}
	
	/**
	 * Ask the user to confirm an action by answering Y or N in a prompt. Set a default value if no input is recieved
	 *
	 * @param Scanner to read input from
	 * @param prompt Prompt to show user before input
	 * @param useDefault Use default value
	 * @param defaultValue Default value if no input is given
	 * @return true, if successful
	 */
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
	
	/**
	 * Creates a block of text with a specified size fom the input. If the
	 * input is too large, the text will be cropped; if too small it will be padded with
	 * spaces (' ').
	 *
	 * @param input Text that should be resized
	 * @param size Target size of block
	 * @return Cropped or padded String
	 */
	public static String textBlock(String input,int size)
	{
		return CmdLib.textBlock(input,size,' ');
	}
	
	/**
	 * Creates a block of text with a specified size fom the input. If the
	 * input is too large, the text will be cropped; if too small it will be padded with
	 * the provided padding char.
	 *
	 * @param input Text that should be resized
	 * @param size Target size of block
	 * @param padding Char to be used as padding
	 * @return Cropped or padded String
	 */
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

	/**
	 * Load a Java {@link Properties} file from an {@link InputStream}.
	 *
	 * @param filepath InputStream containing a Java .properties file
	 * @return properties
	 */
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
	
	/**
	 * Load a Java {@link Properties} file from an {@link InputStream}.
	 * The properties are filtered with the provided String, only returning
	 * those keys that match the filter.
	 *
	 * @param filepath InputStream containing a Java .properties file
	 * @param filter Input filter. Only keys matching the filter will be included
	 * @return Filtered properties
	 */
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
	
	/**
	 * Filter a {@link Properties} file with the provided with the provided String, only returning
	 * those keys that match the filter.
	 *
	 * @param toFilter Properties file to filter
	 * @param filter Input filter. Only keys matching the filter will be included.
	 * @return Filtered properties
	 */
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

	/**
	 * Return a key value from a {@link Properties} object.
	 *
	 * @param config Properties object
	 * @param key Key whose value should be fetched
	 * @return The value from the fetched key, or a message stating the value could not be found.
	 */
	public static String getProperty(Properties config, String key)
	{	return config.getProperty(key, ERR_PROPERTY_NOT_FOUND + " " + key);}

	/**
	 * Writes log outputs to {@link System.out} depending on the {@code MainProject.debug} value.  
	 *
	 * @param importance Severity of log entry. Could be {@code DEBUG, INFO, WARNING or ERROR}
	 * @param message Message to be printed with the entry
	 */
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
	
	public static String getRandElement(String[] array)
	{	return array[(int)(Math.random() * ((Array.getLength(array))))];	}
}

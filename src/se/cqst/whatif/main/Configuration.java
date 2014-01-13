package se.cqst.whatif.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Configuration {
	
	public static enum Type {ACTOR_CONFIG, GAME_CONFIG, DICT_CONFIG, WORLD_CONFIG, ROOM_CONFIG, CONTAINER_CONFIG, ITEM_CONFIG, ROOMCONN_CONFIG, MISC_CONFIG;	}

	private String name;
	private Type type;
	private Properties config;
	
	public Configuration(String name, Type type, String path)
	{
		this.setConfig(loadConfiguration(path));
		this.setType(type);
		this.setName(name);
	}
	
	public Configuration(String name, Type type, Properties config)
	{
		this.setConfig(config);
		this.setType(type);
		this.setName(name);
	}
	
	private Properties loadConfiguration(String filePath)
	{
		Properties target = null;
		try
		{
			CmdLib.writeLog("DEBUG", "Loading settings from " + filePath + "...");
			target = this.loadProperties(Configuration.class.getResourceAsStream(filePath));
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("DEBUG", "Configuration file not found: " + filePath);
//			System.exit(-1);
		}
		return target;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Properties getConfig() {
		return config;
	}

	private void setConfig(Properties config) {
		this.config = config;
	}
	
	/**
	 * Return a key value from a {@link Properties} object.
	 *
	 * @param config Properties object
	 * @param key Key whose value should be fetched
	 * @return The value from the fetched key, or a message stating the value could not be found.
	 */
	public String getProperty(String key)
	{	return this.getConfig().getProperty(key, "ERROR: Could not load property:" + " " + key);}
	
	/**
	 * Load a Java {@link Properties} file from an {@link InputStream}.
	 *
	 * @param filepath InputStream containing a Java .properties file
	 * @return properties
	 */
	private Properties loadProperties(InputStream filepath)
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

	public Type getType() {
		return type;
	}

	private void setType(Type type) {
		this.type = type;
	}
	
}

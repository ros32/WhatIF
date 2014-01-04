package se.cqst.whatif.main;

import java.util.Properties;

public class Configuration {

	private Properties worldConfig;
	private Properties dictConfig;
	private Properties roomConfig;
	private Properties containerConfig;
	private Properties itemConfig;
	private Properties connectorConfig;
	
	public Configuration(String worldConf, String dictConf, String roomConf, String containerConf, String itemConf, String connectorConf)
	{
		this.setWorldConfig(loadConfiguration(worldConf));
		this.setDictConfig(loadConfiguration(dictConf));
		this.setRoomConfig(loadConfiguration(roomConf));
		this.setContainerConfig(loadConfiguration(containerConf));
		this.setItemConfig(loadConfiguration(itemConf));
		this.setConnectorConfig(loadConfiguration(connectorConf));
	}
	
	private Properties loadConfiguration(String filePath)
	{
		Properties target = null;
		try
		{
			CmdLib.writeLog("INFO", "Loading settings from " + filePath + "...");
			target = CmdLib.loadProperties(World.class.getResourceAsStream(filePath));
		}
		catch(NullPointerException ex)
		{
			CmdLib.writeLog("ERROR", "Configuration file not found: " + filePath);
			System.exit(-1);
		}
		return target;
	}

	public Properties getWorldConfig() {
		return worldConfig;
	}

	private void setWorldConfig(Properties worldConfig) {
		this.worldConfig = worldConfig;
	}

	public Properties getDictConfig() {
		return dictConfig;
	}

	private void setDictConfig(Properties dictConfig) {
		this.dictConfig = dictConfig;
	}

	public Properties getRoomConfig() {
		return roomConfig;
	}

	private void setRoomConfig(Properties roomConfig) {
		this.roomConfig = roomConfig;
	}

	public Properties getContainerConfig() {
		return containerConfig;
	}

	private void setContainerConfig(Properties containerConfig) {
		this.containerConfig = containerConfig;
	}

	public Properties getItemConfig() {
		return itemConfig;
	}

	private void setItemConfig(Properties itemConfig) {
		this.itemConfig = itemConfig;
	}

	public Properties getConnectorConfig() {
		return connectorConfig;
	}

	private void setConnectorConfig(Properties connectorConfig) {
		this.connectorConfig = connectorConfig;
	}
	
}

package se.cqst.whatif.main;

import java.util.Properties;

public class Configuration {

	private Properties worldConfig;
	private Properties dictConfig;
	private Properties roomConfig;
	private Properties containerConfig;
	private Properties itemConfig;
	private Properties connectorConfig;

	public Configuration()
	{
		this.setWorldConfig(null);
		this.setDictConfig(null);
		this.setRoomConfig(null);
		this.setContainerConfig(null);
		this.setItemConfig(null);
		this.setConnectorConfig(null);
	}

	public Properties getWorldConfig() {
		return worldConfig;
	}

	public void setWorldConfig(Properties worldConfig) {
		this.worldConfig = worldConfig;
	}

	public Properties getDictConfig() {
		return dictConfig;
	}

	public void setDictConfig(Properties dictConfig) {
		this.dictConfig = dictConfig;
	}

	public Properties getRoomConfig() {
		return roomConfig;
	}

	public void setRoomConfig(Properties roomConfig) {
		this.roomConfig = roomConfig;
	}

	public Properties getContainerConfig() {
		return containerConfig;
	}

	public void setContainerConfig(Properties containerConfig) {
		this.containerConfig = containerConfig;
	}

	public Properties getItemConfig() {
		return itemConfig;
	}

	public void setItemConfig(Properties itemConfig) {
		this.itemConfig = itemConfig;
	}

	public Properties getConnectorConfig() {
		return connectorConfig;
	}

	public void setConnectorConfig(Properties connectorConfig) {
		this.connectorConfig = connectorConfig;
	}
	
}

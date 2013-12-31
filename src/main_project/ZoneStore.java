package main_project;

import java.util.List;

public interface ZoneStore {
	
	public abstract Zone getZone(String identifier);
	public abstract List<Zone> getZoneList();
	public abstract String printZone();

}

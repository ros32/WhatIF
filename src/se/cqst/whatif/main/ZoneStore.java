//	TODO: Deprecated, To Be Deleted
package se.cqst.whatif.main;

import java.util.List;

public interface ZoneStore {
	
	public abstract Zone getZone(String identifier);
	public abstract List<Zone> getZoneList();
	public abstract String printZone();

}

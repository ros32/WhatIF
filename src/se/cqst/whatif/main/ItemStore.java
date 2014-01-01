package se.cqst.whatif.main;

import java.util.List;

public interface ItemStore {

	public abstract Item getItem(String identifier);
	public abstract void putItem(Item thing);
	public abstract List<Item> getItemList();
	public abstract String toString();
	
}

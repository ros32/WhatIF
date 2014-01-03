package se.cqst.whatif.main;

import java.util.List;

/**
 * <p>The ItemStore inteface adds {@link Item} inventory-handling capabilities to classes, by adding an
 * {@code Item}-List along with methods to manipulate both the list and the objects within.</p>
 * 
 * @see {@link Item}
 */
public interface ItemStore {

	/**
	 * <p>Returns a single {@link Item} matching the {@code String identifier}, or {@link null} if none found.</p>
	 *
	 * @param identifier Contains the {@code GenericObject.identifier} value of the target Item
	 * @return {@link Item}, or {@code null} if no {@code Item} matching the {@code identifier} is found.
	 */
	public abstract Item getItem(String identifier);
	
	
	//	TODO: Javadoc
	public abstract void putItem(Item thing);
	
	
	//	TODO: Javadoc
	public abstract List<Item> getItemList();
	
	
	
	public abstract String toString();
	
}

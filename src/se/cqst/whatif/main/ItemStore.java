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
	
	
	/**
	 * Puts an {@link Item} into an {@code ItemStore} object
	 *
	 * @param thing Object to be stored
	 */
	public abstract void putItem(Item thing);
	
	
	/**
	 * Returns the {@code List<}{@link Item}{@code >} object from the {@code ItemStore}
	 *
	 * @return {@code List<Item>}
	 */
	public abstract List<Item> getItemList();
	
	
	
	public abstract String toString();
	
}

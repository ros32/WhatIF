package se.cqst.whatif.main;

public abstract class Item extends GenericObject {
	
	/*
	 * 						Static Variables
	 */
	
	/*
	 * 						Variables
	 */
	
	
	/*
	 * 						Constructors
	 */
	
	public					Item(String name, String identifier)
	{
		super(name,identifier);
	}
	
	/*
	 * 						Overwritten Object Methods
	 */
	
	/*
	 * 						Basic get Methods
	 */
	
	/*
	 * 						Basic set Methods
	 */
	
	/*
	 * 						Other Methods
	 */
	
	public void				use()
	{
		//	TODO: Write use() method
		//		Items are usable and should have a use() method
	}
	
	public void				use(GenericObject object)
	{
		//	TODO: Write use(GenericObject) method
		//		Items are usable and should have a use() method
	}

	public abstract void	move(ItemStore source, ItemStore target);
	
	/*
	 * 						Static Methods
	 */

}

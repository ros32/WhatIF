package se.cqst.whatif.main;

public abstract class Item extends GenericObject {
	
	/*
	 * 						Static Variables
	 */
	
	/*
	 * 						Variables
	 */
	
	private String			longDescription;
	
	/*
	 * 						Constructors
	 */
	
	public					Item(String name, String identifier)
	{
		super(name,identifier);
		this.longDescription = "";
	}
	
	/*
	 * 						Overwritten Object Methods
	 */
	
	/*
	 * 						Basic get Methods
	 */

	public String			getLongDescription()			{	return this.longDescription;	}
	
	/*
	 * 						Basic set Methods
	 */
	
	public void				setLongDescription(String desc)	{	this.longDescription=desc;	}
	
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

	public abstract void	get(ItemStore target);
	
	public abstract void	put(ItemStore source);
	
	/*
	 * 						Static Methods
	 */

}

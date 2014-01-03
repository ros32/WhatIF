package se.cqst.whatif.main;

public abstract class Item extends GenericObject {
	
	/*
	 * 						Static Variables
	 */
	
	/*
	 * 						Variables
	 */
	
	private String			longDescription;
	private boolean			isMovable;
	
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
	public boolean 			isMovable() 					{	return isMovable;		}
	
	/*
	 * 						Basic set Methods
	 */
	
	public void				setLongDescription(String desc)	{	this.longDescription=desc;	}
	public void				setMovable(boolean movable)		{	this.isMovable=movable;		}
	
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

	public abstract void	get();
	
	public abstract void	put(ItemStore target);
	
	/*
	 * 						Static Methods
	 */

}

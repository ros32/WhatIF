package se.cqst.whatif.main;

public class MovableItem extends Item {
	
	/*
	 * 			Static Variables
	 */
	
	/*
	 * 			Variables
	 */
	
	/*
	 * 			Constructors
	 */
	
//	public			StaticItem(String name)				
//	{
//		super(name);
//		super.setMovable(false);
//	}
	
	public			MovableItem(String name, String identifier)	
	{
		super(name,identifier);		
		CmdLib.writeLog("DEBUG", "MovableItem Object " + identifier + " created.");
	}
	
	/*
	 * 			Overwritten Object Methods
	 */

	
	
	/*
	 * 			Basic get Methods
	 */
	
	/*
	 * 			Basic set Methods
	 */
	
	/*
	 * 			Other Methods
	 */
	
	/*
	 * 			Interface Methods - Usable
	 */
	
	public void		use()
	{
		
	}
	
	public void		use(GenericObject object)
	{
		
	}
	
	public void		get(ItemStore target)
	{
		//	TODO: Write get() for MovableItem
	}
	
	public void		put(ItemStore target)
	{
		//	TODO: Write put(ItemStore) for MovableItem
	}
	
	/*
	 * 			Interface Methods - Lookable
	 */
	
//	public void		look()
//	{
//		
//	}
	
	/*
	 * 			Static Methods
	 */
	

}

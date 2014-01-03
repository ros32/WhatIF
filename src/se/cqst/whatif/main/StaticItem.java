package se.cqst.whatif.main;

public class StaticItem extends Item {
	
	/*
	 * 			Static Variables
	 */
	
	/*
	 * 			Variables
	 */
	
	/*
	 * 			Constructors
	 */
	
	public			StaticItem(String name, String identifier)	
	{
		super(name,identifier);		
		super.setMovable(false);
		CmdLib.writeLog("DEBUG", "StaticItem Object " + identifier + " created.");
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
	
	public void		get()
	{
		//	StaticItem cannot be moved
	}
	
	public void		put(ItemStore target)
	{
		//	StaticItem cannot be moved
	}
	
	/*
	 * 			Interface Methods - Readable
	 */
	
	public void		read()
	{
		
	}
	
	/*
	 * 			Interface Methods - Lookable
	 */
	
	public void		look()
	{
		
	}
	
	/*
	 * 			Static Methods
	 */
	

}

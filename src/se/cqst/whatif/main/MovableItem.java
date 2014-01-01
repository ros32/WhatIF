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
		super.setMovable(true);
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

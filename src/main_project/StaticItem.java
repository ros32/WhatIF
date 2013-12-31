package main_project;

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
	
//	public			StaticItem(String name)				
//	{
//		super(name);
//		super.setMovable(false);
//	}
	
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

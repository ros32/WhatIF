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
	
	public void		use()
	{
		
	}
	
	public void		use(GenericObject object)
	{
		
	}
	
	public void		move(ItemStore source, ItemStore target)
	{
		//	StaticItem cannot be moved
		CmdLib.writeLog("DEBUG", "StaticItem \"" + this.toString() + "\" cannot be moved");
	}
	
	/*
	 * 			Static Methods
	 */
	

}

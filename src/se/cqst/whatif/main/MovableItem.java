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
	
	public void		use()
	{
		
	}
	
	public void		use(GenericObject object)
	{
		
	}
	
	public void		move(ItemStore source, ItemStore target)
	{
		//	TODO: Write get() for MovableItem
		target.putItem(this);
		source.removeItem(this.toString());
		CmdLib.writeLog("DEBUG", this.toString() + " has been moved from " + source.toString() + " to " + target.toString() + ".");
	}
	
	/*
	 * 			Static Methods
	 */
	

}

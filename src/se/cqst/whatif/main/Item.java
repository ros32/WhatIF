//	XXX: Cleanup old logic
package se.cqst.whatif.main;

public abstract class Item extends GenericObject {
	
	/*
	 * 			Static Variables
	 */
	
//	private static int 	counter;
	
	/*
	 * 			Variables
	 */
	
//	private String		identifier;
//	private String		name;
//	private String		description;
	private String		longDescription;
	private boolean		isMovable;
	
	/*
	 * 			Constructors
	 */
	
//	public			Item(String name)		{	this(name, name + counter++); counter--;	}
	
	public			Item(String name, String identifier)
	{
		super(name,identifier);
		this.longDescription = "";
//		CmdLib.writeLog("DEBUG", "Item Object " + identifier + " created.");
	}
	
	/*
	 * 			Overwritten Object Methods
	 */
	
//	public String		toString()			{	return this.identifier;		}
	
	/*
	 * 			Basic get Methods
	 */
	
//	public String		getName()			{	return this.name;		}
//	public String		getDescription()		{	return this.description;	}
	public String		getLongDescription()		{	return this.longDescription;	}
	public boolean 		isMovable() 			{	return isMovable;		}
	
	/*
	 * 			Basic set Methods
	 */
	
//	public void		setDescription(String desc)	{	this.description=desc;		}
	public void		setLongDescription(String desc)	{	this.longDescription=desc;	}
	public void		setMovable(boolean movable)	{	this.isMovable=movable;		}
	
	/*
	 * 			Other Methods
	 */
	
	/*
	 * 			Interface Methods - Usable
	 */
	
	public void	use()
	{
		
	}
	
	public void	use(GenericObject object)
	{
		
	}

	/*
	 * 			Static Methods
	 */

}
